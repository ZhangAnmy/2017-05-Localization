package com.anmy.model;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.anmy.sql.SqlOperation;

public class ModDelUser extends JFrame
{
	private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	private JTextField text_uer;
	private JTextField loginpwd_1;
	private Object[][] users;
	
	private JRadioButton jradiobut1,jradiobut2;
	private ButtonGroup butgroup = new ButtonGroup();
	private String[] strUser = {"用户姓名", "密码","管理员"};
	public Object[][] getAllUsers(List userlist)
	{
		users = new Object[userlist.size()][strUser.length];
		for(int i=0;i<userlist.size();i++)
		{
			User user = (User) userlist.get(i);
			users[i][0]=user.getUserName();
			users[i][1]=user.getPass();
			String admin="是";
			String test = user.getAdmin();
			System.out.println("test is:"+test);;
			if(user.getAdmin().equals("否"))
			{
				admin = "否";
			}
			users[i][2]=admin;
		}
		return users;
	}
	
	public ModDelUser()
	{
		super();
		setTitle("修改与删除用户");
		setBounds(100, 100, 500, 300);
		setLocation((width-600)/2,(height-500)/2);
		setVisible(true);
		
		final JScrollPane jspanel = new JScrollPane();
		jspanel.setPreferredSize(new Dimension(400, 120));
		getContentPane().add(jspanel, BorderLayout.NORTH);
		
		users = getAllUsers(SqlOperation.selectUser());
		final JTable table = new JTable(users,strUser);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				String username,pass,admin;
				int sltRow = table.getSelectedRow();//返回选中的行，如果没有行被选中，则返回-1
				username = table.getValueAt(sltRow, 0).toString().trim();
				pass = table.getValueAt(sltRow, 1).toString().trim();
				if(table.getValueAt(sltRow, 2).toString().trim().equals("是"))
				{
					jradiobut1.setSelected(true);
				}
				else
				{
					jradiobut2.setSelected(true);
				}
				
				text_uer.setText(username);
				loginpwd_1.setText(pass);
			}
		});
		jspanel.setViewportView(table);
		
		final JPanel panel_2 = new JPanel();
		final GridLayout glayout = new GridLayout(0,2);
		panel_2.setLayout(glayout);
		
		final JLabel label_user = new JLabel("      用户账号");
		label_user.setFont(new Font("",Font.BOLD,16));
		panel_2.add(label_user);
		
		text_uer = new JTextField();
		text_uer.setFont(new Font("",Font.PLAIN,15));
		panel_2.add(text_uer);
		
		final JLabel label_sex = new JLabel ("     管 理 员");
		label_sex.setFont(new Font("",Font.BOLD,15));
		panel_2.add(label_sex);
		
		final JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		final FlowLayout flout1 = new FlowLayout();
		panel_3.setLayout(flout1);
		
		jradiobut1 = new JRadioButton();
		jradiobut1.setSelected(true);
		butgroup.add(jradiobut1);
		panel_3.add(jradiobut1);
		jradiobut1.setText("是");
		
		jradiobut2 = new JRadioButton();
		jradiobut2.setSelected(true);
		butgroup.add(jradiobut2);
		panel_3.add(jradiobut2);
		jradiobut2.setText("否");
		
		final JLabel loginpwd = new JLabel("      密    码");
		loginpwd.setFont(new Font("",Font.BOLD,16));
		panel_2.add(loginpwd);
		
		loginpwd_1 = new JPasswordField();
		panel_2.add(loginpwd_1);
		getContentPane().add(panel_2);
		
		final JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, BorderLayout.SOUTH);
		
		final Button but_mod = new Button("修改");
		but_mod.setFont(new Font("",Font.BOLD,16));
		panel_4.add(but_mod);
		but_mod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(text_uer.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "用户名不能为空");
					return;
				}
				
				if(loginpwd_1.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "密码不能为空");
					return;
				}
				
				String userName = text_uer.getText();
				String admin ="是";
				if(jradiobut2.isSelected())
				{
					admin = "否";
				}
				String pwd = loginpwd_1.getText();
				
				int i = SqlOperation.updateUser(userName,pwd,admin);
				if(i>0)
				{
					JOptionPane.showMessageDialog(null, "保存成功");
					ModDelUser.this.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "保存失败");
				}
			}
		});
		
		final Button but_del = new Button("删除");
		but_del.setFont(new Font("",Font.BOLD,16));
		panel_4.add(but_del);
		but_del.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selUser = table.getSelectedRow();
				String userName = text_uer.getText();
				System.out.println("The user is:"+userName);
				int i = SqlOperation.delUser(userName);
				if(i>0)
				{
					JOptionPane.showMessageDialog(null, "删除用户成功");
					users = getAllUsers(SqlOperation.selectUser());
					DefaultTableModel tmodel = new DefaultTableModel();
					table.setModel(tmodel);
					tmodel.setDataVector(users, strUser);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "删除用户失败，请检查");
				}
			}
		});
		
		final Button but_exit = new Button("退出");
		but_exit.setFont(new Font("",Font.BOLD,16));
		panel_4.add(but_exit);
		but_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModDelUser.this.setVisible(false);
			}
		});
	}
}
