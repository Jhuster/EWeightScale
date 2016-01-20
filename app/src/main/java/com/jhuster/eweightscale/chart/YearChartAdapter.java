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

public class YearChartAdapter extends ChartAdapter {

	@Override
	public boolean isEmpty() {			
	    return WeightDBHelper.isEmpty();
	}
	
	@Override
	public double getYScale(int year) {			    
        return WeightDBHelper.getWeightAverage(year);
	}

	@Override
	public int getMinXScale() {
		return CommonUtil.MinYear;
	}

	@Override
	public int getMaxXScale() {
		return CommonUtil.MaxYear;
	}

    @Override
    public String getXScaleUnit() {
        return CommonUtil.YearUnit;
    }
	
}
