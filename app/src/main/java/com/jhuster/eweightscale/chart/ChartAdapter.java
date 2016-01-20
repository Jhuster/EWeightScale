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

public abstract class ChartAdapter {
	public abstract boolean isEmpty();		
	public abstract int getMinXScale();  //最小x刻度	
	public abstract int getMaxXScale();  //最大x刻度
	public abstract String getXScaleUnit(); //x刻度的单位
	public abstract double getYScale(int x); //获取指定x刻度的y值
}
