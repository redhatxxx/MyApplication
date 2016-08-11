package com.example.administrator.myapplication;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class Singal_Activity extends AppCompatActivity {

    MediaPlayer mediaplayer;
    ImageButton btnpaly;
    ImageButton btnpause;
    ImageButton btnstop;
    TextView statusmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        String name = bundle.getString("songpath");
        checkmediaplayer(name);
        initComponents();
    }
    private void checkmediaplayer(String name){
        if(mediaplayer==null) {
            mediaplayer = new MediaPlayer();
        }else if(mediaplayer.isPlaying()){
            mediaplayer.stop();
        }
        try {
            mediaplayer.setDataSource(name);
            mediaplayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initComponents() {
        btnpaly = (ImageButton) findViewById(R.id.play);
        btnpause = (ImageButton) findViewById(R.id.pause);
        btnstop  = (ImageButton) findViewById(R.id.stop);
        statusmsg = (TextView) findViewById(R.id.statustext);

        btnpaly.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                playMedia();
            }
        });

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
        mediaplayer.pause();
        mediaplayer.seekTo(0);
        statusmsg.setText("停止");
    }

    private void pauseMedia() {
        mediaplayer.pause();
        statusmsg.setText("暂停");
    }

    private void playMedia() {
        mediaplayer.start();
        statusmsg.setText("播放");
    }

}
