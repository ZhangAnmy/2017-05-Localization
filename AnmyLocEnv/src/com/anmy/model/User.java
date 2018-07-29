package com.anmy.model;

public class User 
{
	private String username;
	private String admin;
	private String pass;
	
	public void setUserName(String username)
	{
		this.username = username;
	}
	public String getUserName()
	{
		return username;
	}
	
	public void setAdmin(String admin)
	{
		this.admin = admin;
	}
	public String getAdmin()
	{
		return admin;
	}
	
	public void setPass(String pass)
	{
		this.pass = pass;
	}
	public String getPass()
	{
		return pass;
	}
}
