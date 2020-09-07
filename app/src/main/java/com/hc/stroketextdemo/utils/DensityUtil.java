package com.hc.stroketextdemo.utils;

import android.content.Context;

public class DensityUtil {  
  
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dp2px(Context context, float dpValue) {
        final float scale = getScreenDensity(context);
        return (int) (dpValue * scale + 0.5f);  
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        return dp2px(context, dpValue);
    }

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = getScreenDensity(context);
        return (int) (pxValue / scale + 0.5f);  
    }

    /**
     * 根据手机分辨率从sp转成px
     */
    public static float sp2px(Context context, float spValue) {
        final float scale = getScreenDensity(context);
        return (spValue * scale + 0.5f);
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}  