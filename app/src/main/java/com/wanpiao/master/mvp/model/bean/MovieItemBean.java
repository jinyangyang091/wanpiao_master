package com.wanpiao.master.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MovieItemBean implements MultiItemEntity {
    private int id;
    private String name;
    private String background_picture;
    private int itemType;
    private float rate;
    private int commentNums;
    private int laudNums;
    private String showtime;
    private String userGoodNum;


    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOT = 1;

    public void setUserGoodNum(String userGoodNum) {
        this.userGoodNum = userGoodNum;
    }

    public String getUserGoodNum() {
        return userGoodNum;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getShowtime() {
        return showtime;
    }

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

    public float getRate(){
        return rate;
    }

    public void setRate(float rate){
        this.rate = rate;
    }

    public int getCommentNums(){
        return commentNums;
    }

    public void setCommentNums(int commentNums){
        this.commentNums = commentNums;
    }

    public int getLaudNums(){
        return laudNums;
    }

    public void setLaudNums(int laudNums){
        this.laudNums = laudNums;
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
        return "";
    }

}
