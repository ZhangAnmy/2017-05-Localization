package com.anmy.test;
import java.util.List;
import java.util.Scanner;

import com.anmy.model.ValueInfo;
import com.anmy.sql.SqlOperation;

public class DistanceTest 
{
	private static Object[][] getselect(List list) {
		Object[][] search = new Object[list.size()][10];
		for (int i = 0; i < list.size(); i++) {
			ValueInfo valueinfo = (ValueInfo) list.get(i);
			search[i][0] = valueinfo.getID();
			search[i][1] = valueinfo.getlocXValue();
			search[i][2] = valueinfo.getlocYValue();
			search[i][3] = valueinfo.getrssValue1();
			search[i][4] = valueinfo.getrssValue2();
			search[i][5] = valueinfo.getrssValue3();
			search[i][6] = valueinfo.getrssValue4();
			search[i][7] = valueinfo.getrssValue5();
			search[i][8] = valueinfo.getrssValue6();
		}
		return search;
	}
	
	public static void main(String[] args) 
	{
//		Scanner sc = new Scanner(System.in);
//		float n = sc.nextFloat();
//		float[] test = new float[6];
		float test[] = {-70,-62,-75,-68,-72,-68};
		
		/*for(int i=0;i<n;i++)
		{
			test[i]=sc.nextFloat();
		}*/
		
		List<ValueInfo> results = SqlOperation.selectLoctOffline();
		
		Object[][] result=getselect(results);
		float pos[][] = new float[30][2];//Save the Number ID and calculation result of total 30 offline data.
		
		for(int i=0;i<result.length;i++)
		{
			int count = 6;
			int id = (int ) result[i][0];
			float rss1 = Float.parseFloat((String) result[i][3]);
			float rss2 = Float.parseFloat((String) result[i][4]);
			float rss3 = Float.parseFloat((String) result[i][5]);
			float rss4 = Float.parseFloat((String) result[i][6]);
			float rss5 = Float.parseFloat((String) result[i][7]);
			float rss6 = Float.parseFloat((String) result[i][8]);
			
			if(rss1 == 0.0 || test[0] == 0.0)
			{
				test[0] = 0.0f;
				rss1 = 0.0f;
				count-=1;
			}
			if(rss2 == 0.0 || test[1] == 0.0)
			{
				test[1] = 0.0f;
				rss2 = 0.0f;
				count-=1;
			}
			if(rss3 == 0.0 || test[2] == 0.0)
			{
				test[2] = 0.0f;
				rss3 = 0.0f;
				count-=1;
			}
			if(rss4 == 0.0 || test[3] == 0.0)
			{
				test[3] = 0.0f;
				rss4 = 0.0f;
				count-=1;
			}
			if(rss5 == 0.0 || test[4] == 0.0)
			{
				test[4] = 0.0f;
				rss5 = 0.0f;
			}
			if(rss6 == 0.0 || test[5] == 0.0)
			{
				test[5] = 0.0f;
				rss6 = 0.0f;
				count-=1;
			}
			
			float value = (float) Math.sqrt((rss1-test[0])*(rss1-test[0]) + (rss2-test[1])*(rss2-test[1]) + (rss3-test[2])*(rss3-test[2]) + (rss4-test[3])*(rss4-test[3]) + (rss5-test[4])*(rss5-test[4]) + (rss6-test[5])*(rss6-test[5]));
			value = value/(float)count;
//			System.out.println(value);
			pos[i][0] = value;
			pos[i][1] = (float)id;
		}
		
		for(int i=0;i<pos.length;i++)
		{
//			System.out.println("before---"+pos[i][1]+":"+pos[i][0]);
		}
		
		for (int i = 0; i < pos.length-1; i++) 
		{
			for (int j = 0; j < pos.length - i - 1; j++) 
			{
				if (pos[j][0] > pos[j + 1][0]) 
				{
					float[] temp = pos[j];
					pos[j] = pos[j + 1];
					pos[j + 1] = temp;
				}
			}
		}
		
		for(int i=0;i<pos.length;i++)
		{
//			System.out.println("after---"+pos[i][1]+":"+pos[i][0]);
		}
		
		int[] loc = new int[4];
		float[][] offlinePos = new float[4][2];
		for(int i=0;i<4;i++)
		{
			loc[i] = (int) pos[i][1];
			offlinePos[i][0] = (float) result[loc[i]-1][1];//X轴坐标
			offlinePos[i][1] = (float) result[loc[i]-1][2];//Y轴坐标
			System.out.println("Location is :"+offlinePos[i][0]+","+offlinePos[i][1]);
		}
		
		float[][] lastPos = new float[1][2];
		float x=0.0f;
		float y=0.0f;
		float iniWeight = 0.4f;
		for(int i=0;i<4;i++)
		{
			float weight = iniWeight - ((float)i/10);
			System.out.println("deta and weight is:"+weight);
			x += offlinePos[i][0]*weight;
			y += offlinePos[i][1]*weight;
		}
		
		lastPos[0][0] = x;
		lastPos[0][1] = y;
		System.out.println("last calculate position is:"+lastPos[0][0]+","+lastPos[0][1]);
	}
}
