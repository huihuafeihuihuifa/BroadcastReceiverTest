package com.example.zhaoxiao.broadcastreceivertest.view;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhaoxiao.broadcastreceivertest.MainActivity;
import com.example.zhaoxiao.broadcastreceivertest.R;
import com.example.zhaoxiao.broadcastreceivertest.base.BaseActivity;
import com.example.zhaoxiao.broadcastreceivertest.dbhelper.MydatabaseHelper;
import com.example.zhaoxiao.broadcastreceivertest.utils.FileUtil;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by zhaoxiao on 2019/1/19.
 * 保存文件
 * 
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private MydatabaseHelper datahelper;
    private Button btnlogin, btnsavefile, btnreadfile,btncall,btnnotice;
    private EditText etsavefile;
    private TextView textreadfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        datahelper = new MydatabaseHelper(this, "BookStore.db", null, 1);
        btnlogin = findViewById(R.id.btn_login);
        btncall=findViewById(R.id.btncall);
        textreadfile = findViewById(R.id.t_read);
        btnnotice=findViewById(R.id.btnnotice);
        btnreadfile = findViewById(R.id.btnreadfile);
        etsavefile = findViewById(R.id.et_write);
        btnsavefile = findViewById(R.id.btnsave);
        btnlogin.setOnClickListener(this);
        btnsavefile.setOnClickListener(this);
        btnreadfile.setOnClickListener(this);
        btncall.setOnClickListener(this);
        btnnotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnsave:
                String content = etsavefile.getText() + "";
                FileUtil.saveFlie(this, "filedata", content);
                datahelper.getWritableDatabase();
                break;
            case R.id.btnreadfile:
                textreadfile.setText(FileUtil.readfile(this, "filedata"));
                break;
            case R.id.btncall:
                call();
                break;
            case R.id.btnnotice:
                notice();
                break;
            default:
                break;
        }
    }

    ;


    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10010"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},10);
            return;
        }
            startActivity(intent);

    }

//8.0之前使用的方法，8.0之后加上参数
    private void notice(){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        Notification notification= new NotificationCompat.Builder(this)
                .setContentTitle("this is a testNotice")
                .setContentText("my notice info")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background))
                .build();

        manager.notify(1,notification);
    }
}
