package com.wanpiao.master.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MyInfoBean implements MultiItemEntity {
    private int id;
    private String name;
    private String background_picture;
    private int itemType;
    private float rate;
    private String director;
    private String actor;
    private String showtime;
    private double score;
    private String movieType;
    private int status;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOT = 1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setItemType(int itemType){
        this.itemType = itemType;
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
