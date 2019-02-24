package com.example.zhaoxiao.broadcastreceivertest.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoxiao on 2019/1/19.
 */

public class ActivityCollector {
    public static List<Activity> activities =new ArrayList<>();
    public static void addActivity(Activity activity){
        activities .add(activity);
    }
    public static void remove(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
