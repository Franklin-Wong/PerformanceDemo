package com.integration.performancedemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wongerfeng on 2019/9/9.
 */
public class DateUtil {

    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");
        return sdf.format(new Date());
    }

    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }


}
