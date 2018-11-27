package com.wanpiao.master.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class InformationBean0 implements MultiItemEntity {
    private int id;
    private String name;
    private String movieLength;
    private String background_picture;
    private int itemType;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOT = 1;

    public String getBackground_picture() {
        return background_picture;
    }

    public void setBackground_picture(String background_picture) {
        this.background_picture = background_picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieLength(){
        return movieLength;
    }

    public void setMovieLength(String movieLength){
        this.movieLength = movieLength;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType){
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "InterViewBean2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", background_picture='" + background_picture + '\'' +
                ", movieLength='" + movieLength + '\'' +
                '}';
    }
}
