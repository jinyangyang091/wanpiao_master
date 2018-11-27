package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.bean.MyCommentBean;
import com.wanpiao.master.mvp.model.bean.MyInfoBean;
import com.wanpiao.master.utils.TimeHMSSwitchUtil;
import com.wanpiao.master.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyCommentAdapter extends BaseMultiItemQuickAdapter<MyCommentBean, BaseViewHolder> {
    private Context context;
    private MediaPlayer mediaPlayer;
    private ImageView lastVoicePlayImg;
    private ImageView lastVoicePlayImgGif;
    @Inject
    public MyCommentAdapter(List<MyCommentBean> data, Context context, MediaPlayer mediaPlayer){
        super(data);
        addItemType(MyInfoBean.TYPE_NORMAL, R.layout.item_comments);
        this.context = context;
        this.mediaPlayer = mediaPlayer;
    }
    @Override
    protected void convert(BaseViewHolder helper, MyCommentBean item) {
        helper.setText(R.id.name, item.getName());
        ImageView comment_img = (ImageView)helper.getView(R.id.comment_img);
        ImageView voice_play_icon = (ImageView)helper.getView(R.id.voice_play_icon);
        ImageView voice_play_icon_gif = (ImageView)helper.getView(R.id.voice_play_icon_gif);
        TextView comment_desc = (TextView)helper.getView(R.id.comment_desc);
        User.showIcon(comment_img, item.getBackground_picture());
        //这里也要写一个横向布局的Recyclerview
        RecyclerView actor = (RecyclerView)helper.getView(R.id.actor);

        helper.setText(R.id.comment_desc, "    "+item.getCommentDesc());
        helper.setText(R.id.director, "导演："+item.getDirector());
        helper.setText(R.id.showtime, item.getShowtime());
        helper.setText(R.id.mScore, item.getmScore()+"");
        LinearLayoutManager interviewLinearLayoutManager = new LinearLayoutManager(context);
        interviewLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        actor.setLayoutManager(interviewLinearLayoutManager);
        actor.addItemDecoration(new MyCommentAdapter.SpacesItemDecoration(5));
        //设置该属性让recyclerview滑动没有黏连的感觉
        actor.setNestedScrollingEnabled(false);
        Glide.with(mContext).load(R.mipmap.icon_yuyin_white1).into(voice_play_icon_gif);
        voice_play_icon.setVisibility(View.VISIBLE);
        voice_play_icon_gif.setVisibility(View.GONE);
        Log.d("jinyangyang+","item.getActor ======== "+item.getActor());
        String[] actors = item.getActor().split(",");
        List<MyCommentBean> myCommentBeans = new ArrayList<>();
        for(int i=0;i<actors.length;i++){
            MyCommentBean myCommentBean = new MyCommentBean();
            myCommentBean.setActor(actors[i]);
            myCommentBeans.add(myCommentBean);
        }
        ActorAdapter2 actorAdapter = new ActorAdapter2(myCommentBeans, context);
        actorAdapter.setNewData(myCommentBeans);
        actor.setAdapter(actorAdapter);


        if(!item.getVoiceUrl().equals("")&&item.getCommentDesc().equals("")){
            helper.itemView.findViewById(R.id.voice_releative).setVisibility(View.VISIBLE);
            int length = (int)item.getVoiceLength();
            helper.setText(R.id.voice_length, ""+TimeHMSSwitchUtil.getTime(length)+"'");
            helper.itemView.findViewById(R.id.voice_releative).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView itemImg = (ImageView) v.findViewById(R.id.voice_play_icon);
                    ImageView itemImgGif = (ImageView)v.findViewById(R.id.voice_play_icon_gif);
                    try{
                        if(mediaPlayer!=null){
                            if(mediaPlayer.isPlaying()){
                                Log.d("jinyangyang+","mediaPlayerUrl isPlaying");
                                mediaPlayer.stop();
                                if (lastVoicePlayImg!=null){
                                    //lastVoicePlayImg.setImageResource(R.mipmap.icon_yuyin);
                                    lastVoicePlayImg.setVisibility(View.VISIBLE);
                                    lastVoicePlayImgGif.setVisibility(View.GONE);
                                }
                                return;
                            }
                        }
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(item.getVoiceUrl());
                        mediaPlayer.prepare();
                        mediaPlayer.start();

//                        itemImg.setImageResource(R.mipmap.nav_icon_one);
                        itemImg.setVisibility(View.GONE);
                        itemImgGif.setVisibility(View.VISIBLE);

                        lastVoicePlayImg = itemImg;
                        lastVoicePlayImgGif = itemImgGif;

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                //当网络流文件播放完成时候调用的方法
                                //ToastUtil.show(App.getInstance(), "语音文件播放结束");
                                //itemImg.setImageResource(R.mipmap.icon_yuyin);
                                itemImg.setVisibility(View.VISIBLE);
                                itemImgGif.setVisibility(View.GONE);
                            }
                        });

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) comment_desc.getLayoutParams();
            lp.topMargin = 0;
            lp.bottomMargin = 0;
            comment_desc.setLayoutParams(lp);

//            android:layout_marginLeft="14dp"
//            android:layout_marginRight="14dp"
//            android:layout_marginTop="8dp"
//            android:layout_marginBottom="20dp"
        }else if(!item.getVoiceUrl().equals("")&&!item.getCommentDesc().equals("")){
            helper.itemView.findViewById(R.id.voice_releative).setVisibility(View.VISIBLE);
            int length = (int)item.getVoiceLength();
            helper.setText(R.id.voice_length, ""+TimeHMSSwitchUtil.getTime(length)+"'");
            helper.itemView.findViewById(R.id.voice_releative).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView itemImg = (ImageView) v.findViewById(R.id.voice_play_icon);
                    ImageView itemImgGif = (ImageView)v.findViewById(R.id.voice_play_icon_gif);
                    try{
                        if(mediaPlayer!=null){
                            if(mediaPlayer.isPlaying()){
                                Log.d("jinyangyang+","mediaPlayerUrl isPlaying");
                                mediaPlayer.stop();
                                if (lastVoicePlayImg!=null){
                                    //lastVoicePlayImg.setImageResource(R.mipmap.icon_yuyin);

                                    lastVoicePlayImg.setVisibility(View.VISIBLE);
                                    lastVoicePlayImgGif.setVisibility(View.GONE);

                                }
                                return;
                            }
                        }
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(item.getVoiceUrl());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
//                        itemImg.setImageResource(R.mipmap.nav_icon_one);

                        itemImg.setVisibility(View.GONE);
                        itemImgGif.setVisibility(View.VISIBLE);

                        lastVoicePlayImg = itemImg;
                        lastVoicePlayImgGif = itemImgGif;
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                //当网络流文件播放完成时候调用的方法
                                //ToastUtil.show(App.getInstance(), "语音文件播放结束");
                                //itemImg.setImageResource(R.mipmap.icon_yuyin);
                                itemImg.setVisibility(View.VISIBLE);
                                itemImgGif.setVisibility(View.GONE);
                            }
                        });

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) comment_desc.getLayoutParams();
            lp.topMargin = 8;
            lp.bottomMargin = 20;
            comment_desc.setLayoutParams(lp);
        }else {
            helper.itemView.findViewById(R.id.voice_releative).setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) comment_desc.getLayoutParams();
            lp.topMargin = 8;
            lp.bottomMargin = 20;
            comment_desc.setLayoutParams(lp);
        }
    }

    class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.right = space;
        }
    }

    public void stopMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                Log.d("jinyangyang+", "mediaPlayerUrl isPlaying");
                mediaPlayer.stop();
                mediaPlayer.release();;
                mediaPlayer= null;
            }
        }
    }
}
