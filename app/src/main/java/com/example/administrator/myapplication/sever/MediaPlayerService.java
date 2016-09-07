package com.example.administrator.myapplication.sever;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.staticfield.BroadAction;

import java.io.IOException;

public class MediaPlayerService extends Service {
//    MyThread msgthread;
    MediaPlayer audioPlayer;
    MediaPlayerReceive playerReceive;
    public MediaPlayerService() {
    }

    @Override
    public  void onCreate(){
        super.onCreate();
        playerReceive = new MediaPlayerReceive();
        //设置广播过滤
        IntentFilter audioFilter = new IntentFilter();
        audioFilter.addAction(BroadAction.CTL_ACTION);
        registerReceiver(playerReceive,audioFilter);
        audioPlayer = new MediaPlayer();
        audioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
    }
    @Override
    public void onStart(Intent intent,int startId){
//        msgthread = new MyThread();
//        msgthread.start();
        super.onStart(intent,startId);
    }
    @Override
    public void onDestroy(){
//        msgthread.flag = false;
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void stopMedia() {
        audioPlayer.pause();
        audioPlayer.seekTo(0);
    }

    private void pauseMedia() {
        audioPlayer.pause();
    }

    private void playMedia() {
        audioPlayer.start();
    }
    //接受广播类
    public class MediaPlayerReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String control = intent.getStringExtra("control");
            String filepath = intent.getStringExtra("filepath");
            checkmediaplayer(filepath);
            playMedia();
        }
    }
    private void checkmediaplayer(String name){
        if(audioPlayer==null) {
            audioPlayer = new MediaPlayer();
        }else if(audioPlayer.isPlaying()){
            audioPlayer.stop();
        }
        try {
            audioPlayer.setDataSource(name);
            audioPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class MyThread extends Thread{

        boolean flag = true;//标志位

        int currentstatus = 0;//当前状态
        @Override
        public void run(){
            while(flag){
                Intent intent = new Intent("MyApplication");
                intent.putExtra("myThread",currentstatus);
                sendBroadcast(intent);
                currentstatus++;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
