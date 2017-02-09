package com.hjhz.testdemo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.hjhz.testdemo.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by 陈宣宣 on 2016/6/22.
 */
public class ChartActivity extends Activity {

    private LineChartView chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chart = (LineChartView) findViewById(R.id.chart);
        chart.setInteractive(false);
        chart.setZoomType(ZoomType.HORIZONTAL);
        chart.setContainerScrollEnabled(false, ContainerScrollType.HORIZONTAL);

        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(0, 2));
        values.add(new PointValue(1, 4));
        values.add(new PointValue(2, 3));
        values.add(new PointValue(3, 4));

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        chart.setLineChartData(data);
    }
}
