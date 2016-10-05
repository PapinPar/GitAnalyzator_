package chi_gitanalyz.gitanalyzator.ui.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.home.Home;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.project_id.ProjectsID;
import chi_gitanalyz.gitanalyzator.ui.BaseActivity;
import chi_gitanalyz.gitanalyzator.ui.FragmentDialog;
import dmax.dialog.SpotsDialog;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

import static android.provider.Telephony.TextBasedSmsColumns.STATUS_COMPLETE;
import static android.provider.Telephony.TextBasedSmsColumns.STATUS_NONE;

/**
 * Created by Papin on 27.09.2016.
 */

public class GraphProjectActivity extends BaseActivity implements FragmentDialog.GetOnspinListner {

    String TOKEN_ID;
    String PROJECT_ID;

    private LineChartView chart;
    private LineChartData data;

    int branch_id;
    int filter_id;

    AlertDialog dialog;

    boolean check;


    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = false;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = true;
    private boolean isCubic = true;
    private boolean hasLabelForSelected = true;
    ArrayList<String> branch;
    ArrayList<String> filter;
    FragmentDialog fragmentDialog;
    Handler h;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_project_layout);
        Intent intent = getIntent();
        TOKEN_ID = intent.getStringExtra("_TOKEN_");
        PROJECT_ID = intent.getStringExtra("ID_PROJECT");

        fragmentDialog = new FragmentDialog();
        branch_id = -5;

        branch = new ArrayList<>();
        filter = new ArrayList<>();

        filter.add("Score");
        filter.add("Duplications");
        filter.add("Smells");

        chart = (LineChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());


        dialog = new SpotsDialog(this);
        dialog.show();
        check = isNetworkConnected();
        if (check == true)
            app.getNet().projectHome(PROJECT_ID, TOKEN_ID);
        else
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        break;
                    case STATUS_COMPLETE:
                        dialog.dismiss();
                }
            }
        };
        h.sendEmptyMessage(STATUS_NONE);
    }

    private void createGraph(ProjectsID netObjects) {
        hasLabels = !hasLabels;
        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }
        generateData(netObjects);
    }

    @Override
    public void getList(Integer branch, Integer dev, Integer filter) {
        filter_id = filter;
        if (isNetworkConnected() == true) {
            dialog.show();
            if (branch == -5 && dev > 0)
                app.getNet().projectFilter(PROJECT_ID, TOKEN_ID, null, dev);
            else if (dev == -5 && branch > 0)
                app.getNet().projectFilter(PROJECT_ID, TOKEN_ID, branch, null);
            else if (branch == -5 && dev == -5)
                app.getNet().projectFilter(PROJECT_ID, TOKEN_ID, null, null);
            else if (dev != -5 & branch != -5)
                app.getNet().projectFilter(PROJECT_ID, TOKEN_ID, branch, dev);
        } else {
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

    }


    private void generateData(ProjectsID netObjects) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                List<Line> lines = new ArrayList<Line>();
                float max = 0;
                List<PointValue> values;
                int color = 0;
                lines.clear();
                for (int i = 0; i < netObjects.getBranches().size(); i++) {
                    values = new ArrayList<PointValue>();
                    for (int j = 0; j < netObjects.getBranches().get(i).getCommits().size() - 0; j++) {
                        if (filter_id == 0)
                            values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getScore()));
                        if (filter_id == 1)
                            values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getDuplications()));
                        if (filter_id == 2)
                            values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getSmells()));
                        if(max<values.get(j).getY())
                            max = values.get(j).getY();
                    }
                    Line line = new Line(values);
                    if (color >= 9)
                        color = 0;
                    line.setColor(ColorsUtilis.COLORS[color]);
                    color++;
                    line.setCubic(isCubic);
                    line.setFilled(isFilled);
                    line.setHasLabels(hasLabels);
                    line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                    line.setHasLines(hasLines);
                    line.setHasPoints(hasPoints);
                    lines.add(line);
                }

                values = new ArrayList<PointValue>();
                values.add(new PointValue(1,max+2));
                Line line = new Line(values);
                lines.add(line);

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

                data.setBaseValue(Float.POSITIVE_INFINITY);
                chart.setLineChartData(data);
                h.sendEmptyMessage(STATUS_COMPLETE);
            }
        });
        t.start();
    }

    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
        }

        @Override
        public void onValueDeselected() {
        }

    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case I_Net.HOME_PROJECT:
                dialog.dismiss();
                fragmentDialog.getListner(this, (Home) NetObjects);
                fragmentDialog.setCancelable(false);
                fragmentDialog.show(getFragmentManager(), "Filters");
                break;
            case I_Net.FILT_PROJECT:
                createGraph((ProjectsID) NetObjects);
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newFilter:
                dialog.show();
                if (isNetworkConnected() == true)
                    app.getNet().projectHome(PROJECT_ID, TOKEN_ID);
                else {
                    Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {
        dialog.dismiss();
        Toast.makeText(this, "Check your internet conection please", Toast.LENGTH_SHORT).show();
        finish();
    }
}
