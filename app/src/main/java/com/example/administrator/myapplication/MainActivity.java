package com.example.administrator.myapplication;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.administrator.myapplication.toolclasses.FileUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView fileList;
    BaseAdapter fileAdapter;
    TextView titlesong;
    List dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        initData();
        init();
        componentListener();
    }

    /*
    * 初始化控件监听事件
    * */
    private void componentListener() {
        fileList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout clickItem = (LinearLayout) view;
                TextView text = (TextView) clickItem.getChildAt(1);//获取选中项的TextView
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout clickItem = (LinearLayout) view;
                TextView text = (TextView) clickItem.getChildAt(1);//获取选中项的TextView
            }
        });
    }

    /*
    * 初始化基础数据
    * */
    private void initData() {
        dataList = FileUtils.getFileListByFileTypeWithPath(this,new String[]{"mp3","wav"});
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
                songName.setText(dataList.get(i).toString());
                songName.setTextSize(24);
                songName.setTextColor(Color.BLUE);
                songName.setPadding(5, 5, 5, 5);
                songName.setGravity(Gravity.LEFT);

                singal_Item.addView(songName);
                return singal_Item;
            }
        };

        fileList.setAdapter(fileAdapter);
    }


//////////////////////////////////////////////////////////////
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
