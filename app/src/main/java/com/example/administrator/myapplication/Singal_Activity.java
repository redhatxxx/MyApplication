package com.example.administrator.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.myapplication.staticfield.BroadAction;

public class Singal_Activity extends AppCompatActivity {

    ImageButton btnpause;
    ImageButton btnstop;
    TextView statusmsg;
    SingalReceive receiver;
    int playstatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        initComponents();
        receiver = new SingalReceive();
        //过滤广播类型
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadAction.UPDATE_ACTION);
        registerReceiver(receiver,filter);
    }

    private void initComponents() {
        btnpause = (ImageButton) findViewById(R.id.pause);
        btnstop  = (ImageButton) findViewById(R.id.stop);
        statusmsg = (TextView) findViewById(R.id.statustext);



        btnpause.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                pauseMedia();
            }
        });

        btnstop.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                stopMedia();
            }
        });
    }

    private void stopMedia() {
        Intent intentbroad = new Intent(BroadAction.CTL_ACTION);
        intentbroad.putExtra("control","0x003");
        intentbroad.putExtra("number", String.valueOf(-1));
        intentbroad.putExtra("action",BroadAction.CURRENT_SONG);
        sendBroadcast(intentbroad);
        statusmsg.setText("停止");
    }

    private void pauseMedia() {
        Intent intentbroad = new Intent(BroadAction.CTL_ACTION);
        intentbroad.putExtra("number", String.valueOf(-1));
        intentbroad.putExtra("action",BroadAction.CURRENT_SONG);
        if(playstatus==0){
            btnpause.setImageResource(R.drawable.ic_media_play);
            intentbroad.putExtra("control","0x002");
            statusmsg.setText("暂停");
            playstatus = 1;
        }else if(playstatus==1){
            btnpause.setImageResource(R.drawable.ic_media_pause);
            intentbroad.putExtra("control","0x001");
            statusmsg.setText("播放");
            playstatus = 0;
        }
        sendBroadcast(intentbroad);
    }

    //接受广播类
    public class SingalReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

}
