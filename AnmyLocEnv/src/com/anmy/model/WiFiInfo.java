package com.anmy.model;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.anmy.sql.SqlOperation;

public class WiFiInfo extends JFrame implements MouseListener
{
	private static JPanel wifiSetPanel;
	private static JLabel startTimeLabel;
	private static JLabel endTimeLabel;
	private static JButton searchBtn;//查询按钮
	private static JButton resetBtn;//重新设置
	private static JTextField startTimeTxt;//起始查询时间
	private static JTextField endTimeTxt;
	private static JTable table;
	private static JLabel historyLabel;
	
	private static JScrollPane wifiSetSP;
	
	private static final String dataFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat df = new SimpleDateFormat(dataFormat);
	
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	String valuesearch[] = { "节点编号", "取值", "采集时间" };
	private Object[][] getselect(List list) {
		Object[][] search = new Object[list.size()][8];
		for (int i = 0; i < list.size(); i++) {
			ValueInfo valueinfo = (ValueInfo) list.get(i);
			search[i][0] = valueinfo.getNodeID();
			search[i][1] = valueinfo.getValue();
			search[i][2] = valueinfo.getDate();
		}
		return search;
	}
	
	public WiFiInfo()
	{
		super();
		System.out.println("------");
		setTitle("WiFi信息");
		setSize(600, 500);
		setLocation((width-300)/2, (height-200)/2);
		
		wifiSetPanel = new JPanel();
		startTimeLabel = new JLabel("起始时间");
		startTimeTxt = new JTextField(dataFormat);
		startTimeTxt.setEditable(true);
		startTimeTxt.addMouseListener(this);
		startTimeTxt.setName("startTime");
		
		endTimeLabel = new JLabel("结束时间");
		endTimeTxt = new JTextField(dataFormat);
		endTimeTxt.setEnabled(true);
		endTimeTxt.addMouseListener(this);
		endTimeTxt.setName("endTime");
		
		wifiSetPanel.add(startTimeLabel);
		wifiSetPanel.add(startTimeTxt);
		startTimeLabel.setBounds(25, 20, 80, 25);
		startTimeTxt.setBounds(100, 20, 150, 25);
		
		wifiSetPanel.add(endTimeLabel);
		wifiSetPanel.add(endTimeTxt);
		endTimeLabel.setBounds(25, 60, 80, 25);
		endTimeTxt.setBounds(100, 60, 150, 25);
		
		searchBtn = new JButton("查询");
		wifiSetPanel.add(searchBtn);
		searchBtn.setBounds(270, 20, 65, 25);
		
		final DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(JLabel.CENTER);
		
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String startTime=startTimeTxt.getText().trim();
				String endTime=endTimeTxt.getText().trim();
				if(startTime.equals("") || endTime.equals(""))
				{
					JOptionPane.showMessageDialog(null, "请确认输入的时间是否正确", "消息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				Object[][] results = getselect(SqlOperation.selectData("WiFi",startTime, endTime));
				table = new JTable(results, valuesearch);
				wifiSetSP.setViewportView(table);
				table.getColumn("采集时间").setPreferredWidth(150);
				table.setDefaultRenderer(Object.class, tcr);
			}
		});
		
		resetBtn = new JButton("重置");
		wifiSetPanel.add(resetBtn);
		resetBtn.setBounds(270, 60, 65, 25);
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startTimeTxt.setText("");
				endTimeTxt.setText("");
			}
		});
		
		//历史数据显示区
		wifiSetSP = new JScrollPane();
		wifiSetPanel.add(wifiSetSP);
		wifiSetSP.setBounds(380, 50, 400, 500);

		historyLabel = new JLabel("历史数据显示区");
		historyLabel.setFont(new Font("Dialog", 1, 15));
		historyLabel.setBounds(380 + 300 / 2 - 30, 20, 200, 25);
		wifiSetPanel.add(historyLabel);
		
		setVisible(true);
	}
	
	//设置时间文本框中的默认值
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		String name = component.getName();
		if (e.getButton() == MouseEvent.BUTTON1) {
			if ("startTime".equals(name) || "endTime".equals(name)) {
				JTextField time = (JTextField) component;
				if (dataFormat.equals(time.getText())) {
					time.setText("");
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}
