package com.anmy.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.anmy.model.ValueInfo;

public class CircleTest extends ApplicationFrame {
	public CircleTest(String title) {
		super(title);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 500));
		setContentPane(jpanel);
	}

	static JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart("Path Chart Example", "X", "Y", xydataset,
				PlotOrientation.VERTICAL, false, false, false);
		XYPlot xyplot = jfreechart.getXYPlot();
		xyplot.getDomainAxis().setAutoRange(false);
		xyplot.getDomainAxis().setDefaultAutoRange(new Range(0D, 20D));
		xyplot.setDrawingSupplier(getSupplier());
		xyplot.getDomainAxis().setLowerMargin(0D);
		xyplot.getDomainAxis().setUpperMargin(0D);
		return jfreechart;
	}

	public static JPanel createDemoPanel() {
		XYDataset dataset = new SimpleXYDataset();
		JFreeChart jfreechart = createChart(dataset);
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[]) {
		CircleTest circle = new CircleTest("Path Chart Example");
		circle.pack();
		RefineryUtilities.centerFrameOnScreen(circle);
		circle.setVisible(true);
	}

	public static DefaultDrawingSupplier getSupplier() {
		return new DefaultDrawingSupplier(new Paint[] { Color.black, Color.black },
				DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
	}
	
	static XYDataset createDataset(List<?> list, String verticalName) {

		XYSeries xySeries = new XYSeries(verticalName,false);
		for(Object value : list){
			  ValueInfo valueInfo = (ValueInfo) value;
			  xySeries.add(valueInfo.getlocXValue(), valueInfo.getlocYValue());
			  
			  System.out.println("BlueTooth draw tyep is:"+verticalName);
		  }
        
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(xySeries);
        return xySeriesCollection;
    }
}

class SimpleXYDataset extends AbstractXYDataset implements XYDataset {
	public SimpleXYDataset() {
		translate = 0.0D;
	}

	public double getTranslate() {
		return translate;
	}

	public void setTranslate(double d) {
		translate = d;
		notifyListeners(new DatasetChangeEvent(this, this));
	}

	public Number getX(int i, int j) {
		return new Double(-20D + translate + (double) j / 50D);
	}

	public Number getY(int i, int j) {
		if (i == 0) {
			return new Double(Math.sqrt(400D - Math.pow(-20D + translate + (double) j / 50D, 2)));
		} else {
			return new Double(-Math.sqrt(400D - Math.pow(-20D + translate + (double) j / 50D, 2)));
		}
	}

	public int getSeriesCount() {
		return 2;
	}

	public Comparable getSeriesKey(int i) {
		if (i == 0)
			return "y = sqrt(100-x*x)";
		if (i == 1)
			return "y = -sqrt(100-x*x)";
		else
			return "Error";
	}

	public int getItemCount(int i) {
		return 2001;
	}

	private double translate;
}