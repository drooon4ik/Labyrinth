package com.labyrinth.myapplication;

import android.content.Context;
import android.os.SystemClock;

/**
 * Created with IntelliJ IDEA.
 * User: Navi
 * Date: 31.01.13
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class G {
    private static Context cnx;
    private static int swidth, sheight;
    private static float xstep,ystep;
    private static int dTime = 0;
    private static long pTime = 0;
    private static long Time =0;
    public static void setContext(Context context){
        cnx = context;
    }
    public static Context getContext(){
        return cnx;
    }
    public static void setScreenXY(int width, int height){
        swidth = width;
        sheight = height;
        xstep = 2.0f/(float)width;
        ystep = 2.0f/(float)height;
    }
    public static int[] getScreenSize(){
        return new int[] {swidth,sheight};
    }
    public static float[] getSteps(){
        return new float[] {xstep,ystep};
    }
    public static void TimeUpdate(){
        Time = SystemClock.uptimeMillis();
        //if(dTime!=0)dTime = (int)(Time-pTime);
        if(pTime!=0)dTime = (int)(Time-pTime);
        pTime = Time;
    }
    public static int deltaTime(){
        return dTime;
    }
    public static long getTime(){
        return Time;
    }

}
