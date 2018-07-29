package com.anmy.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.anmy.login.MonitorLoginIFrame;
import com.anmy.sql.SqlOperation;

public class ChangePwd extends JFrame
{
	private JTextField username;
	private JPasswordField oldpass;
	private JPasswordField newpass;
	private JPasswordField cfirmpass;
	private JButton but;
	private Operater user = MonitorLoginIFrame.getUser();
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public ChangePwd()
	{
		super();
		setTitle("更改密码");
		setSize(300, 200);
		setLocation((width-300)/2, (height-200)/2);
		getContentPane().setLayout(new GridBagLayout());
		
		final JLabel lable_warning = new JLabel();
		lable_warning.setFont(new Font("TimesRoman",Font.PLAIN,16));
		lable_warning.setForeground(Color.RED);
		lable_warning.setText("<html>注：每个<b>操作员</b>只能修改自己的密码。</html>");
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx=0;//0行,不拉伸
		gbc.weighty=1.0;
		gbc.gridwidth=4;//占据了4列
		getContentPane().add(lable_warning, gbc);
		
		final JLabel lable_login = new JLabel("登录名");
		lable_login.setFont(new Font("",Font.PLAIN,14));
		final GridBagConstraints gbc_login = new GridBagConstraints();
		gbc_login.gridy=2;
		gbc_login.gridx=0;
		getContentPane().add(lable_login, gbc_login);
		
		username = new JTextField(user.getUserName());
		final GridBagConstraints gbc_login_1 = new GridBagConstraints();
		gbc_login_1.gridy=2;
		gbc_login_1.gridx=1;
		gbc_login_1.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(username, gbc_login_1);
		
		final JLabel label_oldpwd = new JLabel("旧密码");
		label_oldpwd.setFont(new Font("",Font.PLAIN,14));
		final GridBagConstraints gbc_oldpwd = new GridBagConstraints();
		gbc_oldpwd.gridy=3;
		gbc_oldpwd.gridx=0;
		getContentPane().add(label_oldpwd, gbc_oldpwd);
		
		oldpass = new JPasswordField();
		oldpass.setDocument(new MyDocument(6));
		oldpass.setFont(new Font("",Font.PLAIN,14));
		final GridBagConstraints gbc_oldpwd_1 = new GridBagConstraints();
		gbc_oldpwd_1.gridy=3;
		gbc_oldpwd_1.gridx=1;
		gbc_oldpwd_1.gridwidth=3;
		gbc_oldpwd_1.insets=new Insets(0, 0, 0, 10);
		gbc_oldpwd_1.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(oldpass, gbc_oldpwd_1);
		
		final JLabel label_newpwd = new JLabel("新密码");
		label_newpwd.setFont(new Font("",Font.PLAIN,14));
		final GridBagConstraints gbc_newpwd = new GridBagConstraints();
		gbc_newpwd.gridy=4;
		gbc_newpwd.gridx=0;
		getContentPane().add(label_newpwd, gbc_newpwd);
		
		newpass = new JPasswordField();
		newpass.setDocument(new MyDocument(6));
		newpass.setFont(new Font("",Font.PLAIN,14));
		final GridBagConstraints gbc_newpwd_1 = new GridBagConstraints();
		gbc_newpwd_1.gridy=4;
		gbc_newpwd_1.gridx=1;
		gbc_newpwd_1.gridwidth=3;
		gbc_newpwd_1.insets=new Insets(0, 0, 0, 10);
		gbc_newpwd_1.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(newpass, gbc_newpwd_1);
		
		final JLabel label_cfirmpwd = new JLabel("确认新密码");
		label_cfirmpwd.setFont(new Font("",Font.PLAIN,14));
		final GridBagConstraints gbc_cfirmpwd = new GridBagConstraints();
		gbc_cfirmpwd.gridy=5;
		gbc_cfirmpwd.gridx=0;
		getContentPane().add(label_cfirmpwd, gbc_cfirmpwd);
		
		cfirmpass = new JPasswordField();
		cfirmpass.setDocument(new MyDocument(6));
		cfirmpass.setFont(new Font("",Font.PLAIN,14));
		cfirmpass.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) 
			{
				if (e.getKeyCode() == 10)//点击确定键(回车键)
					but.doClick();//点击下一个按钮
			}
		});
		final GridBagConstraints gbc_cfirmpwd_1 = new GridBagConstraints();
		gbc_cfirmpwd_1.gridy=5;
		gbc_cfirmpwd_1.gridx=1;
		gbc_cfirmpwd_1.gridwidth=3;
		gbc_cfirmpwd_1.insets=new Insets(0, 0, 0, 10);
		gbc_cfirmpwd_1.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(cfirmpass, gbc_cfirmpwd_1);
		
		but = new JButton("确定");
		but.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(oldpass.getText().equals(user.getPassword()))
				{
					if(newpass.getText().equals(cfirmpass.getText()))
					{
						int i = SqlOperation.updatePass(user.getUserName(), cfirmpass.getText());
						if(i>0)
						{
							JOptionPane.showMessageDialog(null, "用户密码修改成功");
							ChangePwd.this.setVisible(false);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "用户密码修改失败");
						}
						oldpass.setText("");
						newpass.setText("");
						cfirmpass.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "请确认新密码两次输入相同。。");
						newpass.setText("");
						cfirmpass.setText("");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "用户密码错误，请重新输入");
					oldpass.setText("");
				}
			}
		});
		final GridBagConstraints but_conf = new GridBagConstraints();
		but_conf.gridy=6;
		but_conf.gridx=1;
		but_conf.anchor = GridBagConstraints.EAST;
		getContentPane().add(but, but_conf);
		
		final JButton but_reset = new JButton("重新设置");
		but_reset.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						oldpass.setText("");
						newpass.setText("");
						cfirmpass.setText("");
					}
				});
		final GridBagConstraints but_res = new GridBagConstraints();
		but_res.gridy=6;
		but_res.gridx=2;
		but_res.anchor = GridBagConstraints.WEST;
		getContentPane().add(but_reset, but_res);
		setVisible(true);
	}
}	
