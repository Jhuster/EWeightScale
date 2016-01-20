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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MoreActivity extends Activity implements OnClickListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_more);
        
        addSettingItem(R.id.MoreAccount,getString(R.string.more_account));
        
        addSettingItem(R.id.MoreBackupOrRestore,getString(R.string.more_backup_restore));
        addSettingItem(R.id.MoreSetting,getString(R.string.more_setting));
        
        addSettingItem(R.id.MoreWhatBMI,getString(R.string.more_what_bmi));
        addSettingItem(R.id.MoreWhatBMR,getString(R.string.more_what_bmr));
        
        addSettingItem(R.id.MoreAboutUs,getString(R.string.more_about));
    }
        
    protected void addSettingItem(int layout_id, String title) {        
        RelativeLayout layout = (RelativeLayout)findViewById(layout_id);
        ((TextView)layout.findViewById(R.id.SettingItemTitle)).setText(title);
        layout.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Intent intent = null;                
        switch (v.getId()) {
        case R.id.MoreAccount:      
             intent = new Intent(this,PersonalActivity.class);
        	   startActivity(intent);
             break;
        case R.id.MoreBackupOrRestore:                           
             break;             
        case R.id.MoreSetting:        
        	   intent = new Intent(this,SettingActivity.class);
        	   startActivity(intent);
             break;
        case R.id.MoreWhatBMI: 
             intent = new Intent(this,WhatActivity.class);
             intent.putExtra("What", "BMI");
       	     startActivity(intent);
             break;
        case R.id.MoreWhatBMR:      
       	     intent = new Intent(this,WhatActivity.class);
       	     intent.putExtra("What", "BMR");
      	     startActivity(intent);
             break;         
       case R.id.MoreAboutUs:      
             intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
             break;
        default:
             return;
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
