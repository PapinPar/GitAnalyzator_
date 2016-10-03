package chi_gitanalyz.gitanalyzator.ui.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

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
        app.getNet().projectHome(PROJECT_ID, TOKEN_ID);
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
        dialog.show();
        if (branch == -5 && dev > 0)
            app.getNet().projectFilter(PROJECT_ID, TOKEN_ID, null, dev);
        else if (dev == -5 && branch > 0)
            app.getNet().projectFilter(PROJECT_ID, TOKEN_ID, branch, null);
        else if (branch == -5 && dev == -5)
            app.getNet().projectFilter(PROJECT_ID, TOKEN_ID, null, null);
        else if (dev != -5 & branch != -5)
            app.getNet().projectFilter(PROJECT_ID, TOKEN_ID, branch, dev);
    }


    private void generateData(ProjectsID netObjects) {
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values;
        int color = 0;
        lines.clear();
        for (int i = 0; i < netObjects.getBranches().size(); i++) {
            values = new ArrayList<PointValue>();
            for (int j = 0; j < netObjects.getBranches().get(i).getCommits().size() - 1; j++) {
                if (filter_id == 0)
                    values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getScore()));
                if (filter_id == 1)
                    values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getDuplications()));
                if (filter_id == 2)
                    values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getSmells()));
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
            case I_Net.PROJECT_ANALYZ:
                dialog.dismiss();
                createGraph((ProjectsID) NetObjects);
                break;
            case I_Net.HOME_PROJECT:
                dialog.dismiss();
                fragmentDialog.getListner(this, (Home) NetObjects);
                fragmentDialog.setCancelable(false);
                fragmentDialog.show(getFragmentManager(), "Filters");
                break;
            case I_Net.FILT_PROJECT:
                dialog.dismiss();
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
                app.getNet().projectHome(PROJECT_ID, TOKEN_ID);
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
