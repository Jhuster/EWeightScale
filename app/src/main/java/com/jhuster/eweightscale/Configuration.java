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

import java.util.Calendar;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Configuration {
    
    public static final String CONFIG_FIRST_START = "isFirstStart";
    
	public static final String CONFIG_USER_SEX  = "sex";
	public static final String CONFIG_USER_AGE_YEAR  = "age_year";
	public static final String CONFIG_USER_AGE_MONTH = "age_month";
	public static final String CONFIG_USER_AGE_DAY   = "age_day";
	public static final String CONFIG_USER_HEIGHT = "usr_height";	
	public static final String CONFIG_CHART_MODE  = "x_base";
	public static final String CONFIG_CONTINUOUS_DAYS = "continuous_days";

    private static SharedPreferences mSharedPreferences;
    private static OnChartModeChangeListener mChartModeChangeListener;
    
    public static interface OnChartModeChangeListener {
        public void onChartModeChanged(int chartmode);
    }
       
    public static void initialize(Context context) {       		
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context); 
    }
    
    public static void setOnChartModeChangeListener(OnChartModeChangeListener listener) {
        mChartModeChangeListener = listener;
    }
    
    public static boolean isFirstStart() {
        return mSharedPreferences.getBoolean(CONFIG_FIRST_START,true);      
    }
    
    public static void setFirstStart(boolean isFirstStart) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();        
        edit.putBoolean(CONFIG_FIRST_START, isFirstStart);       
        edit.commit();
    }
    
    public static void setUserSex(int sex) {    	
    	SharedPreferences.Editor edit = mSharedPreferences.edit();        
        edit.putInt(CONFIG_USER_SEX, sex);        
        edit.commit();
    }
    
    public static int getUserSex() {
    	return mSharedPreferences.getInt(CONFIG_USER_SEX, CommonUtil.UserSexMale);    	
    }
    
    public static void setUserBirthday(int year, int month, int day) {
    	SharedPreferences.Editor edit = mSharedPreferences.edit();        
        edit.putInt(CONFIG_USER_AGE_YEAR, year);
        edit.putInt(CONFIG_USER_AGE_MONTH, month);
        edit.putInt(CONFIG_USER_AGE_DAY, day);
        edit.commit();
    }
    
    public static int getUserBirthdayYear() {
    	return mSharedPreferences.getInt(CONFIG_USER_AGE_YEAR,Calendar.getInstance().get(Calendar.YEAR));
    }
    
    public static int getUserBirthdayMonth() {
    	return mSharedPreferences.getInt(CONFIG_USER_AGE_MONTH,Calendar.getInstance().get(Calendar.MONTH) + 1);
    }
    
    public static int getUserBirthdayDay() {
    	return mSharedPreferences.getInt(CONFIG_USER_AGE_DAY,Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }
    
    public static int getUserAge() {
    	return Calendar.getInstance().get(Calendar.YEAR) - getUserBirthdayYear();
    }
    
    public static void setUserHeight(String height) {
    	SharedPreferences.Editor edit = mSharedPreferences.edit();        
        edit.putString(CONFIG_USER_HEIGHT, height);
        edit.commit();
    }
    
    public static double getUserHeight() {    	
    	String heightStr = mSharedPreferences.getString(CONFIG_USER_HEIGHT, "");
    	if ("".equals(heightStr)) {
    		return 0;
    	}    	
    	return Double.valueOf(heightStr);
    }
    
    public static void setContinousDays(int days) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();        
        edit.putInt(CONFIG_CONTINUOUS_DAYS,days);        
        edit.commit();
    }
    
    public static int getContinousDays() {
        return mSharedPreferences.getInt(CONFIG_CONTINUOUS_DAYS,0);
    }
    
    public static void setChartMode(int chartmode) {
        if (chartmode!=getChartMode()) {
            SharedPreferences.Editor edit = mSharedPreferences.edit();        
            edit.putInt(CONFIG_CHART_MODE, chartmode);        
            edit.commit();
            if (mChartModeChangeListener!=null) {
                mChartModeChangeListener.onChartModeChanged(chartmode);
            }   
        }    	
    }
    
    public static int getChartMode() {
    	return mSharedPreferences.getInt(CONFIG_CHART_MODE, CommonUtil.ChartModeDay);
    }    
}
