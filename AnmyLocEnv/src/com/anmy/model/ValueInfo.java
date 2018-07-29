package com.anmy.model;

import java.sql.Date;

public class ValueInfo {
	private int id;
	private String nodeID;
	private Float value;
	private Float locvalue_x;
	private Float wifilocvalue_x;
	private Float wsnlocvalue_x;
	private Float bothlocvalue_x;
	private Float locvalue_y;
	private Float wifilocvalue_y;
	private Float wsnlocvalue_y;
	private Float bothlocvalue_y;
	private String rssvalue;
	private String rssvalue1;
	private String rssvalue2;
	private String rssvalue3;
	private String rssvalue4;
	private String rssvalue5;
	private String rssvalue6;
	private String date;
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	
	public Float getlocXValue() {
		return locvalue_x;
	}
	public void setlocXValue(Float locvalue_x) {
		this.locvalue_x = locvalue_x;
	}
	
	public Float getlocYValue() {
		return locvalue_y;
	}
	public void setlocYValue(Float locvalue_y) {
		this.locvalue_y = locvalue_y;
	}
	
	public Float getwifilocXValue() {
		return wifilocvalue_x;
	}
	public void setwifilocXValue(Float wifilocvalue_x) {
		this.wifilocvalue_x = wifilocvalue_x;
	}
	
	public Float getwifilocYValue() {
		return wifilocvalue_y;
	}
	public void setwifilocYValue(Float wifilocvalue_y) {
		this.wifilocvalue_y = wifilocvalue_y;
	}
	
	public Float getwsnlocXValue() {
		return wsnlocvalue_x;
	}
	public void setwsnlocXValue(Float wsnlocvalue_x) {
		this.wsnlocvalue_x = wsnlocvalue_x;
	}
	
	public Float getwsnlocYValue() {
		return wsnlocvalue_y;
	}
	public void setwsnlocYValue(Float wsnlocvalue_y) {
		this.wsnlocvalue_y = wsnlocvalue_y;
	}
	
	public Float getbothlocXValue() {
		return bothlocvalue_x;
	}
	public void setbothlocXValue(Float bothlocvalue_x) {
		this.bothlocvalue_x = bothlocvalue_x;
	}
	
	public Float getbothlocYValue() {
		return bothlocvalue_y;
	}
	public void setbothlocYValue(Float bothlocvalue_y) {
		this.bothlocvalue_y = bothlocvalue_y;
	}
	
	public String getrssValue() {
		return rssvalue;
	}
	public void setrssValue(String rssvalue) {
		this.rssvalue = rssvalue;
	}
	
	public String getrssValue1() {
		return rssvalue1;
	}
	public void setrssValue1(String rssvalue1) {
		this.rssvalue1 = rssvalue1;
	}
	
	public String getrssValue2() {
		return rssvalue2;
	}
	public void setrssValue2(String rssvalue2) {
		this.rssvalue2 = rssvalue2;
	}
	
	public String getrssValue3() {
		return rssvalue3;
	}
	public void setrssValue3(String rssvalue3) {
		this.rssvalue3 = rssvalue3;
	}
	
	public String getrssValue4() {
		return rssvalue4;
	}
	public void setrssValue4(String rssvalue4) {
		this.rssvalue4 = rssvalue4;
	}
	
	public String getrssValue5() {
		return rssvalue5;
	}
	public void setrssValue5(String rssvalue5) {
		this.rssvalue5 = rssvalue5;
	}
	
	public String getrssValue6() {
		return rssvalue6;
	}
	public void setrssValue6(String rssvalue6) {
		this.rssvalue6 = rssvalue6;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
