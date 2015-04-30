package com.ibm.rqm;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

public class BarChartBuilder extends Activity {

    private GraphicalView mChartView;
    public static String[] titles;
    public static List<double[]> values;

    public void back(View v) {

        Intent intent = new Intent();
        intent.setClass(BarChartBuilder.this, MainActivity.class);
        startActivity(intent);
        BarChartBuilder.this.finish();
    }

    public void put2bar(String[] a,double[] b1)
    {

        BarChartBuilder.titles = a;
        BarChartBuilder.values = new ArrayList<double[]>();

        BarChartBuilder.values.add(b1);
        BarChartBuilder.values.add(b1);

    }
    protected void setChartSettings(XYMultipleSeriesRenderer renderer,
                                    String title, String xTitle, String yTitle, double xMin,
                                    double xMax, double yMin, double yMax, int axesColor,
                                    int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(colors[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
                                                      List<double[]> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            double[] v = values.get(i);
            int seriesLength = v.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(v[k]);
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xy_chart);
        LinearLayout mLinear = (LinearLayout) findViewById(R.id.chart);
        mLinear.setBackgroundColor(Color.BLACK);


        int[] colors = new int[] { Color.RED, Color.BLUE };
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        setChartSettings(renderer, "柱状图示例", "月份", "数量", 0.5, 12.5, 0, 2400,
                Color.GRAY, Color.LTGRAY);
        renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
        renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setXLabelsAlign(Align.LEFT);
        renderer.setYLabelsAlign(Align.LEFT);
        renderer.setPanEnabled(true, false);
        renderer.setZoomEnabled(true);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomRate(1.1f);
        renderer.setBarSpacing(0.5f);

        if (mChartView == null) {
            mChartView = ChartFactory.getBarChartView(getApplicationContext(),
                    buildBarDataset(titles, values), renderer, Type.DEFAULT);
            renderer.setClickEnabled(true);
            //renderer.set
            mLinear.addView(mChartView, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        } else
            mChartView.repaint();
    }
}