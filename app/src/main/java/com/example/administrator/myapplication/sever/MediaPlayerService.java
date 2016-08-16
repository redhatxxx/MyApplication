package com.example.administrator.myapplication.sever;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MediaPlayerService extends Service {
    MyThread msgthread;
    public MediaPlayerService() {
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
    public IBinder onBind(Intent intent) {
        return null;
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
