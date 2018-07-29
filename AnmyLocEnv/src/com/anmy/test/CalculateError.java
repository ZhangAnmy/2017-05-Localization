package com.anmy.test;

import java.util.List;

import com.anmy.model.ValueInfo;
import com.anmy.sql.SqlOperation;

public class CalculateError {
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
		List<ValueInfo> results = SqlOperation.selectLoctOffline();
		
		Object[][] result=getselect(results);
		float value[][] = new float[30][2];//Save the Number ID and calculation result of total 30 offline data.
		
		for(int i=0;i<result.length;i++)
		{
			float error = 1;
		}

	}

}
