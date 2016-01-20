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

import com.jhuster.eweightscale.CommonUtil;
import com.jhuster.eweightscale.R;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ChartModeSelector implements OnCheckedChangeListener {

	private RadioGroup mRadioGroup;
    private TextView mRadioGroupTitle;
    private OnDateModeSelectListener mDateModeSelectListener;
    
    public interface OnDateModeSelectListener {
    	public void onRadioYearSelected();
    	public void onRadioMonthSelected();
    	public void onRadioDaySelected();
    }
    
	public ChartModeSelector(RadioGroup layout, String title) {
		mRadioGroup = layout;		
		mRadioGroupTitle = (TextView)layout.findViewById(R.id.RadioGroupTitle);
		mRadioGroupTitle.setText(title);
		mRadioGroup.setOnCheckedChangeListener(this);				
	}
	
	public void setOnDateModeSelectListener(OnDateModeSelectListener listener) {
	    mDateModeSelectListener = listener;
	}
	
	public void setRadioChecked(int datemode) {		
		if (datemode == CommonUtil.ChartModeYear) {
			mRadioGroup.check(R.id.RadioYear);
		}
		else if (datemode == CommonUtil.ChartModeMonth) {
			mRadioGroup.check(R.id.RadioMonth);
		}
		else if (datemode == CommonUtil.ChartModeDay) {
			mRadioGroup.check(R.id.RadioDay);
		}
		else{}
	}
	
    public void setVisibility(boolean isVisible) {    	
    	if (isVisible) {
    		mRadioGroup.setVisibility(View.VISIBLE);
    	}
    	else {
    		mRadioGroup.setVisibility(View.GONE);
    	}
    }    

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
	    if (mDateModeSelectListener == null) {
	        return;
	    }
		switch(checkedId) {
		case R.id.RadioYear:
			 mDateModeSelectListener.onRadioYearSelected();
			 break;
		case R.id.RadioMonth:
			 mDateModeSelectListener.onRadioMonthSelected();
			 break;
		case R.id.RadioDay:
			 mDateModeSelectListener.onRadioDaySelected();
			 break;
		default:
			break;
		}		
	}
}
