package com.anmy.login;

import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;

import com.anmy.model.JChartUtil;
import com.anmy.model.MenuItemActionListener;
import com.anmy.model.TabPanel;
import com.anmy.model.ValueInfo;
import com.anmy.sql.SqlOperation;
import com.anmy.utils.ComboBoxUtil;

public class MonitorFrame extends JFrame implements MouseListener
{
	private JTabbedPane jTabbedPane;
	private static final String dataFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat df = new SimpleDateFormat(dataFormat);
	
	private static final JDesktopPane DESKTOP_PANE = new JDesktopPane();
	public static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	/**
	 * 系统设置部分
	 */
	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	
	private static JPanel sysSetPanel;
	private static JLabel serialPort;
	private static JComboBox<String> comboBox;//串口列表
	private static JComboBox<String> comboAlgBox;//定位方法列表
	private static JLabel sysBaudLabel;
	private static JLabel algLabel;
	private static JLabel startTimeLabel;
	private static JLabel endTimeLabel;
	private static JLabel lightLabel;
	private static JTextField sysBaudTxt;//波特率
	private static JTextField startTimeTxt;//起始查询时间
	private static JTextField endTimeTxt;
	
	private static JButton searchBtn;//查询按钮
//	private static JButton resetBtn;//重新设置
	private static JButton startCtrlBtn;
	private static JButton closeCtrlBtn;
	private static JButton clearCtrlBtn;
	private static JLabel locLabel;
	private static JLabel rssLabel;
	private static JLabel ImageLabel;
	private static JTextField locTxt;
	private static JTextField rssTxt;
	
	private static JScrollPane showPane;
	
	private static TabPanel wifiSetPanel;
	private static TabPanel btSetPanel;
	private static TabPanel wsnSetPanel;
	private static TabPanel allSetPanel;
	private static JTable jtable;
	
	String valuesearch[] = { "ID","X轴坐标", "Y轴坐标", "RSS","时间" ,"WiFi定位X","WiFi定位Y"};
	Object[][] search=null;
	
	private Object[][] getselect(List list) {
		search = new Object[list.size()][10];
		for (int i = 0; i < list.size(); i++) {
			ValueInfo valueinfo = (ValueInfo) list.get(i);
			search[i][0] = valueinfo.getID();
			search[i][1] = valueinfo.getlocXValue();
			search[i][2] = valueinfo.getlocYValue();
			search[i][3] = valueinfo.getrssValue();
			search[i][4] = valueinfo.getDate();
			search[i][5] = valueinfo.getwifilocXValue();
			search[i][6] = valueinfo.getwifilocYValue();
		}
		return search;
	}
	
//	JTable jtable = new JTable(search,valuesearch);
//	private JScrollPane tblSPane = new JScrollPane(jtable);
	
	public static void main(String[] args) 
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			MonitorLoginIFrame mlf = new MonitorLoginIFrame();
			mlf.setTitle("定位系统");
			mlf.pack();
			mlf.setVisible(true);
			mlf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mlf.setBounds(400, 200, 300, 250);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void addIFrame(JInternalFrame iframe)
	{
		DESKTOP_PANE.add(iframe);
	}
	
	private JMenuBar jmb;
	
	public MonitorFrame()
	{
		super();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("定位系统");
		setLocationByPlatform(true);
		setLocationRelativeTo(null);
		setSize(900, 710);
		
		initSysSet();
		
		wifiSetPanel = new TabPanel("WiFi");
		btSetPanel = new TabPanel("BlueTooth");
		wsnSetPanel = new TabPanel("WSN");
		allSetPanel = new TabPanel("AllNode");
		
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(jTabbedPane);
		jTabbedPane.addTab("参数设置", null, sysSetPanel, null);
		jTabbedPane.addTab("WiFi", null, wifiSetPanel, null);
		jTabbedPane.addTab("BlueTooth", null, btSetPanel, null);
		jTabbedPane.addTab("WSN", null, wsnSetPanel, null);
		jTabbedPane.addTab("AllNode", null, allSetPanel, null);
		
		jmb = new JMenuBar();
		setJMenuBar(jmb);
		jmb.add(createMenu("系统设置"));
		jmb.add(createMenu("查询数据"));
		
	    //为“系统设置”添加子菜单和菜单项:更改口令，用户信息,退出系统
		JMenu sysDataMenu = getMenuName("系统设置");
		sysDataMenu.add(createMenuItem("更改口令"));
		sysDataMenu.add(createMenuUserInfo());//"用户信息"
		sysDataMenu.add(createMenuItem("退出系统"));
		
		//为“系统设置”添加子菜单和菜单项
		JMenu sysQuryMenu = getMenuName("查询数据");
		sysQuryMenu.add(createMenuItem("WiFi"));
		sysQuryMenu.add(createMenuItem("BlueTooth"));
		sysQuryMenu.add(createMenuItem("WSN"));
	}
	
	private JMenuItem createMenuUserInfo() 
	{
		JMenu userModInfo = new JMenu("用户信息");
		userModInfo.add(createMenuItem("增加用户"));
		userModInfo.add(createMenuItem("用户修改与删除"));
		return userModInfo;
	}

	private JMenuItem createMenuItem(String menuItemName) 
	{
		JMenuItem jmi = new JMenuItem(menuItemName);
		jmi.addActionListener(new MenuItemActionListener());
		return jmi;
	}

	private JMenu getMenuName(String nemuName) 
	{
		JMenu menu=null;
		for(int i=0;i<jmb.getMenuCount();i++)
		{
			menu = jmb.getMenu(i);
			if(menu.getText().equals(nemuName))
			{
				return menu;
			}
		}
		return null;
	}

	private JMenu createMenu(String menu_name) 
	{
		/*
         * 创建menu并添加到menuBar
         */
		JMenu menu = new JMenu(menu_name);
		return menu;
	}
	
	private void initSysSet() 
	{
		sysSetPanel = new JPanel();
		sysSetPanel.setLayout(null);

		serialPort = new JLabel("串口号");
		comboBox = ComboBoxUtil.getComboBox();
		
		sysBaudLabel = new JLabel("波特率");
		sysBaudTxt = new JTextField();
		
		startCtrlBtn = new JButton("开始定位");
		closeCtrlBtn = new JButton("关闭定位");
		clearCtrlBtn = new JButton("重置参数");
		
		sysSetPanel.add(comboBox);
		sysSetPanel.add(serialPort);
		serialPort.setBounds(25, 20, 50, 25);
		comboBox.setBounds(80, 20, 80, 25);
		
		sysSetPanel.add(sysBaudLabel);
		sysSetPanel.add(sysBaudTxt);
		sysBaudLabel.setBounds(175, 20, 50, 25);
		sysBaudTxt.setBounds(230, 20, 80, 25);
		
		sysSetPanel.add(startCtrlBtn);
		sysSetPanel.add(closeCtrlBtn);
		sysSetPanel.add(clearCtrlBtn);
		
		startCtrlBtn.setBounds(25, 60, 85, 25);
		startCtrlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "开始定位", "消息", JOptionPane.INFORMATION_MESSAGE);
				buttonActionPerformed(e);
			}
		});
		
		closeCtrlBtn.setBounds(125, 60, 85, 25);
		closeCtrlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "关闭定位", "消息", JOptionPane.INFORMATION_MESSAGE);
				buttonActionPerformed(e);
			}
		});
		
		clearCtrlBtn.setBounds(225, 60, 85, 25);
		clearCtrlBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sysBaudTxt.setText("");
				comboBox.setSelectedIndex(0);
				return;
			}
		});
		
		startTimeLabel = new JLabel("开始时间");
		startTimeTxt = new JTextField(dataFormat);
		startTimeTxt.setEditable(true);
		startTimeTxt.addMouseListener(this);
		startTimeTxt.setName("startTime");

		endTimeLabel = new JLabel("结束时间");
		endTimeTxt = new JTextField(dataFormat);
		endTimeTxt.setEnabled(true);
		endTimeTxt.addMouseListener(this);
		endTimeTxt.setName("endTime");

		sysSetPanel.add(startTimeLabel);
		sysSetPanel.add(startTimeTxt);
		startTimeLabel.setBounds(545, 5, 70, 25);
		startTimeTxt.setBounds(610, 5, 160, 25);

		sysSetPanel.add(endTimeLabel);
		sysSetPanel.add(endTimeTxt);
		endTimeLabel.setBounds(545, 35, 70, 25);
		endTimeTxt.setBounds(610, 35, 160, 25);
		
		algLabel = new JLabel("定位结果");
		comboAlgBox = ComboBoxUtil.getAlgComboBox();

		sysSetPanel.add(algLabel);
		sysSetPanel.add(comboAlgBox);
		algLabel.setBounds(545, 65, 70, 25);
		comboAlgBox.setBounds(610, 65, 115, 25);
		
		searchBtn = new JButton("查询");
		sysSetPanel.add(searchBtn);
		searchBtn.setBounds(740, 65, 60, 25);
		
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFreeChart chart=null;
				List<ValueInfo> results;
				
				String algName=(String)comboAlgBox.getSelectedItem();
				String startTime=startTimeTxt.getText().trim();
				String endTime=endTimeTxt.getText().trim();
				
				if(startTime.equals("") || endTime.equals(""))
				{
					JOptionPane.showMessageDialog(null, "请输入正确的时间段", "消息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				final DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 创建table
				tcr.setHorizontalAlignment(JLabel.CENTER);
				
				if(algName.equals("真实路径")){
					results = SqlOperation.selectLoctValue(startTime, endTime);
					
					Object[][] result=getselect(results);
					jtable = new JTable(result,valuesearch);
					showPane.setViewportView(jtable);
					jtable.getColumn("时间").setPreferredWidth(150);
					jtable.setDefaultRenderer(Object.class, tcr);
					
				    //画折线图
//    				DefaultCategoryDataset dataset = JChartUtil.createDataset(results, algName);
//					chart = JChartUtil.createChart(dataset,"x轴坐标", "y轴坐标",true);
					
//					XYDataset dataset = JChartUtil.createPathDataset(results, algName);
    				chart = JChartUtil.createPathChart(results, algName, false);//false doesn't display the dot.
    				
    				ImageIcon icon = null;
    				try {
    					icon = JChartUtil.createImageIcon(chart);
    				} catch (IOException e1) {
    					e1.printStackTrace();
    				}
    				JLabel Imagelabel = new JLabel(icon);
    				Imagelabel.setBounds(30, 120, 800, 390);
    				sysSetPanel.add(Imagelabel);
    				sysSetPanel.repaint();
    				sysSetPanel.remove(20);//清除之前的图片组件（list 为20），否则图片会累加，刷新后只会显示第一次的结果。
    				sysSetPanel.add(Imagelabel);
				}
				else if(algName.equals("WiFi定位")){
					results = SqlOperation.selectLoctValue(startTime, endTime);
					
					Object[][] result=getselect(results);
					jtable = new JTable(result,valuesearch);
					showPane.setViewportView(jtable);
					jtable.getColumn("时间").setPreferredWidth(150);
					jtable.setDefaultRenderer(Object.class, tcr);
					
				    //画折线图
//					XYDataset dataset = JChartUtil.createPathDataset(results, algName);
    				chart = JChartUtil.createPathChart(results, algName,true);
    				
    				ImageIcon icon = null;
    				try {
    					icon = JChartUtil.createImageIcon(chart);
    				} catch (IOException e1) {
    					e1.printStackTrace();	
    				}
    				JLabel Imagelabel = new JLabel(icon);
    				Imagelabel.setBounds(30, 120, 800, 390);
    				sysSetPanel.add(Imagelabel);
    				sysSetPanel.repaint();
    				sysSetPanel.remove(20);//清除之前的图片组件（list 为20），否则图片会累加，刷新后只会显示第一次的结果。
    				sysSetPanel.add(Imagelabel);
				}
				else if(algName.equals("WSN定位")){
					results = SqlOperation.selectLoctValue(startTime, endTime);
					
					Object[][] result=getselect(results);
					jtable = new JTable(result,valuesearch);
					showPane.setViewportView(jtable);
					jtable.getColumn("时间").setPreferredWidth(150);
					jtable.setDefaultRenderer(Object.class, tcr);
					
				    //画折线图
//					XYDataset dataset = JChartUtil.createPathDataset(results, algName);
    				chart = JChartUtil.createPathChart(results, algName,true);
    				
    				ImageIcon icon = null;
    				try {
    					icon = JChartUtil.createImageIcon(chart);
    				} catch (IOException e1) {
    					e1.printStackTrace();	
    				}
    				JLabel Imagelabel = new JLabel(icon);
    				Imagelabel.setBounds(30, 120, 800, 390);
    				sysSetPanel.add(Imagelabel);
    				sysSetPanel.repaint();
    				sysSetPanel.remove(20);//清除之前的图片组件（list 为20），否则图片会累加，刷新后只会显示第一次的结果。
    				sysSetPanel.add(Imagelabel);	
				}
				
				else if(algName.equals("WiFi+WSN定位")){
					results = SqlOperation.selectLoctValue(startTime, endTime);
					
					Object[][] result=getselect(results);
					jtable = new JTable(result,valuesearch);
					showPane.setViewportView(jtable);
					jtable.getColumn("时间").setPreferredWidth(150);
					jtable.setDefaultRenderer(Object.class, tcr);
					
				    //画折线图
//					XYDataset dataset = JChartUtil.createPathDataset(results, algName);
    				chart = JChartUtil.createPathChart(results, algName,true);
    				
    				ImageIcon icon = null;
    				try {
    					icon = JChartUtil.createImageIcon(chart);
    				} catch (IOException e1) {
    					e1.printStackTrace();	
    				}
    				JLabel Imagelabel = new JLabel(icon);
    				Imagelabel.setBounds(30, 120, 800, 390);
    				sysSetPanel.add(Imagelabel);
    				sysSetPanel.repaint();
    				sysSetPanel.remove(20);//清除之前的图片组件（list 为20），否则图片会累加，刷新后只会显示第一次的结果。
    				sysSetPanel.add(Imagelabel);	
				}
				//Open all positioning way
				else{
//					JOptionPane.showMessageDialog(null, "打开所有定位结果", "消息", JOptionPane.INFORMATION_MESSAGE);
					results = SqlOperation.selectLoctValue(startTime, endTime);
					
					Object[][] result=getselect(results);
					jtable = new JTable(result,valuesearch);
					showPane.setViewportView(jtable);
					jtable.getColumn("时间").setPreferredWidth(150);
					jtable.setDefaultRenderer(Object.class, tcr);
					
				    //画折线图
//					XYDataset dataset = JChartUtil.createPathDataset(results, algName);
    				chart = JChartUtil.createPathChart(results, algName,true);
    				
    				ImageIcon icon = null;
    				try {
    					icon = JChartUtil.createImageIcon(chart);
    				} catch (IOException e1) {
    					e1.printStackTrace();	
    				}
    				JLabel Imagelabel = new JLabel(icon);
//    				Imagelabel.setBounds(30, 120, 1200, 530);
    				Imagelabel.setBounds(30, 120, 800, 390);
    				sysSetPanel.add(Imagelabel);
    				sysSetPanel.repaint();
    				sysSetPanel.remove(20);//清除之前的图片组件（list 为20），否则图片会累加，刷新后只会显示第一次的结果。
    				sysSetPanel.add(Imagelabel);
				}
			}
		});
		
		lightLabel = new JLabel("定位结果显示区");
		lightLabel.setFont(new Font("Dialog",1,15));
		lightLabel.setBounds(400, 100, 200, 25);
		sysSetPanel.add(lightLabel);

		showPane = new JScrollPane();
		sysSetPanel.add(showPane);
		showPane.setBounds(0, 510, 890, 140);//Show table
		
		String loc="20,20";
		String rss="-20";
		
		locLabel = new JLabel("当前位置(x,y)");
		locTxt = new JTextField(loc,10);
		
		rssLabel = new JLabel("当前RSS(dbm)");
		rssTxt = new JTextField(rss,10);
		
		sysSetPanel.add(locLabel);
		sysSetPanel.add(rssLabel);
		sysSetPanel.add(locTxt);
		sysSetPanel.add(rssTxt);
		
		locLabel.setBounds(330, 20, 85, 25);
		locTxt.setBounds(435,20,90,25);
		
		rssLabel.setBounds(330,60,85,25);
		rssTxt.setBounds(435,60,90,25);
	}

	//更新位置状态信息
	public void updateInfo(String loc, String rss, ImageIcon icon) {
		locTxt.setText(loc);
		rssTxt.setText(rss);
		
//		ImageLabel.setIcon(icon);
//		sysSetPanel.repaint();
	}
	
	//执行打开和关闭定位事件
	private void buttonActionPerformed(java.awt.event.ActionEvent e) {
		SensorThread st = new SensorThread("anmy");
		
		Thread thread = null;
		thread = new Thread(new Runnable() {
            private Float locX=0.0f;
            private Float locY=0.0f;
            private String loc="";
            private String rss="";
            private String date="";
            private ImageIcon icon1=null;
            
			public void run() 
            {
            	while(true &&(!Thread.currentThread().isInterrupted()))
        		{
        			try {
        				List<?> results=SqlOperation.selectCurrentValue();//查询当前位置状态信息
        				if(results.size()!=0)
        				{
        					ValueInfo valueinfo = (ValueInfo) results.get(0);
            				locX = valueinfo.getlocXValue();
            				locY = valueinfo.getlocXValue();
            				rss = valueinfo.getrssValue();
            				date = valueinfo.getDate();
        				}
        				
        				loc = locX+","+locY;
        				
        				//Below only for testing the interface refresh or not..
        				if(locX==2.00 && locY==2.00)
        				{
        					icon1 = new ImageIcon("/Users/Zhangzy/Anmy_Project/workspace/AnmyLocEnv/res/green.jpeg");
        					System.out.println("--2.0--");
        				}
        				else if(locX==3.00 && locY==3.00)
        				{
        					icon1 = new ImageIcon("/Users/Zhangzy/Anmy_Project/workspace/AnmyLocEnv/res/green.jpeg");
        					System.out.println("--3.0--");
        				}
        				
        				if ((rss != null || !rss.equals(""))) 
						{
							updateInfo(loc, rss, icon1);
						}
        				
						Thread.sleep(5000);
        				
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		}
            }
        });
		
		if(e.getActionCommand().equals("开始定位"))
		{
			st.start();//开始读入读数据，并写入数据库
			thread.start();
			System.out.println("start the threads..");
		}
		
		if(e.getActionCommand().equals("关闭定位"))
		{
			if(thread != null)
			{
				thread.interrupt();
				System.out.println("stop the thread..");
			}
			
			if(st!=null)
			{
				st.interrupt();
				System.out.println("stop the st..");
			}
		}
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