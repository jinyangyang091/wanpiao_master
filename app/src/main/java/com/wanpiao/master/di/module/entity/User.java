package com.wanpiao.master.di.module.entity;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.wanpiao.master.glide.GlideApp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class User extends BaseObservable {
    private String firms_id;
    private String token;
    private String head_url;
    private String jc_title;

    @Bindable
    public String getFirms_id() {
        return firms_id;
    }

    public void setFirms_id(String firms_id) {
        this.firms_id = firms_id;
       // notifyPropertyChanged(BR.firms_id);
    }

    @BindingAdapter("show")
    public static void showIcon(ImageView iv, String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            GlideApp.with(iv).load(
                    new GlideUrl(imgUrl))
                    //.error(R.mipmap.syapp_sy_sjlogo_mr_icon)
                    .circleCrop()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);
        }
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getJc_title() {
        return jc_title;
    }

    public void setJc_title(String jc_title) {
        this.jc_title = jc_title;
    }
}
