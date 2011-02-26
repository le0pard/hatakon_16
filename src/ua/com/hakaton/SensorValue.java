package ua.com.hakaton;

import java.util.Date;

public class SensorValue {
	
	public float x;
	public float y;
	public float z;
	public int Snum;
	public Date time;
	
	public SensorValue(){
		time = new Date(0);
		Snum = 0;
		x = 0;
		y = 0;
		z = 0;
	}
	
	public SensorValue(long lTime, int num, float xx, float yy, float zz){
		time = new Date(lTime);
		Snum = num;
		x = xx;
		y = yy;
		z = zz;
	}
	
	/**
	 * Convert String Value to Sensor Value
	 * @param str
	 * @return 
	 */
	public void setValue(String str){
		String delims = "[>||,,]+";
		String[] parts = str.split(delims);
		
		time = new Date(Long.parseLong(parts[1]));
		Snum = Integer.parseInt(parts[2]);
		x = Float.parseFloat(parts[3]);
		y = Float.parseFloat(parts[4]);
		z = Float.parseFloat(parts[5]);
	}
}