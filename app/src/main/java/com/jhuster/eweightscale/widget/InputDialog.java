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

import com.jhuster.eweightscale.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class InputDialog implements TextWatcher {
	
    protected static final Double MAX_INPUT_DATA = 200.00;
    protected static final Double MIN_INPUT_DATA = 10.00;
    
	protected Context mContext;	
	protected EditText mEditText;
	protected AlertDialog mAlertDialog;
	
	public InputDialog(Context context) {		
		mContext = context;
		AlertDialog.Builder builder = new Builder(context);
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View root = inflater.inflate(R.layout.dialog_input_weight,null);		
		mEditText = (EditText)root.findViewById(R.id.DlgEditText);
		builder.setTitle(context.getString(R.string.dlg_add_weight));
		builder.setView(root);		
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);        
        mEditText.addTextChangedListener(this);
	}
	
	public void setOnClickListener(OnClickListener listener) {
	    mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE,mContext.getString(R.string.dlg_ok),listener);
	    mAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,mContext.getString(R.string.dlg_cancel),listener);
	}
	
	public void show() {		
		mAlertDialog.show();	
		mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
	}	

	public String getInputValue() {
		return mEditText.getText().toString();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void afterTextChanged(Editable s) {
	    
		if (mAlertDialog == null) {
			return;
		}
		
		mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
		
		if ("".equals(s.toString())) {           
            return;
        }        
		
		Double value = Double.valueOf(s.toString());
		if (value < MIN_INPUT_DATA || value > MAX_INPUT_DATA) {
		    return;
		}
		
		mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
	}	
}
