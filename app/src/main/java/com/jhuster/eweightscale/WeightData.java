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

import com.jhuster.eweightscale.core.DateHelper;
import com.jhuster.eweightscale.core.WeightDB.Weight;

public class WeightData {

	private static final String WEIGHT_SUFFIX = "kg";
	private static final String BMI_PREFIX = "BMI: ";
	
	public String mWeightValue;
	public long mRecordDate;

	public WeightData(Weight weight) {
	    mWeightValue = weight.value;
	    mRecordDate  = weight.date;
	}
	
	public String getWeightValue() {
	    return mWeightValue;
	}
	
	public String getWeightStr() {				
		String result = String.format("%-4s", mWeightValue);
		return (result + " " + WEIGHT_SUFFIX);
	}
	
	public String getBMIStr() {		
		return (BMI_PREFIX + getBMIValue());
	}
	
	public String getWeekStr() {
        return DateHelper.getWeekStr(mRecordDate);
    }
	
	public String getDateStr() {
		return DateHelper.getDateStr(mRecordDate);
	}		
	
	public String getBMIValue() {       
        double height = Configuration.getUserHeight();
        if (height == 0.0) {
            return "0.00";
        }               
        double bmi = CommonUtil.calculateBMI(Double.valueOf(mWeightValue), height);             
        return new DecimalFormat("0.00").format(bmi).toString();
    }
}
