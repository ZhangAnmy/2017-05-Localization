package com.anmy.model;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.anmy.sql.*;

public class AddUser extends JFrame
{
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	private JTextField userName_1;
	private JPasswordField loginpwd_1;
	private JRadioButton admin;
	private JRadioButton admin_1;
	
	public AddUser()
	{
		super();
		setTitle("�����û�");
		setSize(300,200);
		setLocation((width-400)/2,(height)/2);
		setVisible(true);
		getContentPane().setLayout(new GridLayout(0,2));
		
		final JLabel userName = new JLabel("�û��˺�");
		userName.setFont(new Font("",Font.PLAIN,16));
		userName.setBorder(new EmptyBorder(10, 20, 10, 20));
		getContentPane().add(userName);
		
		userName_1 = new JTextField();
		userName_1.setFont(new Font("",Font.PLAIN,16));
//		userName_1.setBorder(new EmptyBorder(10, 20, 10, 20));
		userName_1.setPreferredSize(new Dimension(0, 100));
		getContentPane().add(userName_1);
		
		final JLabel loginpwd = new JLabel("��  ��");
		loginpwd.setFont(new Font("",Font.PLAIN,16));
		loginpwd.setBorder(new EmptyBorder(10, 20, 10, 20));
		getContentPane().add(loginpwd);
		
		loginpwd_1 = new JPasswordField();
//		loginpwd_1.setBorder(new EmptyBorder(10, 20, 10, 20));
		loginpwd_1.setPreferredSize(new Dimension(0, 100));
		getContentPane().add(loginpwd_1);
		
		admin = new JRadioButton("��");
		admin.setBorder(new EmptyBorder(10, 20, 10, 20));
		getContentPane().add(admin);
		
		admin_1 = new JRadioButton("��");
		admin_1.setBorder(new EmptyBorder(10, 20, 10, 20));
		getContentPane().add(admin_1);
		
		JButton but = new JButton("����");
		but.setFont(new Font("",Font.PLAIN,16));
		but.addActionListener(new actionListener());
		but.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) 
			{
				if (e.getKeyCode() == 10)//����س���
					new actionListener();
			}
		});
		getContentPane().add(but);
		
		final JButton butCan = new JButton("ȡ��");
		butCan.setFont(new Font("",Font.PLAIN,16));
		butCan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUser.this.setVisible(false);
			}
		});
		getContentPane().add(butCan);
	}
	
	public class  actionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			String admin_value="��";
			if(!admin.isSelected())
			{
				admin_value="��";
			}
			
			int i = SqlOperation.insertUserInfo(userName_1.getText(), loginpwd_1.getText(), admin_value.trim());
			if(i>0)
			{
				JOptionPane.showMessageDialog(null, "����ɹ�");
				AddUser.this.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "����û�ʧ��");
			}
		}
	}
}
