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

import com.jhuster.eweightscale.widget.ChartModeSelector;
import com.jhuster.eweightscale.widget.ChartModeSelector.OnDateModeSelectListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;

public class SettingActivity extends Activity implements OnDateModeSelectListener {

	public static final String TITLE = "系统设置";
	public static final String XAXIS_TITLE = "X轴坐标：";
		
	private ChartModeSelector mDateModeSelector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);        
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));
		setContentView(R.layout.activity_setting);		
		getActionBar().setTitle(TITLE);
		
		mDateModeSelector = new ChartModeSelector((RadioGroup)findViewById(R.id.SettingDateMode),XAXIS_TITLE);				
		mDateModeSelector.setRadioChecked(Configuration.getChartMode());
		mDateModeSelector.setOnDateModeSelectListener(this);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {        
        switch (item.getItemId()) {
        case android.R.id.home:
             finish();
        default:
             break;
        }
        return true;
    }

	@Override
	public void onRadioYearSelected() {
		Configuration.setChartMode(CommonUtil.ChartModeYear);				
	}

	@Override
	public void onRadioMonthSelected() {
		Configuration.setChartMode(CommonUtil.ChartModeMonth);		
	}

	@Override
	public void onRadioDaySelected() {
		Configuration.setChartMode(CommonUtil.ChartModeDay);		
	}
	
}
