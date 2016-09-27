package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.project_id.ProjectsID;
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

public class GraphActivity extends BaseActivity {

    String TOKEN_ID;
    String PROJECT_ID;

    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 2;

    double [][]randomNumbersTab = new double[6][2];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = true;
    private boolean hasLabelForSelected = true;
    private boolean pointsHaveDifferentColor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);
        Intent intent = getIntent();
        TOKEN_ID = intent.getStringExtra("_TOKEN_");
        PROJECT_ID = intent.getStringExtra("ID_PROJECT");

        chart = (LineChartView)findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        app.getNet().projectAnalyz(PROJECT_ID,TOKEN_ID);
    }

    private void createGraph(ProjectsID netObjects) {
        hasLabels = !hasLabels;

        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }
        generateValues(netObjects);

    }
    private void generateValues(ProjectsID netObjects)
    {
        //numberOfPoints = netObjects.getBranches().get(0).getCommits().size();
    /*    for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                randomNumbersTab[i][j] = netObjects.getBranches().get(i).getCommits().get(j).getSmells();
            }
        }*/
        generateData(netObjects);
    }



    private void generateData(ProjectsID netObjects) {
        List<Line> lines = new ArrayList<Line>();
        int color =0;
        for (int i = 0; i < netObjects.getBranches().size(); ++i) {
            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < netObjects.getBranches().get(i).getCommits().size(); ++j) {
                values.add(new PointValue(j, netObjects.getBranches().get(i).getCommits().get(j).getScore()));
            }

            Line line = new Line(values);
            if(color>4)
                color=0;
            line.setColor(ChartUtils.COLORS[color]);
            color++;
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
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
        switch (evetId){
            case I_Net.PROJECT_ANALYZ:
                createGraph((ProjectsID)NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {
    }
}
