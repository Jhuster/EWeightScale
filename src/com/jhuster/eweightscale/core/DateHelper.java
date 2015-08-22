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
package com.jhuster.eweightscale.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateHelper {
    
    protected static final String DISPLAY_WEEK_FORMAT = "EEEE";
    protected static final String DISPLAY_DATE_FORMAT = "yyyy年MM月dd日";
    protected static final String DISPLAY_FULL_FORMAT = "yyyy年MM月dd日 EEEE HH点mm分";
    
    public static class DatePeriod {
        public long begin;
        public long end;
        public DatePeriod(long begin,long end) {
            this.begin = begin;
            this.end = end;
        }
    }
    
    public static Calendar getToday() {
        return Calendar.getInstance();
    }
    
    public static Calendar getYestday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(GregorianCalendar.DAY_OF_MONTH,-1);   
        return calendar;
    }
    
    public static Calendar getDayOfLastWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR,-1);   
        return calendar;
    }
    
    public static Calendar getDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);   
        return calendar;
    }
    
    public static Calendar getCalendar(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }
    
    public static DatePeriod getYearPeriod(int year) {
        Calendar calendar = new GregorianCalendar(year,0,0);
        long begin = calendar.getTimeInMillis();
        calendar.add(GregorianCalendar.YEAR,1);
        long end = calendar.getTimeInMillis();  
        return new DatePeriod(begin,end);
    }
    
    public static DatePeriod getMonthPeriod(int year,int month) {
        Calendar calendar = new GregorianCalendar(year,month,1);
        long begin = calendar.getTimeInMillis();
        calendar.add(GregorianCalendar.MONTH,1);
        long end = calendar.getTimeInMillis();  
        return new DatePeriod(begin,end);
    }
    
    public static DatePeriod getDatePeriod(int year,int month,int day) {      
        Calendar calendar = new GregorianCalendar(year,month,day);
        long begin = calendar.getTimeInMillis();
        calendar.add(GregorianCalendar.DAY_OF_MONTH,1);
        long end = calendar.getTimeInMillis();          
        return new DatePeriod(begin,end);
    }
    
    public static DatePeriod getDatePeriod(Calendar calendar) {
        return getDatePeriod(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static DatePeriod getWeekPeriod(Calendar calendar) {            
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK,calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        long begin = calendar.getTimeInMillis();
        calendar.add(Calendar.WEEK_OF_YEAR,1); 
        long end = calendar.getTimeInMillis();    
        return new DatePeriod(begin,end);       
    }
    
    public static DatePeriod getMonthPeriod(Calendar calendar) {       
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        long begin = calendar.getTimeInMillis();
        calendar.add(GregorianCalendar.MONTH,1);
        long end = calendar.getTimeInMillis();
        return new DatePeriod(begin,end);             
    }   
    
    public static String getWeekStr(long milliseconds) {
        return new SimpleDateFormat(DISPLAY_WEEK_FORMAT,Locale.CHINA).format(milliseconds);
    }
    
    public static String getDateStr(long milliseconds) {
        return new SimpleDateFormat(DISPLAY_DATE_FORMAT,Locale.CHINA).format(milliseconds);
    }
}
