package com.example.administrator.myapplication;

import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaplayer;
    ImageButton btnpaly;
    ImageButton btnpause;
    ImageButton btnstop;
    TextView statusmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaplayer = MediaPlayer.create(this,R.raw.touchthesky);
        setContentView(R.layout.activity_main);
        initComponents();
//        mediaplayer.start();
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


//    SoundPool soundPool;
//
//    HashMap<Integer,Integer> poolmap ;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initsounds();
//        ImageView frontpage = new ImageView(this);
//        frontpage.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.front));
//        this.setContentView(frontpage);
//        playsound(1,5);
//    }
//
//    private void playsound(int sound, int loop) {
//        soundPool.play(poolmap.get(sound),1,1,1,loop,1f);
//    }
//
//    private void initsounds() {
//        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC,100);
//        poolmap = new HashMap<Integer, Integer>();
//        poolmap.put(1,soundPool.load(this,R.raw.ibelieve,1));
//    }
}
