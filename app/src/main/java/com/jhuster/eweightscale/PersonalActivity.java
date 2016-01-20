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

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalActivity extends Activity implements OnDateChangedListener {

	public static final String TITLE = "个人信息";
	public static final String SAVE_MSG = "信息保存成功!";
	
	private RadioGroup mRadioGroup;
	private DatePicker mDatePicker; 
	private TextView mBirthdayText;
	private EditText mHeightEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);        
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));              
		setContentView(R.layout.activity_personal);		
	    getActionBar().setTitle(TITLE);
				
		mRadioGroup = (RadioGroup) findViewById(R.id.PersonalSex);
		mDatePicker = (DatePicker) findViewById(R.id.PersonalAgePicker);
		mBirthdayText = (TextView) findViewById(R.id.BirthdayValue);
		mHeightEditText = (EditText)findViewById(R.id.WidgetHeightInputEdit);
		
		int year,month,day; 
		
		year = Configuration.getUserBirthdayYear();
		month = Configuration.getUserBirthdayMonth();
		day = Configuration.getUserBirthdayDay();
		
		mDatePicker.init(year,month,day,this);
		mDatePicker.setMaxDate(Calendar.getInstance().getTimeInMillis());
		
		String birthday = year + "-" + month +  "-" + day;
		mBirthdayText.setText(birthday);
		
		int sex = Configuration.getUserSex(); 
		if (sex == CommonUtil.UserSexFemale) {
			mRadioGroup.check(R.id.RadioFemale);
		}
		
		if (Configuration.getUserHeight() != 0.0) {
			String height = String.valueOf(Configuration.getUserHeight());
			mHeightEditText.setText(height);
		}		
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
	
	public void onClickSaveInfo(View v) {
		if (R.id.RadioMale == mRadioGroup.getCheckedRadioButtonId()) {
			Configuration.setUserSex(CommonUtil.UserSexMale);					
		}
		else {
			Configuration.setUserSex(CommonUtil.UserSexFemale);	
		}
		Configuration.setUserBirthday(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
		Configuration.setUserHeight(mHeightEditText.getText().toString());						
		Toast.makeText(this, SAVE_MSG, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		monthOfYear = monthOfYear+1;
		String birthday = year + "-" + monthOfYear +  "-" + dayOfMonth;
		mBirthdayText.setText(birthday);
	}
}
