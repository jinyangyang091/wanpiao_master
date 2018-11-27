package com.wanpiao.master.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class GuideBean implements MultiItemEntity {
    private int itemType;
    private int localImg;
    public static final int TYPE_NORMAL = 0;
    public void setLocalImg(int localImg){
        this.localImg = localImg;
    }
    public int getLocalImg(){
        return localImg;
    }
    public void setItemType(int itemType){
        this.itemType = itemType;
    }
    @Override
    public int getItemType() {
        return itemType;
    }
}
