package chi_gitanalyz.gitanalyzator.ui.developer;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.developers.CurrentDev;
import chi_gitanalyz.gitanalyzator.ui.BaseActivity;
import dmax.dialog.SpotsDialog;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

import static android.provider.Telephony.TextBasedSmsColumns.STATUS_COMPLETE;
import static android.provider.Telephony.TextBasedSmsColumns.STATUS_NONE;

/**
 * Created by Papin on 29.09.2016.
 */

public class GraphDeveloperActivity extends BaseActivity {

    String TOKEN_ID;
    String DEV_ID;

    private LineChartView chart;
    private LineChartData data;

    Spinner SpinProject;
    Spinner SpinFilt;
    Spinner SpinDev;
    Spinner SpinBranch;

    int project_id;
    int filter_id;
    int dev_id;
    int branch_id;

    int curProjectID;
    int curBranchID;

    AlertDialog dialog;


    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = false;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = true;
    private boolean isCubic = true;
    private boolean hasLabelForSelected = true;
    ArrayList<String> project;
    ArrayList<String> filter;
    ArrayList<String> dev;
    ArrayList<Integer> id;
    ArrayList<String> branch;
    ArrayList<Integer> branchID;

    Handler h;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_developer_layout);
        Intent intent = getIntent();
        TOKEN_ID = intent.getStringExtra("TOKEN_ID");
        DEV_ID = intent.getStringExtra("DEV_ID");
        project_id = -5;
        dev_id = -5;
        branch_id = -5;
        curProjectID = -5;
        curBranchID = -5;

        project = new ArrayList<>();
        filter = new ArrayList<>();
        dev = new ArrayList<>();
        id = new ArrayList<>();
        branch = new ArrayList<>();
        branchID = new ArrayList<>();

        chart = (LineChartView) findViewById(R.id.chart_dev);
        chart.setOnValueTouchListener(new ValueTouchListener());

        SpinProject = (Spinner) findViewById(R.id.spinBranch_dev);
        SpinFilt = (Spinner) findViewById(R.id.spinFilter_dev);
        SpinDev = (Spinner) findViewById(R.id.spinDev_dev);
        SpinBranch = (Spinner) findViewById(R.id.spin_branch_dev);

        dialog = new SpotsDialog(this);
        dialog.show();

        app.getNet().getCuurDev(DEV_ID, TOKEN_ID);

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        break;
                    case STATUS_COMPLETE:
                        dialog.dismiss();
                }
            }

            ;
        };
        h.sendEmptyMessage(STATUS_NONE);
    }

    private void createGraph(CurrentDev CurDevObj) {
        fillSpiner(CurDevObj);
        hasLabels = !hasLabels;
        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }
        generateData(CurDevObj);
    }

    private void fillSpiner(CurrentDev curDevObj) {
        filter.clear();
        project.clear();
        dev.clear();
        branch.clear();
        id.clear();


        filter.add("Score");
        filter.add("Duplications");
        filter.add("Smells");
        ArrayAdapter<String> adapterFilter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, filter);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        project.add("All projects");
        for (int i = 0; i < curDevObj.getProjects().size(); i++)
            project.add(curDevObj.getProjects().get(i).getName());

        ArrayAdapter<String> adapterProject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, project);
        adapterProject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dev.add("All developers");
        for (int i = 0; i < curDevObj.getProjects().size(); i++)
            for (int j = 0; j < curDevObj.getProjects().get(i).getDevelopers().size(); j++) {
                dev.add(curDevObj.getProjects().get(i).getDevelopers().get(j).getName());
                id.add(curDevObj.getProjects().get(i).getDevelopers().get(j).getId());
            }
        ArrayAdapter<String> adapterDev = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dev);
        adapterProject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        SpinFilt.setAdapter(adapterFilter);
        SpinDev.setAdapter(adapterDev);
        SpinProject.setAdapter(adapterProject);


        // ***************** SPINER *****************

        SpinProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    project_id = -5;
                    SpinBranch.setVisibility(View.GONE);
                    generateData(curDevObj);
                } else {
                    branch.clear();
                    branch.add("All branches");
                    project_id = position - 1;
                    curProjectID = curDevObj.getProjects().get(project_id).getId();
                    for (int i = 0; i < curDevObj.getProjects().get(project_id).getBranches().size(); i++) {
                        if (curDevObj.getProjects().get(project_id).getBranches().get(i).getProjectId() == curProjectID) {
                            branch.add(curDevObj.getProjects().get(project_id).getBranches().get(i).getName());
                            branchID.add(curDevObj.getProjects().get(project_id).getBranches().get(i).getCommits().get(0).getBranchId());
                        }
                    }
                    SpinBranch.setVisibility(View.VISIBLE);
                    showSpin(curDevObj);
                }
                Log.d("POSIITON", "POSIITON" + project_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinFilt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter_id = position;
                generateData(curDevObj);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinDev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
                if (position == 0)
                    dev_id = -5;
                else
                    dev_id = id.get(position - 1);
                generateData(curDevObj);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 1) {
                    curBranchID = branchID.get(position - 1);
                    generateData(curDevObj);
                } else {
                    curBranchID = -5;
                    generateData(curDevObj);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // ***************** SPINER *****************
    }

    private void showSpin(CurrentDev curDevObj) {
        if (branch.size() >= 2) {
            ArrayAdapter<String> adapterBranch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branch);
            adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinBranch.setAdapter(adapterBranch);
            generateData(curDevObj);
        }
    }

    private void generateData(CurrentDev curDevObj) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                List<Line> lines = new ArrayList<Line>();
                List<PointValue> values;
                int color = 0;
                lines.clear();
                // AL PROJECTS
                if (project_id == -5) {
                    for (int i = 0; i < curDevObj.getProjects().size(); i++) {
                        for (int g = 0; g < curDevObj.getProjects().get(i).getBranches().size(); g++) {
                            values = new ArrayList<PointValue>();
                            for (int j = 0; j < curDevObj.getProjects().get(i).getBranches().get(g).getCommits().size(); j++) {
                                if (dev_id == -5) {
                                    if (filter_id == 0)
                                        values.add(new PointValue(j, curDevObj.getProjects().get(i).getBranches().get(g).getCommits().get(j).getScore()));
                                    else if (filter_id == 1)
                                        values.add(new PointValue(j, curDevObj.getProjects().get(i).getBranches().get(g).getCommits().get(j).getDuplications()));
                                    else if (filter_id == 2)
                                        values.add(new PointValue(j, curDevObj.getProjects().get(i).getBranches().get(g).getCommits().get(j).getSmells()));
                                } else {
                                    if ((curDevObj.getProjects().get(i).getBranches().get(g).getCommits().get(j).getDeveloperId() == dev_id)) {
                                        if (filter_id == 0) {
                                            values.add(new PointValue(j, curDevObj.getProjects().get(i).getBranches().get(g).getCommits().get(j).getScore()));
                                        } else if (filter_id == 1) {
                                            values.add(new PointValue(j, curDevObj.getProjects().get(i).getBranches().get(g).getCommits().get(j).getDuplications()));
                                        } else if (filter_id == 2) {
                                            values.add(new PointValue(j, curDevObj.getProjects().get(i).getBranches().get(g).getCommits().get(j).getSmells()));
                                        }
                                    }
                                }
                            }
                            Line line = new Line(values);
                            if (color > 4)
                                color = 0;
                            line.setColor(ChartUtils.COLORS[color]);
                            color++;
                            line.setCubic(isCubic);
                            line.setFilled(isFilled);
                            line.setHasLabels(hasLabels);
                            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                            line.setHasLines(hasLines);
                            line.setHasPoints(hasPoints);
                            lines.add(line);
                        }
                    }
                }
                // SELECTED PROJECT
                else {
                    for (int i = 0; i < 1; i++) {
                        for (int g = 0; g < curDevObj.getProjects().get(project_id).getBranches().size(); g++) {
                            values = new ArrayList<PointValue>();
                            for (int j = 0; j <= curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().size() - 1; j++) {
                                if (dev_id == -5) {
                                    if (curBranchID < 0) {
                                        if (filter_id == 0)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getScore()));
                                        else if (filter_id == 1)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getDuplications()));
                                        else if (filter_id == 2)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getSmells()));
                                    } else if (curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getBranchId() == curBranchID) {
                                        if (filter_id == 0)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getScore()));
                                        else if (filter_id == 1)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getDuplications()));
                                        else if (filter_id == 2)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getSmells()));
                                    }
                                } else if (curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getDeveloperId() == dev_id && curBranchID > 0) {
                                    if (curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getBranchId() == curBranchID) {
                                        if (filter_id == 0)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getScore()));
                                        else if (filter_id == 1)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getDuplications()));
                                        else if (filter_id == 2)
                                            values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getSmells()));
                                    }
                                } else if (curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getDeveloperId() == dev_id && curBranchID < 0) {
                                    if (filter_id == 0)
                                        values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getScore()));
                                    else if (filter_id == 1)
                                        values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getDuplications()));
                                    else if (filter_id == 2)
                                        values.add(new PointValue(j, curDevObj.getProjects().get(project_id).getBranches().get(g).getCommits().get(j).getSmells()));

                                }

                            }
                            Line line = new Line(values);
                            if (color > 4)
                                color = 0;
                            line.setColor(ChartUtils.COLORS[color]);
                            color++;
                            line.setShape(shape);
                            line.setCubic(isCubic);
                            line.setFilled(isFilled);
                            line.setHasLabels(hasLabels);
                            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                            line.setHasLines(hasLines);
                            line.setHasPoints(hasPoints);
                            lines.add(line);
                        }
                    }
                }
                data = new LineChartData(lines);

                if (hasAxes) {
                    Axis axisX = new Axis();
                    Axis axisY = new Axis().setHasLines(true);
                    if (hasAxesNames) {
                        axisX.setName("Comits");
                        axisY.setName("" + filter.get(filter_id));
                    }
                    data.setAxisXBottom(axisX);
                    data.setAxisYLeft(axisY);
                } else {
                    data.setAxisXBottom(null);
                    data.setAxisYLeft(null);
                }

                data.setBaseValue(Float.NEGATIVE_INFINITY);
                chart.setLineChartData(data);
                h.sendEmptyMessage(STATUS_COMPLETE);
            }
        });
        t.start();
    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case I_Net.CURR_DEV:
                createGraph((CurrentDev) NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {
        super.onNetRequestFail(evetId, NetObjects);
    }


    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
        }

        @Override
        public void onValueDeselected() {
        }

    }
}
