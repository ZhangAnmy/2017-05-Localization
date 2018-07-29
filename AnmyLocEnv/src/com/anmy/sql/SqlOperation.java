package com.anmy.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anmy.model.Operater;
import com.anmy.model.User;
import com.anmy.model.ValueInfo;
import com.mysql.jdbc.ResultSet;

public class SqlOperation 
{
	protected static String dbClassName = "com.mysql.jdbc.Driver";
	protected static String dbUrl = "jdbc:mysql://localhost:3306/db_LocEnv";
	protected static String dbUser = "root";
	protected static String dbPwd = "1";
	private static Connection conn = null;

	private SqlOperation()
	{
		if (conn == null)//���ӿգ��������ݿ�����
		{
			try {
				Class.forName(dbClassName).newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void close()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("conn close failed.");
		}
		conn = null;
	}
	
	private static ResultSet excuteResult(String sql)
	{
		if(conn == null)
		{
			new SqlOperation();
		}
		try {
			return (ResultSet) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
		} catch (SQLException e) {
			return null;
		}
	}
	
	private static ResultSet executeQuery(String sql) {
		try {
			if(conn==null)
			new SqlOperation();
			return (ResultSet) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}
	
	public static int excuteUpdate(String sql)
	{
		try {
			if(conn == null)
			{
				new SqlOperation();
			}
			return conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Update sql failed..");
		}
		return -1;
	}
	
	public static Operater check(String username,String password)
	{
		Operater operater = new Operater();
		String sql = "select * from LoginUser where loginname='"+username+"' and password = '"+password+"'";
		ResultSet rs = SqlOperation.excuteResult(sql);
		try {
			while(rs.next())
			{
				String names = rs.getString(1);
				operater.setUserName(rs.getString("loginname"));
				operater.setPassword(rs.getString("password"));
				if(names!=null)
				{
					return operater;
				}
			}
		} catch (SQLException e) {
			System.out.println("Sql check login execute failed..");
			return null;
		}
		SqlOperation.close();
		return operater;
	}
	
	public static int insertUserInfo(String username, String pwd, String admin)
	{
		String sql = "insert into LoginUser(loginname,password,admin) values ('"+username+"','"+pwd+"','"+admin+"'"+")";
		int i = SqlOperation.excuteUpdate(sql);
		if(i>0)
		{
			return i;
		}
		return -1;
	}
	
	public static int updatePass(String username, String newpas)
	{
		String sql = "update LoginUser set password = '"+newpas+"' where loginname = '"+username+"'";
		int i = SqlOperation.excuteUpdate(sql);
		if(i>0)
		{
			return i;
		}
		return -1;
	}
	
	public static List selectUser()
	{
		List<User> list = new ArrayList<User>();
		String sql = "select * from LoginUser";
		try {
			if(conn == null)
			{
				new SqlOperation();
			}
			ResultSet rst = (ResultSet) conn.createStatement().executeQuery(sql);
			while(rst.next())
			{
				User user = new User();
				user.setUserName(rst.getString("loginname"));
				user.setAdmin(rst.getString("Admin"));
				user.setPass(rst.getString("Password"));
				
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SqlOperation.close();
		return list;
	}
	
	public static int updateUser(String userName,String pwd,String admin)
	{
		int i = 0;
		String sql = "update LoginUser set admin = '"+admin+"',password = '"+pwd+"' where loginname = '"+userName+"'";
		i = SqlOperation.excuteUpdate(sql);
		return i;
	}
	
	public static int delUser(String userName)
	{
		int i=0;
		String sql = "delete from LoginUser where loginname = '"+userName+"'";
		try {
			if(conn == null)
			{
				new SqlOperation();
			}
			i = conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Delete user failed..");
		}
		SqlOperation.close();
		return i;
	}
	
	public static List<ValueInfo> selectData(String name, String startTime, String endTime)
	{
		List<ValueInfo> list=new ArrayList<ValueInfo>();
		String sql="";
		if(name.equals("WiFi"))
		{
			sql="select * from tbl_wifi t where t.date>='"+startTime+"' and t.date<='"+endTime+"'";
		}
		else if (name.equals("BlueTooth"))
		{
			sql="select * from tbl_bluetooth t where t.date>='"+startTime+"' and t.date<='"+endTime+"'";
		}
		else
		{
			sql="select * from tbl_wsn t where t.date>='"+startTime+"' and t.date<='"+endTime+"'";
		}
		ResultSet s=SqlOperation.executeQuery(sql);
		try {
			while(s.next()){
				ValueInfo valueInfo=new ValueInfo();
				valueInfo.setID(s.getInt(1));
				valueInfo.setNodeID(s.getString(2));
				valueInfo.setValue(s.getFloat(3));
				valueInfo.setDate(s.getString(4));
				list.add(valueInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static int selectStatus()
	{
		String msg = "";
		String sql = "";
		try {
			sql = "select * from Infrared order by date desc limit 1";
			ResultSet s = SqlOperation.executeQuery(sql);
			while (s.next()) {
				msg = s.getString(3).trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SqlOperation.close();
		if (msg.equals("����")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static int insertSensorInfo(String loc_x, String loc_y, float rss, String date)
	{
		String sql = "insert into tbl_location (LOC_X,LOC_Y,rss_value,date) values ('"+loc_x+"','"+loc_y+"','"+rss+"','"+date+"'"+")";
		System.out.println("insert sql is:"+sql);
		int i = SqlOperation.excuteUpdate(sql);
		if(i>0)
		{
			return i;
		}
		return -1;
	}
	
	public static List<ValueInfo> selectCurrentValue()
	{
		List<ValueInfo> list=new ArrayList<ValueInfo>();
		String sql="select * from tbl_location order by date desc limit 1";
		ResultSet s=SqlOperation.executeQuery(sql);
		try {
			while(s.next()){
				ValueInfo valueInfo=new ValueInfo();
				valueInfo.setlocXValue(s.getFloat(2));
				valueInfo.setlocYValue(s.getFloat(3));
				valueInfo.setrssValue(s.getString(4));
				valueInfo.setDate(s.getString(5));
				list.add(valueInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<ValueInfo> selectLoctValue(String startTime, String endTime)
	{
		List<ValueInfo> list=new ArrayList<ValueInfo>();
		String sql="select * from tbl_location t where t.date>='"+startTime+"' and t.date<='"+endTime+"'";
		ResultSet s=SqlOperation.executeQuery(sql);
		try {
			while(s.next()){
				ValueInfo valueInfo=new ValueInfo();
				valueInfo.setID(s.getInt(1));
				valueInfo.setlocXValue(s.getFloat(2));
				valueInfo.setlocYValue(s.getFloat(3));
				valueInfo.setrssValue(s.getString(4));
				valueInfo.setDate(s.getString(5));
				valueInfo.setwifilocXValue(s.getFloat(6));
				valueInfo.setwifilocYValue(s.getFloat(7));
				valueInfo.setwsnlocXValue(s.getFloat(8));
				valueInfo.setwsnlocYValue(s.getFloat(9));
				valueInfo.setbothlocXValue(s.getFloat(10));
				valueInfo.setbothlocYValue(s.getFloat(11));
				list.add(valueInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<ValueInfo> selectLoctOffline()
	{
		List<ValueInfo> list=new ArrayList<ValueInfo>();
		String sql="select * from tbl_location_offline";
		ResultSet s=SqlOperation.executeQuery(sql);
		try {
			while(s.next()){
				ValueInfo valueInfo=new ValueInfo();
				valueInfo.setID(s.getInt(1));
				valueInfo.setlocXValue(s.getFloat(2));
				valueInfo.setlocYValue(s.getFloat(3));
				valueInfo.setrssValue1(s.getString(4));
				valueInfo.setrssValue2(s.getString(5));
				valueInfo.setrssValue3(s.getString(6));
				valueInfo.setrssValue4(s.getString(7));
				valueInfo.setrssValue5(s.getString(8));
				valueInfo.setrssValue6(s.getString(9));
				list.add(valueInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
