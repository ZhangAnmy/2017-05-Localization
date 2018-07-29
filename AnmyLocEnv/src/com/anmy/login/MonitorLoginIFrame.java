package com.anmy.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.anmy.model.Operater;
import com.anmy.sql.SqlOperation;


public class MonitorLoginIFrame extends JFrame
{
	/**
	 * Author:Anmy
	 * Date:2017/04/22
	 * Version:1.0
	 */
	
private static final long serialVersionUID = 1L;
	
	//Login operation for logining the system.
	public class LoginAction implements ActionListener
	{
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) 
		{
			user = SqlOperation.check(username.getText(), password.getText());
//			if(username.getText().equals(user.getUserName()) && password.getText().equals(user.getPassword()))
			{
				MonitorFrame monitor = new MonitorFrame();
				MonitorLoginIFrame.this.setVisible(false);
				monitor.setLocationRelativeTo(null);
				monitor.setVisible(true);
			}
			/*else
			{
				JOptionPane.showMessageDialog(null, "UserName or Password wrong,please relogin.");
				username.setText("");
				password.setText("");
			}*/
		}
	}
	
	public class LoginResetAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("Login Reset!");
			username.setText("");
			password.setText("");
		}
	}
	
	private JTextField username;
	private JPasswordField password;
	private JButton login;
	private JButton reset;
	private ImageIcon picImage=null;
	private static Operater user;
	
	public MonitorLoginIFrame() //Constructor to initialize object values
	{
		super();
		final BorderLayout borderLayout = new BorderLayout();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5,5)); //����Ϊ�߽粼�֣����������������Ϊ5���ء�Ĭ��Ϊ0
		setFont(new Font("TimesRoman",Font.PLAIN,16));//�������壬�����ֺ�
		final JPanel panel = new JPanel();
		panel.setLayout(borderLayout);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(panel);
		
		final JPanel panel_1 = new JPanel();
		final  GridLayout gridLayout = new GridLayout(0,2);
		gridLayout.setHgap(5);//�������֮���ˮƽ���
		gridLayout.setVgap(10);
		panel_1.setLayout(gridLayout);//������ע�����
		panel.add(panel_1);
		getContentPane().add("Center", panel_1);
		
		final JLabel label = new JLabel("登陆名");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(0, 0));
		label.setMinimumSize(new Dimension(0, 0));
		panel_1.add(label);
		
		username = new JTextField(10);
		username.setPreferredSize(new Dimension(0, 100));
		panel_1.add(username);
		
		final JLabel label_1 = new JLabel("密  码");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setPreferredSize(new Dimension(0, 0));
		label_1.setMinimumSize(new Dimension(0, 0));
		panel_1.add(label_1);
		
		password = new JPasswordField(10);
		password.setEchoChar('*');
		password.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) 
			{
				if (e.getKeyCode() == 10)//���ȷ����(�س���)
					login.doClick();//�����һ����ť
			}
		});
		panel_1.add(password);
		
		final JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		
		login = new JButton();
		login.setText("登陆");
		login.addActionListener(new LoginAction());
		panel_2.add(login);
		
		reset = new JButton();
		reset.setText("重置");
		reset.addActionListener(new LoginResetAction());
		panel_2.add(reset);
		getContentPane().add("South", panel_2);
		
		final JLabel piclabel = new JLabel();
		picImage = new ImageIcon(getClass().getResource("/login.jpg"));
		Image img = picImage.getImage();  
        img = img.getScaledInstance(300, 100, Image.SCALE_DEFAULT);  
        picImage.setImage(img);  
		piclabel.setIcon(picImage);
		piclabel.setOpaque(true);//���ÿؼ���͸�� 
		piclabel.setBackground(Color.GRAY);
		getContentPane().add("North", piclabel);
	}
	public static Operater getUser() {
		return user;
	}
	public static void setUser(Operater user) {
		MonitorLoginIFrame.user = user;
	}
}
