package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.bean.MovieItemBean;
import com.wanpiao.master.mvp.ui.activity.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    private Context mContext;
    private List<Integer> mDatas;
    private List<MovieItemBean> data = new ArrayList<>();
    private Bitmap bitmap;
    private String Flag ;
    private String loadtype;
    private OnItemPhotoChangedListener mOnItemPhotoChangedListener;

    public RecyclerAdapter(List<MovieItemBean> data, Context context, String loadtype){
        this.mContext = context;
        this.data = data;
        this.loadtype = loadtype;
    }

    public RecyclerAdapter(Context mContext, List<Integer> mDatas, String Flag) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.Flag = Flag;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        DLog.d(MainActivity.TAG, "RecyclerAdapter onCreateViewHolder" + " width = " + parent.getWidth());
        View itemView = null;
        itemView = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        Log.d("jinyangyang+","onBindViewHolder  position is "+position);
//        holder.mView.setImageResource(mDatas.get(holder.getAdapterPosition()));
        //User.showIcon(holder.mView, data.get(position).getBackground_picture());
        Glide.with(mContext).load(data.get(position).getBackground_picture()).into(holder.mView);
        if(loadtype.equals("1")){
            holder.score_img.setVisibility(View.VISIBLE);
            holder.score_text.setVisibility(View.VISIBLE);
            float rate = data.get(position).getRate();
//            if(rate>5){
//                holder.score_text.setText("5.0");
//            }else {
                holder.score_text.setText(""+data.get(position).getRate());
//            }
        }else if(loadtype.equals("2")){
            holder.score_img.setVisibility(View.GONE);
            holder.score_text.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
//        return mDatas.size();
//        Log.d("jinyangyang+","getItemCount is "+data.size());
        return data.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        final ImageView mView;
        FloatingActionButton mChange;
        TextView title_text;
        ImageView score_img;
        TextView score_text;
        MyHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.iv_photo);
            mChange = itemView.findViewById(R.id.fab_change);
            title_text = (TextView)itemView.findViewById(R.id.title_text);
            score_img = (ImageView)itemView.findViewById(R.id.score_img);
            score_text = (TextView)itemView.findViewById(R.id.score_text);
        }
    }

    /**
     * 获取position位置的resId
     *
     * @param position int
     * @return int
     */
    public int getResId(int position) {
        return mDatas == null ? 0 : mDatas.get(position);
    }

    public void setOnItemPhotoChangedListener(OnItemPhotoChangedListener mOnItemPhotoChangedListener) {
        this.mOnItemPhotoChangedListener = mOnItemPhotoChangedListener;
    }

    public interface OnItemPhotoChangedListener {
        /**
         * 局部更新后需要替换背景图片
         */
        void onItemPhotoChanged();
    }


}
