package com.example.administrator.myapplication.sever;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.administrator.myapplication.MainActivity;

public class MediaPlayerService extends Service {
    MyThread msgthread;
    MediaPlayer audioPlayer;
    public MediaPlayerService() {
    }

    @Override
    public  void onCreate(){
        super.onCreate();
        //设置广播过滤
        IntentFilter audioFilter = new IntentFilter();

        audioPlayer = new MediaPlayer();
    }
    @Override
    public void onStart(Intent intent,int startId){
        msgthread = new MyThread();
        msgthread.start();
        super.onStart(intent,startId);
    }
    @Override
    public void onDestroy(){
        msgthread.flag = false;
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    //广播接受
    class MusicReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

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
