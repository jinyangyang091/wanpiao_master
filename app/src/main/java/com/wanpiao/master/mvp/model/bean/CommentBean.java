package com.wanpiao.master.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class CommentBean implements MultiItemEntity {
    private int itemType;
    private String userHead;
    private String userName;
    private String commentTime;
    private float score;
    private String commentDesc;
    private String voiceUrl ;
    private double voiceLength;

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceLength(double voiceLength) {
        this.voiceLength = voiceLength;
    }

    public double getVoiceLength() {
        return voiceLength;
    }

    public void setUserHead(String userHead){
        this.userHead = userHead;
    }

    public String getUserHead(){
        return userHead;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setCommentTime(String commentTime){
        this.commentTime = commentTime;
    }

    public String getCommentTime(){
        return commentTime;
    }

    public void setScore(float score){
        this.score = score;
    }

    public float getScore(){
        return score;
    }

    public void setCommentDesc(String commentDesc){
        this.commentDesc = commentDesc;
    }

    public String getCommentDesc(){
        return commentDesc;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
