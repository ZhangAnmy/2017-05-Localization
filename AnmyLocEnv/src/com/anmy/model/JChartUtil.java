package com.anmy.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ShapeUtilities;

public class JChartUtil extends ChartPanel{
	private static final long serialVersionUID = 1L;

	public JChartUtil(DefaultCategoryDataset linedataset, String xName, String verticalName,boolean showData) {
		super(createChart(linedataset, xName,verticalName,showData));
	}

	/**
	 * 曲线图
	 * 
	 * @param xName
	 *            x轴标题
	 * @param verticalName
	 *            y轴标题
	 * @param linedataset
	 *            数据集对象
	 * @param showData
	 *            是否显示各数据点及其数值
	 * @return
	 */
	public static JFreeChart createChart(DefaultCategoryDataset linedataset, String xName, String verticalName,boolean showData)
	{
		JFreeChart chart = ChartFactory.createLineChart(
				 "", //图标题
				 xName, //X轴名称
				 verticalName, //Y轴名称
				 linedataset,//数据集合
				 PlotOrientation.VERTICAL, //水平显示图像
			     true, //include legend
			     true, //tooltips
			     false //URL
			     );
		 CategoryPlot plot = chart.getCategoryPlot();
		 plot.setBackgroundPaint(Color.LIGHT_GRAY);//设置背景颜色
		 plot.setDomainGridlinePaint(Color.BLUE);//设置X轴格子线为蓝色
		 plot.setRangeGridlinePaint(Color.BLUE);//设置Y轴格子线为蓝色
		 plot.setDomainGridlinesVisible(true);//是否显示X轴格子线
	     plot.setRangeGridlinesVisible(true); //是否显示Y轴格子线
	     plot.setOutlinePaint(Color.RED);//显示外方框线为红色
	     plot.setBackgroundAlpha(0.3f); //设置背景透明度
	     
	     double unit=2d;//刻度的步长
	     NumberTickUnit ntu= new NumberTickUnit(unit);
	     CategoryAxis domainAxis = plot.getDomainAxis();
	     //x轴，横轴上的label斜显示
	     domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	     NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
	     rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	     rangeAxis.setAutoRangeIncludesZero(true);
	     rangeAxis.setUpperMargin(0.20);
//	     rangeAxis.setLabelAngle(Math.PI /2.0);//y轴标题横向显示
	     rangeAxis .setAutoTickUnitSelection(true);
//	     rangeAxis .setTickUnit(ntu);//y轴步长
	     
	   //Line实例化,获得LineAndShapeRenderer类的实例，目的是设置线形的绘制属性
    	 LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer)plot.getRenderer();
    	//设置第一条线有数据点
	    lineandshaperenderer.setSeriesShapesVisible(0, true);
	    //可以重新设置数据点颜色默认为白色方形
	    lineandshaperenderer.setUseFillPaint(true); 
	   //数据点填充颜色
		lineandshaperenderer.setFillPaint(Color.yellow);
	    //设置数据点为上三角形（x为三角距离）
		lineandshaperenderer.setSeriesShape(0,ShapeUtilities.createUpTriangle(3.0f));
		//设置每个数据点的连接线是否显示
		lineandshaperenderer.setSeriesLinesVisible(0, true);
		plot.setRenderer(lineandshaperenderer);
		
	     if (showData) 
	     {
	    	// 显示数据点
			lineandshaperenderer.setShapesVisible(true);
			lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			lineandshaperenderer.setBaseItemLabelsVisible(true);
			CategoryItemRenderer cgitem = plot.getRenderer();
			cgitem.setBaseItemLabelsVisible(true);
			cgitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
			cgitem.setBaseItemLabelFont(new Font("Dialog", 1, 10));
			plot.setRenderer(cgitem);
		}
	     return chart;
	}
	
	//生成显示图表的面板
	public static ImageIcon createImageIcon(JFreeChart chart) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		ChartUtilities.writeChartAsPNG(out, chart, 1200, 530);
		ChartUtilities.writeChartAsPNG(out, chart, 750, 380);
		byte[] buf = out.toByteArray();
		ImageIcon icon = new ImageIcon(buf);
		return icon;
	}
	
	public static DefaultCategoryDataset createDataset(List<?> list, String verticalName) {
		  DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
		  if(verticalName.equals("WiFi")){
			  String type = "WiFi";
			  for(Object value : list){
				  ValueInfo valueInfo = (ValueInfo) value;
				  linedataset.addValue(valueInfo.getValue(), type, valueInfo.getDate().substring(11,19));
				  System.out.println("wifi draw tyep is--:"+type);
			  }
		  }else if(verticalName.equals("BlueTooth")){
			  String type = "BlueTooth";
			  for(Object value : list){
				  ValueInfo valueInfo = (ValueInfo) value;
				  linedataset.addValue(valueInfo.getValue(), type, valueInfo.getDate().substring(11,19));
				  System.out.println("BlueTooth draw tyep is:"+type);
			  }
		  }else if(verticalName.equals("WSN")){
			  String type = "WSN";
			  for(Object value : list){
				  ValueInfo valueInfo = (ValueInfo) value;
				  linedataset.addValue(valueInfo.getValue(), type, valueInfo.getDate().substring(11,19));
				  System.out.println("WSN draw tyep is:"+type);
			  }
		  }
		  else{
			  String type = "Others";
			  for(Object value : list){
				  ValueInfo valueInfo = (ValueInfo) value;
				  linedataset.addValue(valueInfo.getlocYValue(), type, valueInfo.getlocXValue());
				  System.out.println("Other draw tyep is:"+type);
			  }
		  }
		   //横轴名称(列名称)
		   return linedataset;
		}
	
	@SuppressWarnings("deprecation")
	public static JFreeChart createPathChart(List<ValueInfo> list, String verticalName, boolean showData) {
		
		XYSeries xySeries = new XYSeries("Real Path",false);
		for(Object value : list){
			  ValueInfo valueInfo = (ValueInfo) value;
			  xySeries.add(valueInfo.getlocXValue(), valueInfo.getlocYValue());
		  }
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(xySeries);
		
		XYSeries xySeries1 = null;
		if(verticalName.equals("WiFi定位") || verticalName.equals("All")){
			xySeries1 = new XYSeries("WiFi",false);
			for(Object value : list){
				  ValueInfo valueInfo = (ValueInfo) value;
				  xySeries1.add(valueInfo.getwifilocXValue(), valueInfo.getwifilocYValue());
			  }
		}
		
		XYSeries xySeries2 = null;
		if(verticalName.equals("WSN定位") || verticalName.equals("All")){
			xySeries2 = new XYSeries("WSN",false);
			for(Object value : list){
				  ValueInfo valueInfo = (ValueInfo) value;
				  xySeries2.add(valueInfo.getwsnlocXValue(), valueInfo.getwsnlocYValue());
			  }
		}
		
		XYSeries xySeries3 = null;
		if(verticalName.equals("WiFi+WSN定位") || verticalName.equals("All")){
			xySeries3 = new XYSeries("WiFi+WSN",false);
			for(Object value : list){
				  ValueInfo valueInfo = (ValueInfo) value;
				  xySeries3.add(valueInfo.getbothlocXValue(), valueInfo.getbothlocYValue());
			  }
		}
        
        JFreeChart jfreechart = ChartFactory.createXYLineChart("Path Chart", "X轴坐标", "Y轴坐标", xySeriesCollection,
				PlotOrientation.VERTICAL, true, true, true);
		XYPlot xyplot = jfreechart.getXYPlot();

		 double unit=2d;//刻度的步长
	     NumberTickUnit ntu= new NumberTickUnit(unit);
	     ValueAxis yValueaxis = xyplot.getRangeAxis(); 
	     ValueAxis xValueaxis = xyplot.getDomainAxis(); 
	     //数据为整型
	     yValueaxis .setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	     // 设定显示范围,即总是显示1-20
	     yValueaxis .setLowerBound(0);
	     yValueaxis .setUpperBound(13.5);
	     
	     xValueaxis .setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	     // 设定显示范围,即总是显示1-20
	     xValueaxis .setLowerBound(0);
	     xValueaxis .setUpperBound(20.5);
	   
	     //wifi 三边定位
	     if(xySeries1 != null)
	     {
	    	 wifiDrawing(xyplot,xySeries1);
	     }
		
		//WSN 三边定位
		if (xySeries2 != null) 
		{
			wsnDrawing(xyplot,xySeries2);
		}
		
		if (xySeries3 != null) 
		{
			bothDrawing(xyplot,xySeries3);
		}
		
		if (verticalName.equals("All"))
		{
			wifiDrawing(xyplot,xySeries1);
			wsnDrawing(xyplot,xySeries2);
			bothDrawing(xyplot,xySeries3);
		}
        
		xyplot.getDomainAxis().setAutoRange(false);
		xyplot.getDomainAxis().setDefaultAutoRange(new Range(0D, 20D));
		xyplot.setDrawingSupplier(getSupplier());
		xyplot.getDomainAxis().setLowerMargin(0D);
		xyplot.getDomainAxis().setUpperMargin(0D);
		
//		 xyplot.setBackgroundPaint(Color.LIGHT_GRAY);//设置背景颜色
		 xyplot.setBackgroundPaint(Color.WHITE);//设置背景颜色
//		 xyplot.setDomainGridlinePaint(Color.BLUE);//设置X轴格子线为蓝色
//		 xyplot.setRangeGridlinePaint(Color.BLUE);//设置Y轴格子线为蓝色
//		 xyplot.setDomainGridlinesVisible(true);//是否显示X轴格子线
//	     xyplot.setRangeGridlinesVisible(true); //是否显示Y轴格子线
	     xyplot.setOutlinePaint(Color.RED);//显示外方框线为红色
	     xyplot.setBackgroundAlpha(0.3f); //设置背景透明度
	     
	    //x轴设置
	     NumberAxis domainAxis = (NumberAxis) xyplot.getDomainAxis();
	     domainAxis.setTickMarkStroke(new BasicStroke(1.6f));  // 设置坐标标记大小
	     domainAxis.setTickUnit(new NumberTickUnit(1));//设置x轴步长
	     domainAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色
	     //x轴，横轴上的label斜显示
//	     domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	     
	     //y轴设置
	     NumberAxis rangeAxis = (NumberAxis)xyplot.getRangeAxis();
	     rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	     rangeAxis.setAutoRangeIncludesZero(true);
	     rangeAxis.setUpperMargin(0.20);//上边距,防止最大的一个数据靠近了坐标轴。
	     rangeAxis.setTickMarkStroke(new BasicStroke(1.6f));  // 设置坐标标记大小
	     rangeAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色
	     rangeAxis.setTickUnit(ntu);//y轴步长,每ntu个刻度显示一个刻度值
//	     rangeAxis.setLabelAngle(Math.PI /2.0);//y轴标题横向显示
	     rangeAxis .setAutoTickUnitSelection(false);
	     
	     //Line实例化,获得LineAndShapeRenderer类的实例，目的是设置线形的绘制属性
	     XYLineAndShapeRenderer lineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
    	 if (showData) 
	     {
	    	// 显示数据点
			lineandshaperenderer.setShapesVisible(true);
			lineandshaperenderer.setBaseItemLabelsVisible(true);
			XYItemRenderer cgitem = xyplot.getRenderer();
			cgitem.setBaseItemLabelsVisible(true);
			cgitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
			cgitem.setBaseItemLabelFont(new Font("Dialog", 1, 5));
			xyplot.setRenderer(cgitem);
		}
	     return jfreechart;
	}
	
	public static DefaultDrawingSupplier getSupplier() {
		return new DefaultDrawingSupplier(new Paint[] { Color.black, Color.black },
				DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
	}
	
	//Draw WiFi positioning
	public static void wifiDrawing(XYPlot xyplot,XYSeries xySeries1)
	{
        	XYSeriesCollection xySeriesCollection1 = new XYSeriesCollection();
        	xySeriesCollection1.addSeries(xySeries1);
        	xyplot.setDataset(1, xySeriesCollection1);
    		
    		// 添加第2个Y轴
//            xyplot.setRangeAxis(1, valueaxis);//添加第二个Y轴，这里不添加，使用同一个Y轴坐标
//            xyplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);//设置Y轴坐标显示位置
//            xyplot.mapDatasetToRangeAxis(1, 1);
            
            // 修改第2条曲线显示效果
            XYLineAndShapeRenderer render2 =  new XYLineAndShapeRenderer(); 
            render2.setSeriesPaint(0, Color.pink);
            xyplot.setRenderer(1, render2);
	}
	
	//Draw WSN positioning
	public static void wsnDrawing(XYPlot xyplot,XYSeries xySeries2)
	{
		XYSeriesCollection xySeriesCollection2 = new XYSeriesCollection();
		xySeriesCollection2.addSeries(xySeries2);
		xyplot.setDataset(2, xySeriesCollection2);

		// 修改第2条曲线显示效果
		XYLineAndShapeRenderer render3 = new XYLineAndShapeRenderer();
		render3.setSeriesPaint(0, Color.BLUE);
		xyplot.setRenderer(2, render3);
	}
	
	//Draw WiFi+WSN positioning
	public static void bothDrawing(XYPlot xyplot, XYSeries xySeries3) 
	{
		XYSeriesCollection xySeriesCollection3 = new XYSeriesCollection();
		xySeriesCollection3.addSeries(xySeries3);
		xyplot.setDataset(3, xySeriesCollection3);

		// 修改第2条曲线显示效果
		XYLineAndShapeRenderer render3 = new XYLineAndShapeRenderer();
		render3.setSeriesPaint(0, Color.GREEN);
		xyplot.setRenderer(3, render3);
	}
}
