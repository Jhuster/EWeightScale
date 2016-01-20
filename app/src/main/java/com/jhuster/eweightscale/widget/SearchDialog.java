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

import com.jhuster.eweightscale.core.DateHelper;
import com.jhuster.eweightscale.core.WeightDB;
import com.jhuster.eweightscale.core.DateHelper.DatePeriod;
import com.jhuster.eweightscale.widget.ChartModeSelector.OnDateModeSelectListener;
import com.jhuster.eweightscale.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SearchDialog implements OnDateModeSelectListener {
	
    protected Context mContext; 
    protected EditText mEditText;
    protected AlertDialog mAlertDialog;
    
    protected SearchType mSearchType = SearchType.SearchTypeYear;			
    protected ChartModeSelector mDateModeWidget;
    protected DatePicker mDatePicker; 	
    protected String mSearchCondition=null;
	
	public static enum SearchType {
        SearchTypeYear,
        SearchTypeMonth,
        SearchTypeDay,
    };
    
	public SearchDialog(Context context) {		
	    mContext = context;
	    AlertDialog.Builder builder = new Builder(context);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_search,null);              
        mDateModeWidget = new ChartModeSelector((RadioGroup)root.findViewById(R.id.SearchModeSelecter),
                context.getString(R.string.dlg_search_condition));  
        mDateModeWidget.setOnDateModeSelectListener(this);
        mDatePicker = (DatePicker) root.findViewById(R.id.SearchDatePicker);                                
        setDatePickerEnabled(true,false,false);             
        builder.setTitle(context.getString(R.string.dlg_search_weight));
        builder.setView(root);        
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);        		
	}
	
	public void setOnClickListener(OnClickListener listener) {
        mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE,mContext.getString(R.string.dlg_ok),listener);
        mAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,mContext.getString(R.string.dlg_cancel),listener);
    }    
	
	public void show() {       
        mAlertDialog.show();    
    }   

	protected void setDatePickerEnabled(boolean year, boolean month, boolean date) {					
		((ViewGroup)((ViewGroup)mDatePicker.getChildAt(0)).getChildAt(0)).getChildAt(0).setEnabled(year);		
		((ViewGroup)((ViewGroup)mDatePicker.getChildAt(0)).getChildAt(0)).getChildAt(1).setEnabled(month);		
		((ViewGroup)((ViewGroup)mDatePicker.getChildAt(0)).getChildAt(0)).getChildAt(2).setEnabled(date);						
		mDatePicker.invalidate();					
	}

	@Override
	public void onRadioYearSelected() {
		setDatePickerEnabled(true,false,false);
		mSearchType = SearchType.SearchTypeYear;
	}

	@Override
	public void onRadioMonthSelected() {
		setDatePickerEnabled(true,true,false);
		mSearchType = SearchType.SearchTypeMonth;
	}

	@Override
	public void onRadioDaySelected() {
		setDatePickerEnabled(true,true,true);
		mSearchType = SearchType.SearchTypeDay;
	}
	
	public SearchType getSearchType() {
		return mSearchType;
	}
	
	public int getSearchYear() {
		return mDatePicker.getYear();
	}
	
	public int getSearchMonth() {
		return mDatePicker.getMonth(); //DatePicker返回的month是0～11
	}
	
	public int getSearchDay() {
		return mDatePicker.getDayOfMonth();
	}
	
	public String getSearchCondition() {	    
	    DatePeriod period = null; 	    
	    if (mSearchType == SearchType.SearchTypeYear) {
	        period = DateHelper.getYearPeriod(getSearchYear()); 	        
	    }
	    else if (mSearchType == SearchType.SearchTypeMonth) {
	        period = DateHelper.getMonthPeriod(getSearchYear(),getSearchMonth());
	    }
	    else {
	        period = DateHelper.getDatePeriod(getSearchYear(),getSearchMonth(),getSearchDay());
	    }	    
	    return WeightDB.getInstance().makeCondition(period.begin,period.end);
	}
}


