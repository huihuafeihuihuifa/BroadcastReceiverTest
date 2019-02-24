package com.example.zhaoxiao.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.zhaoxiao.broadcastreceivertest.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private IntentFilter intentFilter;
    private Fragment fragment;
    private FrameLayout frameLayout;
    int a =0;
    private NetWorkChangeReceiver netWorkChangeReceiver;
    private Button btn,btn2,btn3;
    AFragment aFragment;
    BFragment bFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btnforce);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        frameLayout=findViewById(R.id.flayout);
         aFragment=new AFragment();
         bFragment=new BFragment();
        registerReceiver(netWorkChangeReceiver,intentFilter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("com.example.zhaoxiao.action.MYBROAD");
//                sendBroadcast(intent);
                if(a==0) {
                    replaceFragment(aFragment);
                    a=1;
                }else{
                    replaceFragment(bFragment);
                    a=0;
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aFragment.reflesh();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tag", "onClickbtn: ");
                Intent intent=new Intent("com.zhaoxiao.broadcast.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkChangeReceiver);
    }


    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flayout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    class NetWorkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null && networkInfo.isAvailable()){
                Toast.makeText(context,"Network is avalilable",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context,"Network is unavalilable",Toast.LENGTH_LONG).show();
            }

        }
    }
}

