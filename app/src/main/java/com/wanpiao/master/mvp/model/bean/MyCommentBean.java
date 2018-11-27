package com.wanpiao.master.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MyCommentBean implements MultiItemEntity {
    private int id;
    private String name;
    private String background_picture;
    private int itemType;
    private double rate;
    private String director;
    private String actor;
    private String showtime;
    private String commentDesc;
    private double mScore;
    private String voiceUrl ;
    private double voiceLength;

    public void setVoiceLength(double voiceLength) {
        this.voiceLength = voiceLength;
    }

    public double getVoiceLength() {
        return voiceLength;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setmScore(double mScore) {
        this.mScore = mScore;
    }

    public double getmScore() {
        return mScore;
    }

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

    public double getRate(){
        return rate;
    }

    public void setRate(float rate){
        this.rate = rate;
    }

    public void setDirector(String director){
        this.director = director;
    }

    public String getDirector(){
        return director;
    }

    public void setActor(String actor){
        this.actor = actor;
    }

    public String getActor(){
        return actor;
    }

    public void setShowtime(String showtime){
        this.showtime = showtime;
    }

    public String getShowtime(){
        return showtime;
    }

    public void setItemType(int itemType){
        this.itemType = itemType;
    }

    public String getCommentDesc(){
        return commentDesc;
    }

    public void setCommentDesc(String commentDesc){
        this.commentDesc = commentDesc;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }
}
