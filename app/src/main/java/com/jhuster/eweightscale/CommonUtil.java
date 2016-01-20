/*
 *  Copyright (C) 2015, Jhuster, All Rights Reserved
 *  Author:  Jhuster(lujun.hust@gmail.com)
 *  
 *  https://github.com/Jhuster/EWeightScale
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 */
package com.jhuster.eweightscale;

import java.text.DecimalFormat;
import android.content.Context;
import android.widget.Toast;

public class CommonUtil {

	public static final int MinYear = 2013;
	public static final int MaxYear = 2020;
	public static final String YearUnit = "年";
	
	public static final int MinMonth = 1;
	public static final int MaxMonth = 12;
	public static final String MonthUnit = "月";
	
	public static final int MinDay = 1;
	public static final int MaxDay = 31;
	public static final String DayUnit = "号";
	
	public static final int ChartModeYear  = 0;
	public static final int ChartModeMonth = 1;
	public static final int ChartModeDay   = 2;
	
	public static final int UserSexMale   = 0;
	public static final int UserSexFemale = 1;
	
	private static final int BACK_KEY_EXIT_TIME_PEROID = 1000;    
    private static long mLastBackKeyPressedTime = 0;
    
	public static double calculateBMI(double weight, double height) {		
		DecimalFormat format = new DecimalFormat("0.00");
		double result = weight/(height*height)*10000; 						
		return Double.valueOf(format.format(result).toString());
	}
	
	public static double calculateBMR(int sex, double weight, double height, int age) {		
		double result = 0.0;		
		DecimalFormat format = new DecimalFormat("0.00");		
		if (sex == UserSexMale) {
			result = 655 + (9.6*weight)  + (1.8*height) - (4.7*age);
		}
		else {
			result = 66  + (13.8*weight) + (5.0*height) - (6.8*age);
		}		
		return Double.valueOf(format.format(result).toString());
	}
	
	public static double calculateMinWeight(double height) {
		DecimalFormat format = new DecimalFormat("0.0");
		double result = 18.5*height*height/10000; 
		return Double.valueOf(format.format(result).toString());
	}
	
	public static double calculateMaxWeight(double height) {		
		DecimalFormat format = new DecimalFormat("0.0");
		double result = 24.0*height*height/10000; 		
		return Double.valueOf(format.format(result).toString());
	}
	
	public static boolean onExitProcess(Context context) {
	    if (System.currentTimeMillis() - mLastBackKeyPressedTime >  BACK_KEY_EXIT_TIME_PEROID) {
            mLastBackKeyPressedTime = System.currentTimeMillis(); 
            Toast.makeText(context,context.getString(R.string.another_press_to_exit), Toast.LENGTH_SHORT).show();
            return false;
        }   
        return true;             
    }
}
