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

    ImageButton btnpaly;
    ImageButton btnpause;
    ImageButton btnstop;
    TextView statusmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        initComponents();
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
        statusmsg.setText("停止");
    }

    private void pauseMedia() {
        statusmsg.setText("暂停");
    }

    private void playMedia() {
        statusmsg.setText("播放");
    }

}
