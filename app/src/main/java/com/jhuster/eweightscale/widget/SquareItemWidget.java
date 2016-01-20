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
import android.view.View;
import android.widget.TextView;

public class SquareItemWidget {           

    private TextView mSquareTitle;
    private TextView mSquareValue;       
    
    public SquareItemWidget(View layout, String title) {
    	mSquareTitle = (TextView)layout.findViewById(R.id.SquareItemTitle);
    	mSquareValue = (TextView)layout.findViewById(R.id.SquareItemValue);        
    	mSquareTitle.setText(title);    	
    }         
    
    public void setItemValue(String value) {
    	mSquareValue.setText(value);
    }
    
    public String getItemValue() {
    	return mSquareValue.getText().toString();
    }
}
