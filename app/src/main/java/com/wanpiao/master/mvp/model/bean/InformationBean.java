package com.wanpiao.master.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class InformationBean implements MultiItemEntity {
    private int id;
    private String name;
    private String bannerVsmallUrl;
    private int saleType;
    private String actor;
    private String director;
    private String description;
    private double score;
    private String show_mark;
    private String open_time;
    private String background_picture;
    private int like_num;
    private int length;
    private String poster;
    private String playerUrl;
    private String infoDetailUrl;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOT = 1;

    private int itemType;

    public void setPlayerUrl(String playerUrl){
        this.playerUrl = playerUrl;
    }

    public String getPlayerUrl(){
        return playerUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;

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

    public String getBannerVsmallUrl() {
        return bannerVsmallUrl;
    }

    public void setBannerVsmallUrl(String bannerVsmallUrl) {
        this.bannerVsmallUrl = bannerVsmallUrl;
    }

    public int getSaleType() {
        return saleType;
    }

    public void setSaleType(int saleType) {
        this.saleType = saleType;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getShow_mark() {
        return show_mark;
    }

    public void setShow_mark(String show_mark) {
        this.show_mark = show_mark;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getBackground_picture() {
        return background_picture;
    }

    public void setBackground_picture(String background_picture) {
        this.background_picture = background_picture;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setInfoDetailUrl(String infoDetailUrl) {
        this.infoDetailUrl = infoDetailUrl;
    }

    public String getInfoDetailUrl() {
        return infoDetailUrl;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
