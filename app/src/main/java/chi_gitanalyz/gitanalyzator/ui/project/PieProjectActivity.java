package chi_gitanalyz.gitanalyzator.ui.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.ui.adapter.MyPieModel;
import chi_gitanalyz.gitanalyzator.ui.adapter.PieAdapter;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by Papin on 10.10.2016.
 */

public class PieProjectActivity extends Activity {

    private Float HTML, CSS, JS, RB;

    private ArrayList<MyPieModel> product;

    private PieChartView chart;
    private ListView listView;
    private PieChartData data;
    private PieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart_language);
        Intent intent = getIntent();
        HTML = intent.getFloatExtra("HTML", 0);
        CSS = intent.getFloatExtra("CSS", 0);
        JS = intent.getFloatExtra("JS", 0);
        RB = intent.getFloatExtra("RB", 0);

        product = new ArrayList<MyPieModel>();
        adapter = new PieAdapter(this, product);
        listView = (ListView) findViewById(R.id.pieList);
        listView.setAdapter(adapter);

        setChart();

    }

    private void setChart() {

        final PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        mPieChart.clearChart();
        mPieChart.setInnerValueString("34");
        mPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPieChart.setRotation(mPieChart.getRotation() + 23);
                Log.d("click", "+");
            }
        });

        mPieChart.addPieSlice(new PieModel("HTNL", HTML, ColorsUtilis.COLOR_Cyan));
        mPieChart.addPieSlice(new PieModel("JS", JS, ColorsUtilis.COLOR_LightSlateBlue));
        mPieChart.addPieSlice(new PieModel("CSS", CSS, ColorsUtilis.COLOR_DeepPink));
        mPieChart.addPieSlice(new PieModel("RUBY", RB, ColorsUtilis.COLOR_DarkGreen));

        product.add(new MyPieModel(ColorsUtilis.COLOR_Cyan,"HTML",HTML));
        product.add(new MyPieModel(ColorsUtilis.COLOR_LightSlateBlue,"JS",JS));
        product.add(new MyPieModel(ColorsUtilis.COLOR_DeepPink,"CSS",CSS));
        product.add(new MyPieModel(ColorsUtilis.COLOR_DarkGreen,"RUBY",RB));

        adapter.notifyDataSetChanged();
        mPieChart.startAnimation();
    }

}