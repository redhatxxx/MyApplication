package com.example.administrator.myapplication.toolclasses;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.administrator.myapplication.AudioFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 * 按类型检索文件
 */
public class FileUtils {

    private String[] searchType;

    public FileUtils(){

    }

    //通过类型查找文件
    public List getFileListByFileType(Context context,String[] filetypes){

        //返回列表
        List datalist = new ArrayList();
        //从存储中获取
        //内存根目录
        File rootpath = Environment.getRootDirectory();

        //存储卡根目录
        File sdpath = Environment.getExternalStorageDirectory();

        Uri fileUri = MediaStore.Files.getContentUri(rootpath.getPath());

        //筛选列，只筛选：文件路径和不含后缀的文件名
        String[] projection = new String[]{MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.TITLE};

        //构造筛选语句
        String selection = "";
        for(int i=0;i<filetypes.length;i++){
            if(i!=0){
                selection = selection+" OR ";
            }
            selection = selection+ MediaStore.Files.FileColumns.DATA+" LIKE '%"+filetypes[i]+"'";
        }

        //按时间递增序列对结果进行排序，后面从后向前移动游标就可以实现时间递减
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
        //获取内容解析器对象
        ContentResolver resolver = context.getContentResolver();
        //获取游标
        Cursor cursor = resolver.query(fileUri,projection,selection,null,sortOrder);

        if(cursor==null){
            return null;
        }
        //游标从最后开始往前递减，以此实现时间递减顺序
        if(cursor.moveToLast()){
            do{
                //输出文件完整路径
                String data = cursor.getString(0);
                Log.d("tag",data);
                datalist.add(data);
            }while (cursor.moveToPrevious());
        }
        return datalist;
    }
    //遍历文件夹查找文件
    public List getFileListByFileTypeWithPath(Context context,String[] filetypes){

        searchType = filetypes;
        //返回列表
        List datalist = new ArrayList();
        //从存储中获取
        //内存根目录
        File rootpath = Environment.getRootDirectory();
        datalist.addAll(getAudioFiles(rootpath));

        //存储卡根目录
        File sdpath = Environment.getExternalStorageDirectory();
        if(sdpath.canRead()){
            int num=1;
        }
        datalist.addAll(getAudioFiles(sdpath));
        return datalist;
    }

    private List getAudioFiles(File file) {
        List files = new ArrayList();
        if(file.isDirectory()){
            File[] filelist = file.listFiles();
            if(filelist==null)
                return  files;
            for(int i=0;i<filelist.length;i++){
                if(!filelist[i].isDirectory()){
                    String filename = filelist[i].getName();
                    filename = filename.toLowerCase();
                    if(matchType(filename)) {
                        files.add(getFileInfo(filelist[i]));
                    }
                }else {
                    files.addAll(getAudioFiles(filelist[i]));
                }
            }
        }else {
            String filename = file.getName();
            filename = filename.toLowerCase();
            if(matchType(filename))
                files.add(getFileInfo(file));
        }
        return files;
    }

    public boolean matchType(String filename){
        boolean match= false;
        if(searchType==null||searchType.length<=0)
            return match;
        else{
            for(int i=0;i<searchType.length;i++){
                if (filename.endsWith(searchType[i])) {
                    match = true;
                    break;
                }
            }
            return match;
        }
    }

    public AudioFile getFileInfo(File file){
        AudioFile audio = new AudioFile();
        String filename = file.getName();
        audio.setFielname(file.getName());
        audio.setFilepath(file.getPath());
        audio.setFiletype(filename.substring(filename.lastIndexOf(".")+1,filename.length()));
        return audio;
    }
}
