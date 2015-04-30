package com.ibm.rqm;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

//import com.ibm.rqm.MainFragment;
//import com.qiuzhping.achart.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.NumberFormat;
import java.util.Random;


public class PieChartBuilder extends Activity {

    private GraphicalView mChartView;

    private LinearLayout mLinear;
    public static double PieData[];

    public void back(View v) {

        Intent intent = new Intent();
        intent.setClass(PieChartBuilder.this, MainActivity.class);
        startActivity(intent);
        PieChartBuilder.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xy_chart);

        mLinear = (LinearLayout) findViewById(R.id.chart);
        mLinear.setBackgroundColor(Color.BLACK);

        if (mChartView == null) {
            int[] COLORS = new int[] { Color.RED, Color.GREEN, Color.BLUE,
                    Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.DKGRAY };


            final CategorySeries mSeries = new CategorySeries("");

            final DefaultRenderer mRenderer = new DefaultRenderer();

            double VALUE = 0;
            mRenderer.setZoomButtonsVisible(true);
            mRenderer.setStartAngle(180);
            mRenderer.setDisplayValues(true);
            mRenderer.setFitLegend(true);
            mRenderer.setLegendTextSize(10);
            mRenderer.setLegendHeight(10);
            mRenderer.setChartTitle("饼图示例");

            for (int i = 0; i < PieData.length; i++)
                VALUE += PieData[i];
            for (int i = 0; i < PieData.length; i++) {
                mSeries.add("示例 " + (i + 1), PieData[i] / VALUE);
                SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
                if (i < COLORS.length) {
                    renderer.setColor(COLORS[i]);
                } else {
                    renderer.setColor(getRandomColor());
                }
                renderer.setChartValuesFormat(NumberFormat.getPercentInstance());
                mRenderer.setChartTitleTextSize(14);
                mRenderer.addSeriesRenderer(renderer);
            }

            mChartView = ChartFactory.getPieChartView(getApplicationContext(),
                    mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mChartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = mChartView
                            .getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(getApplicationContext(),
                                "您未选择数据", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < mSeries.getItemCount(); i++) {
                            mRenderer.getSeriesRendererAt(i)
                                    .setHighlighted(
                                            i == seriesSelection
                                                    .getPointIndex());
                        }
                        mChartView.repaint();
                        Toast.makeText(
                                getApplicationContext(),
                                "您选择的是第"
                                        + (seriesSelection
                                        .getPointIndex() + 1)
                                        + " 项 "
                                        + " 百分比为  "
                                        + NumberFormat
                                        .getPercentInstance()
                                        .format(seriesSelection
                                                .getValue()),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mLinear.addView(mChartView, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        } else {
            mChartView.repaint();
        }
    }

    public void put2pie(double data[])
    {
        PieChartBuilder.PieData=data;
    }
    private int getRandomColor() {
        Random random = new Random();
        int R = random.nextInt(255);
        int G = random.nextInt(255);
        int B = random.nextInt(255);
        return Color.rgb(R, G, B);
    }
}
