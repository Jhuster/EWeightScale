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

import com.jhuster.eweightscale.core.WeightDB;
import com.jhuster.eweightscale.core.WeightDB.OnDBDataChangeListener;
import com.jhuster.eweightscale.core.WeightDBHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

public class DataActivity extends Activity implements OnDBDataChangeListener {

	private LinearLayout mDataSummary;
	private TextView mTextContinuousDays;
	private TextView mTextReduceWeek;
	private TextView mTextReduceMonth;
	private ListView mWeightListView;
	private int mSelectedPosition = -1;
	private WeightDataAdapter mWeightDataAdapter;  
	private String mCondition = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_data);
		
		mDataSummary = (LinearLayout)findViewById(R.id.DataSummary);
		
		mCondition = getIntent().getStringExtra("Condition");
		if (mCondition != null && !"".equals(mCondition)) {
		    getActionBar().setDisplayHomeAsUpEnabled(true);        
	        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));
		    getActionBar().setTitle(getString(R.string.activity_title_search_result));
		    mDataSummary.setVisibility(View.GONE);
		}
		
		mTextContinuousDays = (TextView)findViewById(R.id.TextContinuousDays);
		mTextReduceWeek  = (TextView)findViewById(R.id.TextReduceWeek);
		mTextReduceMonth = (TextView)findViewById(R.id.TextReduceMonth);
        updateDataSummary();            
		
		mWeightListView = (ListView)findViewById(R.id.WeightDataListView);
		mWeightDataAdapter = new WeightDataAdapter(this,mCondition);		
		mWeightListView.setAdapter(mWeightDataAdapter);
		registerForContextMenu(mWeightListView);
		
		OnItemLongClickListener longListener = new OnItemLongClickListener() {
		     public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {		    	 	
		    	 mSelectedPosition = position;	
		    	 mWeightListView.showContextMenu();
	             return true;
	             
	         }
		};		
		mWeightListView.setOnItemLongClickListener(longListener);		
	}	
	
	@Override
	public void onResume() {
	    super.onResume();
	    WeightDB.getInstance().setOnDBDataChangeListener(this);
	}
	
	@Override
    public void onPause() {
        super.onPause();
        WeightDB.getInstance().setOnDBDataChangeListener(null);
    }	

    @Override
    public void onDBDataChanged() {
        mWeightDataAdapter.notifyDataSetChanged();
        updateDataSummary();
    }    
	
	protected void updateDataSummary() {	    
	    mTextContinuousDays.setText(WeightDBHelper.getContinuousDays()+"天");
	    Double reduced = 0.0;
	    reduced = WeightDBHelper.getWeightReduceThisWeek();	    
	    if (reduced > 0) {
	        mTextReduceWeek.setText("+" + new DecimalFormat("0.00").format(reduced) + " kg");
	    }
	    else {
	        mTextReduceWeek.setText(reduced + " kg");
	    }
	    reduced = WeightDBHelper.getWeightReduceThisMonth();
        if (reduced > 0) {
            mTextReduceMonth.setText("+" + new DecimalFormat("0.00").format(reduced) + " kg");
        }
        else {
            mTextReduceMonth.setText(reduced + " kg");
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.data_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {				
		switch(item.getItemId()) {
		    case R.id.DataDelete:
		    	 if (mSelectedPosition != -1) {		
		    		 WeightDB.getInstance().delete(mSelectedPosition,mCondition);
		    	 }		    	 
		         return true;
		    case R.id.DataClear:	
		         WeightDB.getInstance().clear(mCondition);
		    	 return true;		    
		    default:
		    	return super.onContextItemSelected(item);
		}
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {        
        if (keyCode == KeyEvent.KEYCODE_BACK) {               
            if (mCondition == null || "".equals(mCondition)) {
                if (!CommonUtil.onExitProcess(this)) {
                    return true;
                }   
            }            
        }        
        return super.onKeyDown(keyCode, event);
    }
}
