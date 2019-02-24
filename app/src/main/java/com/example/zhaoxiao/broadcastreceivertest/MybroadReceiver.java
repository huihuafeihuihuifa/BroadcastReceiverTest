package com.example.zhaoxiao.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by zhaoxiao on 2018/12/26.
 */

public class MybroadReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"接收自己定义的广播",Toast.LENGTH_LONG).show();
    }
}
