package com.anmy.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MenuItemActionListener implements ActionListener 
{
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("�˳�ϵͳ"))
		{
			System.exit(0);
		}
		
		if(e.getActionCommand().equals("���Ŀ���"))
		{
			ChangePwd changPwd = new ChangePwd();
		}
		
		if(e.getActionCommand().equals("�����û�"))
		{
			AddUser adduser = new AddUser();
		}
		
		if(e.getActionCommand().equals("�û��޸���ɾ��"))
		{
			ModDelUser deluser = new ModDelUser();
		}
		
		if(e.getActionCommand().equals("WiFi"))
		{
			System.out.println("wifi interface");
			WiFiInfo wifiinfo = new WiFiInfo();
		}
		
		if(e.getActionCommand().equals("BlueTooth"))
		{
			BTInfo btinfo = new BTInfo();
		}
		
		if(e.getActionCommand().equals("WSN"))
		{
			WSNInfo wsninfo = new WSNInfo();
		}
	}
}
