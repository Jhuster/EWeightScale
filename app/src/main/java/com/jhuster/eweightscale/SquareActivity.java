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

import com.jhuster.eweightscale.core.WeightDB;
import com.jhuster.eweightscale.core.WeightDB.Weight;
import com.jhuster.eweightscale.widget.SquareItemWidget;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class SquareActivity extends Activity {
	
	public static final String BMI_THIN_WEIGHT  = "偏瘦";
	public static final String BMI_NICE_WEIGHT  = "完美";
	public static final String BMI_FAT_WEIGHT   = "偏胖";
	public static final String BMI_BIG_WEIGHT  =  "肥胖";

	private SquareItemWidget mWeightItem;
	private SquareItemWidget mBMIItem;
	private SquareItemWidget mBMRItem;
	private SquareItemWidget mResultItem;
	private SquareItemWidget mDestWeight;
	private TextView mPersonalTips;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		 
		setContentView(R.layout.activity_square);
		
		mPersonalTips = (TextView)findViewById(R.id.PersionalTips);				
		mWeightItem = new SquareItemWidget(findViewById(R.id.SquareWeightItem),
				getString(R.string.square_weight));		
		mBMIItem = new SquareItemWidget(findViewById(R.id.SquareBMIItem),
				getString(R.string.square_bmi));		
		mBMRItem = new SquareItemWidget(findViewById(R.id.SquareBMRItem),
				getString(R.string.square_bmr));		
		mResultItem = new SquareItemWidget(findViewById(R.id.SquareResult),
				getString(R.string.square_result));		
		mDestWeight = new SquareItemWidget(findViewById(R.id.SquareDestWeight),
				getString(R.string.square_dest_weight));
	}
	
	public void onResume() {		
		super.onResume();
		onSquareUpdate();
	}
	
	protected void onSquareUpdate() {
	    
	    //判断是否有体重数据
        Weight weight = WeightDB.getInstance().get(0);
        if (weight == null) {
            mPersonalTips.setText(getString(R.string.no_data));
            mPersonalTips.setVisibility(View.VISIBLE);          
            mWeightItem.setItemValue("");
            mBMIItem.setItemValue("");
            mBMRItem.setItemValue("");
            mDestWeight.setItemValue("");
            mResultItem.setItemValue("");           
            return;
        }
        
        //判断个人信息是否填写好了
        double height = Configuration.getUserHeight();
        int age = Configuration.getUserAge();       
        if (height <= 0.0 || age <= 0) {
            mPersonalTips.setText(getString(R.string.personalTips));
            mPersonalTips.setVisibility(View.VISIBLE);
        }
        else {
            mPersonalTips.setVisibility(View.GONE);
        }
        
        //显示BMI指标分析结果
        WeightData weightdata = new WeightData(weight);     
        mWeightItem.setItemValue(weightdata.getWeightStr());
        String bmi = weightdata.getBMIValue();
        if (!"".equals(bmi)) {         
            mBMIItem.setItemValue(bmi);         
            double BMI = Double.valueOf(bmi);               
            if (BMI < 18.5) {
                mResultItem.setItemValue(BMI_THIN_WEIGHT);
            }
            else if (BMI >= 18.5 && BMI < 24) {
                mResultItem.setItemValue(BMI_NICE_WEIGHT);
            }
            else if (BMI >= 24 && BMI < 28) {
                mResultItem.setItemValue(BMI_FAT_WEIGHT);
            }
            else {
                mResultItem.setItemValue(BMI_BIG_WEIGHT);
            }
        }
                
        //显示目标体重
        if (height != 0) {
            double HEIGHT = Double.valueOf(height);
            String destWeight = CommonUtil.calculateMinWeight(HEIGHT) + "kg" + 
                    "～" + CommonUtil.calculateMaxWeight(HEIGHT) + "kg";
            mDestWeight.setItemValue(destWeight);                                   
        }
                    
        //显示BMR
        int bmr_age = Configuration.getUserAge();           
        double bmr_height = Configuration.getUserHeight();
        int bmr_sex = Configuration.getUserSex();
        if (bmr_age!=0 && !"".equals(weight) && bmr_height!= 0) {
            double bmr_weight = Double.valueOf(weightdata.getWeightValue());
            String bmrStr = CommonUtil.calculateBMR(bmr_sex, bmr_weight, bmr_height, bmr_age) + "千卡";
            mBMRItem.setItemValue(bmrStr);
        }       
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {        
        if (keyCode == KeyEvent.KEYCODE_BACK) {                              
            if (!CommonUtil.onExitProcess(this)) {
            	return true;
            }
        }        
        return super.onKeyDown(keyCode, event);
    }    
}
