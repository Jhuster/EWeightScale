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
package com.jhuster.eweightscale.widget;

import java.util.ArrayList;
import java.util.Calendar;
import com.jhuster.eweightscale.CommonUtil;
import com.jhuster.eweightscale.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ChartDateSelector implements Spinner.OnItemSelectedListener {

	public static final String TITLE_DEFAULT = "体重趋势图";	
	
	private TextView mViewTitle;
	private Spinner mYearSpinner;
	private Spinner mMonthSpinner;
	private ArrayAdapter<String> mYearSpinnerAdapter;
	private ArrayAdapter<String> mMonthSpinnerAdapter;		
    private OnDateSelectedListener mDateSelectedListener;
    private int mChartMode = CommonUtil.ChartModeDay;
    
    public interface OnDateSelectedListener {
        public void onDateSelected();
    }
    
	public ChartDateSelector(View layout) {								
		
		mViewTitle = (TextView) layout.findViewById(R.id.DateSelectorTitle);
		mYearSpinner = (Spinner) layout.findViewById(R.id.DateSelectorYearSpinner);
		mMonthSpinner = (Spinner) layout.findViewById(R.id.DateSelectorMonthSpinner);
		
		ArrayList<String> years = new ArrayList<String>();
		for (int year=CommonUtil.MinYear; year<=CommonUtil.MaxYear; year++) {
		    years.add(year+CommonUtil.YearUnit);
		}
		
		mYearSpinnerAdapter = new ArrayAdapter<String>(layout.getContext(),android.R.layout.simple_spinner_item,years);                		      
		mYearSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mYearSpinner.setAdapter(mYearSpinnerAdapter);		
		
        ArrayList<String> months = new ArrayList<String>();
        for (int month=CommonUtil.MinMonth; month<=CommonUtil.MaxMonth; month++) {
            months.add(month+CommonUtil.MonthUnit);
        }
	        
		mMonthSpinnerAdapter = new ArrayAdapter<String>(layout.getContext(),android.R.layout.simple_spinner_item,months);                		      
		mMonthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mMonthSpinner.setAdapter(mMonthSpinnerAdapter);
		
		mYearSpinner.setOnItemSelectedListener(this);
		mMonthSpinner.setOnItemSelectedListener(this);
	}
	
    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        mDateSelectedListener = listener;
    }
    
	public void setChartMode(int chartmode) {
	    
		mChartMode = chartmode;		
		
		mViewTitle.setVisibility(View.GONE);		
		mYearSpinner.setVisibility(View.GONE);
		mMonthSpinner.setVisibility(View.GONE);
		
		if (chartmode == CommonUtil.ChartModeYear) {
			mViewTitle.setText(TITLE_DEFAULT);
			mViewTitle.setVisibility(View.VISIBLE);						
		}
		else if (chartmode == CommonUtil.ChartModeMonth) {									
			setSelectedYear(Calendar.getInstance().get(Calendar.YEAR));
		}
		else if (chartmode == CommonUtil.ChartModeDay) {						
			setSelectedYear(Calendar.getInstance().get(Calendar.YEAR));
			setSelectedMonth(Calendar.getInstance().get(Calendar.MONTH));
		}
		else{} 
	}
	
	public void setViewTitle(String title) {
		mViewTitle.setText(title);
	}
	
	public void setSelectedYear(int year) {
		mYearSpinner.setSelection(year-CommonUtil.MinYear);
		mYearSpinner.setVisibility(View.VISIBLE);		
	}
	
	public void setSelectedMonth(int month) {
		mMonthSpinner.setSelection(month);
		mMonthSpinner.setVisibility(View.VISIBLE);
	}
	
	public int getChartMode() {
		return mChartMode;
	}
	
	public int getSelectYear() {
		return mYearSpinner.getSelectedItemPosition() + CommonUtil.MinYear;
	}
	
	public int getSelectMonth() {
		return mMonthSpinner.getSelectedItemPosition(); 
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mDateSelectedListener != null) {
            mDateSelectedListener.onDateSelected();
        }
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}
