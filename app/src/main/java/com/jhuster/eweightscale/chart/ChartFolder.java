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

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.ViewGroup;

/**
 *  图表容器,可以添加多张表(@see ChartTable)
 */
public class ChartFolder {
	
	public static final String TAG = ChartFolder.class.getSimpleName();

	public static final String TABLE_TIPS = "体重曲线----X轴:日期，Y轴:体重(kg)";

	private GraphicalView mChartView;	
	private ChartTable mChartTable;
	private ChartAdapter mChartAdapter;	
	private ViewGroup mParentView;
	
	public ChartFolder(ViewGroup parent) {
	    mParentView = parent;		    
	}

	public void setChartAdapter(ChartAdapter adapter) {
	    mChartAdapter = adapter;
        draw(adapter.getMinXScale(),adapter.getMaxXScale(),adapter.getXScaleUnit());
	}
	
    public void invalidate() {
        if (mChartAdapter==null) {
            return;
        }
        mChartTable.clear();
        for (int i=mChartAdapter.getMinXScale(); i<mChartAdapter.getMaxXScale(); i++) {                                              
            mChartTable.add(i,mChartAdapter.getYScale(i));                
        }
        mChartView.repaint();
        mChartView.invalidate();
    }
    
	protected void draw(int min, int max, String unit) {	  
	    		
	    XYMultipleSeriesDataset datasheet = new XYMultipleSeriesDataset();    
	    XYMultipleSeriesRenderer render = new XYMultipleSeriesRenderer();   	                 
	        
	    render.setBackgroundColor(Color.TRANSPARENT);
	    render.setApplyBackgroundColor(true);
		//render.setXRoundedLabels(false);
	    render.setAxisTitleTextSize(20);		
		render.setChartTitleTextSize(20);
	    render.setLabelsTextSize(20);
	    render.setLegendTextSize(22); //图表左下角的小标题
	    render.setMargins(new int[] { 30, 40, 30, 10 }); //设置图表外边界
	    render.setMarginsColor(Color.argb(0,0xff,0,0)); //设置图表边界外区域的颜色	   
	    render.setPointSize(5);	    	    	    	    
	    render.setShowLabels(true);
	    render.setShowAxes(true);
	    render.setShowLegend(true);	    
	    render.setShowGridX(true);	    
	    render.setShowGridY(true);
	    //render.setXTitle(XTITLE);
	    //render.setYTitle(YTITLE);	  
	    //render.setZoomButtonsVisible(true);
	    render.setXLabelsPadding(10);
	    render.setYLabelsPadding(10);
	    render.setYLabelsAlign(Align.RIGHT);	    
	    render.setAxesColor(Color.DKGRAY);	 
	    render.setLabelsColor(Color.DKGRAY);
	    render.setXLabelsColor(Color.DKGRAY);
	    render.setYLabelsColor(0,Color.DKGRAY);

	    render.setYAxisMin(40); //决定默认情况下，显示在Y轴上的最小刻度
	    render.setYAxisMax(80); //决定默认情况下，显示在Y轴上的最大刻度
	    render.setYLabels(10);  //决定默认情况下，显示多少个刻度	    	    
	    
	    render.setFitLegend(true);
	    render.setZoomRate(1f);
	        
	    //手动决定最大的X坐标 （防止没有数据的时候不显示Label）
	    render.setXAxisMin(min);
	    if (max-min > 10) {
	    	render.setXAxisMax(min+10); 
	    }
	    else {
	    	render.setXAxisMax(min+4);
	    }
	    
	    //如果要自定义X轴坐标Label，则必须设置为0
	    render.setXLabels(0);
	    for (int i=min; i<=max; i++) {
	    	render.addXTextLabel(i, i+unit);
	    }
	    
	    render.setPanEnabled(true);	    
	    render.setZoomEnabled(true);	    
	    render.setPanLimits(new double[]{min-1,max+1,-1,100});
	    render.setZoomLimits(new double[]{min-1,max+1,-1,100});	    

	    mChartTable = new ChartTable(TABLE_TIPS);
	    datasheet.addSeries(mChartTable.getSeries());
        render.addSeriesRenderer(mChartTable.getRender());
	    
        if (mChartView!=null) {
            mParentView.removeView(mChartView);
        }
        
        mChartView = ChartFactory.getLineChartView(mParentView.getContext(), datasheet, render);
        
	    mParentView.addView(mChartView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
	    mParentView.invalidate();
	}	
}
