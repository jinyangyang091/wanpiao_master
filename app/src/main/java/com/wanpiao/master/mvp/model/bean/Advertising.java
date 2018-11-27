package com.wanpiao.master.mvp.model.bean;

public class Advertising {
    private String advertiseUrl;
    private String advertiseJumpUrl;
    private String jumpType;
    private String jumpId;
    private float rate;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpType() {
        return jumpType;
    }

    public String getJumpId() {
        return jumpId;
    }

    public void setJumpId(String jumpId) {
        this.jumpId = jumpId;
    }

    public String getImgUrl(){
        return advertiseUrl;
    }

    public void setImgUrl(String imgUrl){
        this.advertiseUrl = imgUrl;
    }

    public String getTurnUrl(){
        return  advertiseJumpUrl;
    }

    public void setTurnUrl(String turnUrl){
        this.advertiseJumpUrl = turnUrl;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }
}
