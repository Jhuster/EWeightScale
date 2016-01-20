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

import com.jhuster.eweightscale.core.WeightDB;
import com.jhuster.eweightscale.core.WeightDB.Weight;
import com.jhuster.eweightscale.core.WeightDBHelper;
import com.jhuster.eweightscale.widget.InputDialog;
import com.jhuster.eweightscale.widget.SearchDialog;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener {

    private static final String TAB_TAG_CHART  = "iChart";
    private static final String TAB_TAG_DATAS  = "iDatas";
    private static final String TAB_TAG_SQUARE = "iSquare";
    private static final String TAB_TAG_MORE   = "iMore";
    
    private TabHost mTabHost;
    private SearchDialog mSearchWeightDailog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
        getActionBar().setDisplayHomeAsUpEnabled(false);        
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));        
        setContentView(R.layout.activity_main);
        
        Configuration.initialize(this);        
        WeightDB.getInstance().open(getApplicationContext());              
        
        RadioGroup mMainTab=(RadioGroup)findViewById(R.id.main_tab);        
        mMainTab.setOnCheckedChangeListener(this);
        
        mTabHost = getTabHost();
        
        Intent iChart = new Intent(this, ChartActivity.class);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TAG_CHART)
                .setIndicator(getResources().getString(R.string.menu_chart), getResources().getDrawable(R.drawable.menu_chart_selector))
                .setContent(iChart));
        
        Intent iDatas = new Intent(this, DataActivity.class);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TAG_DATAS)
                .setIndicator(getResources().getString(R.string.menu_data), getResources().getDrawable(R.drawable.menu_data_selector))
                .setContent(iDatas));
        
        Intent iSquare = new Intent(this,SquareActivity.class);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TAG_SQUARE)
                .setIndicator(getResources().getString(R.string.menu_more), getResources().getDrawable(R.drawable.menu_more_selector))
                .setContent(iSquare));
        
        Intent iMore = new Intent(this,MoreActivity.class);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TAG_MORE)
                .setIndicator(getResources().getString(R.string.menu_more), getResources().getDrawable(R.drawable.menu_more_selector))
                .setContent(iMore));
        
        if (Configuration.isFirstStart()) {
            addTestData();
        }
    }
        
    @Override
    protected void onDestroy() {    	
        WeightDB.getInstance().close();    	
    	super.onDestroy();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();        
        if (mTabHost.getCurrentTabTag().equals(TAB_TAG_DATAS)) {
            getMenuInflater().inflate(R.menu.menu_actions, menu);
        }                
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {        
        switch (item.getItemId()) {
        case R.id.action_search:
             onClickSearch();
             break;
        case R.id.action_add:
             onClickAddData();
             break;
        default:
             break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
        case R.id.MenuButtonChart:            
             this.mTabHost.setCurrentTabByTag(TAB_TAG_CHART);
             break;
        case R.id.MenuButtonDatas:
             this.mTabHost.setCurrentTabByTag(TAB_TAG_DATAS);
             break;
        case R.id.MenuButtonSquare:
             this.mTabHost.setCurrentTabByTag(TAB_TAG_SQUARE);
             break;
        case R.id.MenuButtonMore:
             this.mTabHost.setCurrentTabByTag(TAB_TAG_MORE);
             break;
        default:
             break;
        }        
        invalidateOptionsMenu();
    }
    
    protected void addTestData() {        
        Calendar calendar = Calendar.getInstance();
        Double value = 55.0;                
        for (int i=0; i<5; i++) {            
            value = value + 2.0;
            calendar.add(Calendar.DAY_OF_MONTH, -1);            
            Weight weight = new Weight();
            weight.value = String.valueOf(value);
            weight.date = calendar.getTimeInMillis();
            WeightDB.getInstance().insert(weight);
        }        
        for (int i=5; i<10; i++) {            
            value = value - 3.0;
            calendar.add(Calendar.DAY_OF_MONTH, -1);            
            Weight weight = new Weight();
            weight.value = String.valueOf(value);
            weight.date = calendar.getTimeInMillis();
            WeightDB.getInstance().insert(weight);
        }  
        Configuration.setContinousDays(10);
        Configuration.setFirstStart(false);
    }
 
    protected void onClickAddData() {              
        final InputDialog dlg = new InputDialog(this);             
        dlg.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Weight weight = new Weight();
                    weight.value = String.format("%-4s",dlg.getInputValue());
                    weight.date = Calendar.getInstance().getTimeInMillis();
                    WeightDBHelper.addContinuousDays();
                    WeightDB.getInstance().insert(weight);                      
                }                
            }
        });
        dlg.show();
    }
    
    protected void onClickSearch() { 
        
        if (mSearchWeightDailog != null) {
            mSearchWeightDailog.show();
            return;
        }
        
        mSearchWeightDailog = new SearchDialog(this);             
        mSearchWeightDailog.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    String condition = mSearchWeightDailog.getSearchCondition();
                    Intent intent = new Intent(MainActivity.this,DataActivity.class);
                    intent.putExtra("Condition",condition);
                    startActivity(intent);
                }                
            }
        });
        mSearchWeightDailog.show();
    }    
}
