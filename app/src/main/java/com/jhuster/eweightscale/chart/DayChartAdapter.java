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
package com.jhuster.eweightscale.chart;

import com.jhuster.eweightscale.CommonUtil;
import com.jhuster.eweightscale.core.WeightDBHelper;

public class DayChartAdapter extends ChartAdapter {

	private int mChartYear;
	private int mChartMonth;
	
	public DayChartAdapter(int year,int month) {	
		mChartYear  = year;
		mChartMonth = month;		
	}
	
	@Override
	public boolean isEmpty() {
	    return WeightDBHelper.isEmpty(mChartYear, mChartMonth);
	}

	@Override
	public double getYScale(int day) {	
        return WeightDBHelper.getWeightAverage(mChartYear,mChartMonth,day);
	}

	@Override
	public int getMinXScale() {		
		return CommonUtil.MinDay;
	}

	@Override
	public int getMaxXScale() {		
		return CommonUtil.MaxDay;
	}

    @Override
    public String getXScaleUnit() {
        return CommonUtil.DayUnit;
    }
}
