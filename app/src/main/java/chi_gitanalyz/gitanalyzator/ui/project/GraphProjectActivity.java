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
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.Language;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.HomeResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.ProjectsIdResponse;
import chi_gitanalyz.gitanalyzator.ui.BaseActivity;
import dmax.dialog.SpotsDialog;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import static android.provider.Telephony.TextBasedSmsColumns.STATUS_COMPLETE;
import static android.provider.Telephony.TextBasedSmsColumns.STATUS_NONE;

/**
 * Created by Papin on 27.09.2016.
 */

public class GraphProjectActivity extends BaseActivity implements FragmentDialog.GetOnspinListner {

    private String tokenId;
    private String projectId;

    private LineChartView chart;
    private LineChartData data;

    private int branch_id;
    private int filter_id;

    private AlertDialog dialog;

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = false;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = true;
    private boolean isCubic = false;  //планость
    private boolean hasLabelForSelected = true;
    private ArrayList<String> branch;
    private ArrayList<String> filter;
    private FragmentDialog fragmentDialog;
    private Handler h;
    private List<Line> lines;
    private List<PointValue> values;
    private ArrayList<Language> languageList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_project_layout);
        Intent intent = getIntent();
        tokenId = intent.getStringExtra("_TOKEN_");
        projectId = intent.getStringExtra("ID_PROJECT");
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
        if (isNetworkConnected())
            app.getNet().projectHome(projectId, tokenId);
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

    private void createGraph(ProjectsIdResponse netObjects) {
        hasLabels = !hasLabels;
        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }
        generateData(netObjects);
    }

    @Override
    public void getList(Integer branch, Integer dev, Integer filter, Integer leng) {
        filter_id = filter;
        String lenguage = "";
        if (leng == 0)
            lenguage = "Ruby";
        else if (leng == 1)
            lenguage = "JS";

        if (isNetworkConnected() == true) {
            dialog.show();
            if (branch == -5 && dev > 0)
                app.getNet().projectFilter(projectId, tokenId, null, dev, lenguage);
            else if (dev == -5 && branch > 0)
                app.getNet().projectFilter(projectId, tokenId, branch, null, lenguage);
            else if (branch == -5 && dev == -5)
                app.getNet().projectFilter(projectId, tokenId, null, null, lenguage);
            else if (dev != -5 & branch != -5)
                app.getNet().projectFilter(projectId, tokenId, branch, dev, lenguage);
        } else {
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

    }

    private void generateData(ProjectsIdResponse netObjects) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                languageList = netObjects.getLanguages();
                // RB = netObjects.getLanguages().get(0).getPercentage();
                // JS = netObjects.getLanguages().get(1).getPercentage();
                // CSS = netObjects.getLanguages().get(2).getPercentage();
                // HTML = netObjects.getLanguages().get(3).getPercentage();
                lines = new ArrayList<Line>();
                lines.clear();

                fillData(netObjects);

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

                data.setBaseValue(Float.MAX_EXPONENT);

                chart.setLineChartData(data);
                final Viewport v = new Viewport(chart.getMaximumViewport());
                v.top = v.top + 1;
                v.left = v.left - 1;
                v.right = v.right + 1;
                chart.setMaximumViewport(v);
                chart.setCurrentViewport(v);

                h.sendEmptyMessage(STATUS_COMPLETE);
            }
        });
        t.start();
    }

    private void fillData(ProjectsIdResponse netObjects) {
        int color = 0;
        for (int i = 0; i < netObjects.getBranches().size(); i++) {
            values = new ArrayList<PointValue>();
            for (int j = 0; j < netObjects.getBranches().get(i).getCommits().size(); j++) {
                if (filter_id == 0)
                    values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getAnalytics().get(0).getScore()));
                if (filter_id == 1)
                    values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getAnalytics().get(0).getDuplications()));
                if (filter_id == 2)
                    values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getAnalytics().get(0).getSmells()));
            }
            Line line = new Line(values);
            if (color >= 7)
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
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case Net.HOME_PROJECT:
                dialog.dismiss();
                fragmentDialog.getListner(this, (HomeResponse) NetObjects);
                fragmentDialog.setCancelable(false);
                fragmentDialog.show(getFragmentManager(), "Filters");
                break;
            case Net.FILT_PROJECT:
                createGraph((ProjectsIdResponse) NetObjects);
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
                    app.getNet().projectHome(projectId, tokenId);
                else {
                    Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                break;
            case R.id.pieChart:
                Intent intent = new Intent(this, PieProjectActivity.class);
                intent.putExtra("LIST", languageList);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int evetId, Object NetObjects) {
        dialog.dismiss();
        Toast.makeText(this, "Check your internet conection please", Toast.LENGTH_SHORT).show();
        finish();
    }

}
