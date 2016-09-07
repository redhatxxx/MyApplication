package com.example.administrator.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.staticfield.BroadAction;
import com.example.administrator.myapplication.toolclasses.FileUtils;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView fileList;
    BaseAdapter fileAdapter;
    TextView titlesong;
    List dataList;
    FileUtils fileutile;
    MainActivityReceive mainreceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        checkPermission();
        initData();
        init();
        componentListener();
    }

    /*
    * 初始化控件监听事件
    * */
    private void componentListener() {
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout clickItem = (LinearLayout) view;
                TextView text = (TextView) clickItem.getChildAt(1);//获取选中项的TextView
                String msg = text.getText().toString();
                titlesong = (TextView) findViewById(R.id.titletext);
                titlesong.setText(msg);
                String songpath = ((TextView) clickItem.getChildAt(2)).getText().toString();
                jumpToPaly(songpath);
                showMessage(msg);
            }
        });
    }

    private void jumpToPaly(String songpath) {
        Intent intentbroad = new Intent(BroadAction.CTL_ACTION);
        intentbroad.putExtra("control","0x001");
        intentbroad.putExtra("filepath",songpath);
        sendBroadcast(intentbroad);
    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    /*
    * 初始化基础数据
    * */
    private void initData() {
        fileutile = new FileUtils();
        dataList = fileutile.getFileListByFileTypeWithPath(this,new String[]{"mp3","wav"});

        mainreceiver = new MainActivityReceive();
        //过滤广播类型
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadAction.UPDATE_ACTION);
        registerReceiver(mainreceiver,filter);
    }

    /*
    * 初始化基本控件
    * */
    private void init() {
        titlesong = (TextView) findViewById(R.id.titletext);
        titlesong.setText("Touch the sky");
        fileList = (ListView) findViewById(R.id.filelist);
        if(dataList==null||dataList.size()<=0){
            dataList.add(new String("no data!"));
        }
        fileAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                //动态生成列表中的项对应的view，每个view由LinearLayout
                //中包含一个ImageView及两个TextView组成
                LinearLayout singal_Item = new LinearLayout(MainActivity.this);

                singal_Item.setOrientation(LinearLayout.HORIZONTAL);//朝向向下
                singal_Item.setPadding(5, 5, 5, 5);

                ImageView albumImage = new ImageView(MainActivity.this);
                albumImage.setImageDrawable(getResources().getDrawable(R.drawable.front));
                albumImage.setScaleType(ImageView.ScaleType.FIT_XY);
                albumImage.setLayoutParams(new Gallery.LayoutParams(100, 98));

                singal_Item.addView(albumImage);

                TextView songName = new TextView(MainActivity.this);
                songName.setText(((AudioFile)dataList.get(i)).getFielname());
                songName.setTextSize(24);
                songName.setTextColor(Color.BLUE);
                songName.setPadding(5, 5, 5, 5);
                songName.setGravity(Gravity.LEFT);

                TextView songpath = new TextView(MainActivity.this);
                songpath.setText(((AudioFile)dataList.get(i)).getFilepath());
                songpath.setVisibility(View.INVISIBLE);

                singal_Item.addView(songName);
                singal_Item.addView(songpath);
                return singal_Item;
            }
        };

        fileList.setAdapter(fileAdapter);
    }
    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            int write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (write != PackageManager.PERMISSION_GRANTED || read != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
            } else {
                String name = "CrashDirectory";
                File file1 = new File(Environment.getExternalStorageDirectory(), name);
                if (file1.mkdirs()) {
                    Log.i("wytings", "permission -------------> " + file1.getAbsolutePath());
                } else {
                    Log.i("wytings", "permission -------------fail to make file ");
                }
            }
        } else {
            Log.i("wytings", "------------- Build.VERSION.SDK_INT < 23 ------------");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 300) {
            Log.i("wytings", "--------------requestCode == 300->" + requestCode + "," + permissions.length + "," + grantResults.length);
        } else {
            Log.i("wytings", "--------------requestCode != 300->" + requestCode + "," + permissions + "," + grantResults);
        }
    }

    //接受广播类
    public class MainActivityReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
