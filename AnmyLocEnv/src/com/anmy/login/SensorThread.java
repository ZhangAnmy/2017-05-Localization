package com.anmy.login;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.anmy.sql.SqlOperation;

public class SensorThread extends Thread
{
	private static final String dataFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat df = new SimpleDateFormat(dataFormat);
	
	private Thread thread;
	private String threadName;
	SensorThread(String name)
	{
		this.threadName = name;
	}
	
	public void run()
	{
		int n=0;
		float m=-20;
		while(true &&(!Thread.currentThread().isInterrupted()))
		{
			try {
				String data = "test";
				String date = df.format(new Date());
				System.out.println("--���յ�����Ϊ��--"+data+"--ʱ��Ϊ��--"+date);
				
				//�����Ӵ�������õ�����
				String LOC_X = ""+(++n);
				String LOC_Y = ""+n;
				float rss = m++;
				
				SqlOperation.insertSensorInfo(LOC_X, LOC_Y, rss, date);
					Thread.sleep(5000);//5sִ��һ��
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}	
	
	public void start()
	{
		System.out.println("Start..."+threadName);
		if(thread == null)
		{
			thread = new Thread(this,threadName);
			thread.start();
		}
	}

	public static void main(String[] args) {
		SensorThread st = new SensorThread("anmy");
		st.start();
	}
}
