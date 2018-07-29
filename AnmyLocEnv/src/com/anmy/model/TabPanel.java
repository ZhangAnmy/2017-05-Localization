package com.anmy.model;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.anmy.sql.SqlOperation;
import com.anmy.utils.ComboBoxUtil;

import java.util.Date;

public class TabPanel extends JPanel implements ActionListener
{
	private JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
	private JPanel leftPanel = new JPanel();
	
	private JSplitPane jsp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	private JPanel topPanel = new JPanel();
	
	private static JLabel wsnType;
	private static JComboBox<String> comboTypeBox;//串口列表
	
	private static final String dataFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat df = new SimpleDateFormat(dataFormat);

	Vector<Vector> search = new Vector<Vector>();
	private Vector getselect(List<ValueInfo> list) {
		Iterator<ValueInfo> it=list.iterator();
        while(it.hasNext()){
        	ValueInfo valueInfo = it.next();
        	Vector<String> v = new Vector<String>();
        	v.add(String.valueOf(valueInfo.getNodeID()));
        	v.add(String.valueOf(valueInfo.getValue()));
        	v.add(String.valueOf(valueInfo.getDate()));
        	search.add(v);
       } 
		return search;
	}
	
	Vector<String> head = new Vector<String>();
	{
		head.add("节点编号");
		head.add("取值");
		head.add("采集时间");
	}
	
	Vector<Vector> data = new Vector<Vector>();
	DefaultTableModel dtm = new DefaultTableModel(data,head);
	JTable jtable = new JTable(dtm); //表格
	private JScrollPane bomSPane = new JScrollPane(jtable);
	private JLabel tipLabel;
	
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;
	
	private JTextField  startTimeTxt;
	private JTextField  endTimeTxt;
	
	private JButton queryBtn; 
	private JButton showChartBtn; 
	private JButton saveBtn; 
	private JButton refreshBtn; 
	String name="";
	
	public TabPanel(String name)
	{
		this.name = name;
		this.setLayout(new GridLayout(1, 1));
		leftPanel.setLayout(new GridLayout(1, 1));
		jSplitPane.setDividerLocation(650);
		jSplitPane.setDividerSize(4);
		
		jSplitPane.setLeftComponent(leftPanel);
		this.add(jSplitPane);
		
		jsp2.setDividerLocation(450);//分割图片与表格
		jsp2.setDividerSize(4);
		
		topPanel.setLayout(null);
		jsp2.setTopComponent(topPanel);
		jsp2.setBottomComponent(bomSPane);
		leftPanel.add(jsp2);
		
		tipLabel = new JLabel("请输入查询时间，格式为：yyyy-MM-dd HH:mm:ss");
		topPanel.add(tipLabel);
		tipLabel.setBounds(30, 10, 400, 25);
		
		startTimeLabel = new JLabel("开始时间");
		endTimeLabel = new JLabel("结束时间");
		topPanel.add(startTimeLabel);
		topPanel.add(endTimeLabel);
		startTimeLabel.setBounds(30, 50, 70, 25);
		endTimeLabel.setBounds(30, 90, 70, 25);
		
		startTimeTxt = new JTextField();
		endTimeTxt = new JTextField();
		topPanel.add(startTimeTxt);
		topPanel.add(endTimeTxt);
		
		startTimeTxt.setBounds(105, 50, 160, 25);
		endTimeTxt.setBounds(105, 90, 160, 25);
		
		queryBtn = new JButton("查询(表格)");
		showChartBtn = new JButton("查询(折线图)");
		queryBtn.addActionListener(this);
		showChartBtn.addActionListener(this);
		topPanel.add(queryBtn);
		topPanel.add(showChartBtn);
		queryBtn.setBounds(300, 50, 120, 25);
		showChartBtn.setBounds(300, 90, 120, 25);
		
		saveBtn = new JButton("保存图片");
		topPanel.add(saveBtn);
		saveBtn.setBounds(470, 50, 85, 25);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChart();
			}
		});
		
		if(name.equals("AllNode"))
		{
			wsnType = new JLabel("节点类型");
			comboTypeBox = ComboBoxUtil.getTypeComboBox();
			
			topPanel.add(wsnType);
			topPanel.add(comboTypeBox);
			wsnType.setBounds(580, 65, 80, 25);
			comboTypeBox.setBounds(670, 65, 100, 25);
			
			refreshBtn = new JButton("刷新图片");
			topPanel.add(refreshBtn);
			refreshBtn.setBounds(470, 90, 85, 25);
			refreshBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					refreshChart();
				}
			});
		}
	}

	JFreeChart chart=null;
	List<ValueInfo> results;
	
	String startTime="";
	String endTime="";
	String nameNode="";
	
	public void actionPerformed(ActionEvent e) 
	{
		startTime=startTimeTxt.getText().trim();
		endTime=endTimeTxt.getText().trim();
		nameNode = (String)comboTypeBox.getSelectedItem();
		
		if(startTime.equals("") || endTime.equals(""))
		{
			JOptionPane.showMessageDialog(null, "请输入正确的时间段", "消息", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if(e.getSource() == queryBtn)
		{
			dtm.setRowCount(0);
			Vector<?> results = null;
			if(name.equals("WiFi")){
				results = getselect(SqlOperation.selectData(name,startTime, endTime));
			}
			else if(name.equals("BlueTooth")){
				results = getselect(SqlOperation.selectData(name,startTime, endTime));
			}
			else if(name.equals("WSN")){
				results = getselect(SqlOperation.selectData(name,startTime, endTime));
			}
			else if(name.equals("AllNode"))
			{
				if(startTime.equals("") || endTime.equals(""))
				{
					JOptionPane.showMessageDialog(null, "请输入正确的时间段", "消息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				if(nameNode.equals("WiFi")){
					results = getselect(SqlOperation.selectData(nameNode,startTime, endTime));
				}
				else if(nameNode.equals("BlueTooth")){
					
					results = getselect(SqlOperation.selectData(nameNode,startTime, endTime));
				}
				else{
					results = getselect(SqlOperation.selectData(nameNode,startTime, endTime));
				}
			}
			dtm.setDataVector(results,head);//更新table
		    jtable.updateUI();
		    jtable.repaint();
		}
		//绘制折线图
		else
		{
			if(name.equals("WiFi"))
			{
				results = SqlOperation.selectData(name,startTime, endTime);
				DefaultCategoryDataset dataset = JChartUtil.createDataset(results, "WiFi");
				chart = JChartUtil.createChart(dataset, "时间","WiFi",true);
				ImageIcon icon = null;
				try {
					icon = JChartUtil.createImageIcon(chart);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JLabel Imagelabel=new JLabel(icon);
				topPanel.add(Imagelabel);
				Imagelabel.setBounds(30, 120, 800, 390);
				System.out.println("repaint WiFi.icon.."+icon);
				topPanel.repaint();
				topPanel.remove(8);//删除添加的图形区域，图形区域在component中list为11
				topPanel.add(Imagelabel);
			}
			else if(name.equals("BlueTooth")){
				System.out.println("drawing Bluetooth...");
				results = SqlOperation.selectData(name,startTime, endTime);
				DefaultCategoryDataset dataset = JChartUtil.createDataset(results, "BlueTooth");
				chart = JChartUtil.createChart(dataset, "时间", "Bluetooth",true);
				ImageIcon icon = null;
				try {
					icon = JChartUtil.createImageIcon(chart);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JLabel Imagelabel=new JLabel(icon);
				topPanel.add(Imagelabel);
				Imagelabel.setBounds(30, 120, 800, 390);
				System.out.println("repaint BT...");
				topPanel.repaint();
				topPanel.remove(8);//删除添加的图形区域，图形区域在component中list为8
				topPanel.add(Imagelabel);
			}
			else if(name.equals("WSN")){
				results = SqlOperation.selectData("WSN",startTime, endTime);
				DefaultCategoryDataset dataset = JChartUtil.createDataset(results, "WSN");
				chart = JChartUtil.createChart(dataset, "时间","WSN",true);
				ImageIcon icon = null;
				try {
					icon = JChartUtil.createImageIcon(chart);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JLabel Imagelabel=new JLabel(icon);
				topPanel.add(Imagelabel);
				Imagelabel.setBounds(30, 120, 800, 390);
				System.out.println("repaint WSN...");
				topPanel.repaint();
				topPanel.remove(8);//删除添加的图形区域，图形区域在component中list为8
				topPanel.add(Imagelabel);
			}
			else 
			{
				results = SqlOperation.selectData(nameNode,startTime, endTime);
				DefaultCategoryDataset dataset = JChartUtil.createDataset(results, nameNode);
				chart = JChartUtil.createChart(dataset, "时间",nameNode,true);
				ImageIcon icon = null;
				try {
					icon = JChartUtil.createImageIcon(chart);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				JLabel Imagelabel = new JLabel(icon);
				Imagelabel.setBounds(30, 120, 800, 390);
				topPanel.add(Imagelabel);
				topPanel.repaint();
				topPanel.remove(11);//删除添加的图形区域，图形区域在component中list为11
				topPanel.add(Imagelabel);
			}
		}
	}
	
	String curTime = df.format(new Date());
	public void saveChart()
	{
		try {
			if (chart != null) {
				ChartUtilities.saveChartAsPNG(
						new File("/Users/Zhangzy/Anmy_Project/workspace/AnmyLocEnv/res/"+name+curTime+".png"), chart, 550, 250);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void refreshChart()
	{
		startTime=startTimeTxt.getText().trim();
		endTime=endTimeTxt.getText().trim();
		nameNode = (String)comboTypeBox.getSelectedItem();
		
		results = SqlOperation.selectData(nameNode,startTime, endTime);
		DefaultCategoryDataset dataset = JChartUtil.createDataset(results, nameNode);
		chart = JChartUtil.createChart(dataset, "时间",nameNode,true);
		System.out.println("refresh chart:"+chart+"--node--"+nameNode);
		ImageIcon icon = null;
		try {
			icon = JChartUtil.createImageIcon(chart);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel Imagelabel = new JLabel(icon);
		Imagelabel.setBounds(30, 120, 800, 390);
		topPanel.repaint();
		topPanel.remove(11);//删除添加的图形区域，图形区域在component中list为11
		topPanel.add(Imagelabel);
	}
}
