package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2016/8/6.
 * 音频文件基础类
 */
public class AudioFile {
    //文件名
    private String fielname;
    //文件后缀名
    private String filetype;
    //文件路径
    private String filepath;
    //所属专辑
    private String album;
    //歌手名
    private String singerName;
    //专辑封面
    private String albumImage;

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getFielname() {
        return fielname;
    }

    public void setFielname(String fielname) {
        this.fielname = fielname;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
}
