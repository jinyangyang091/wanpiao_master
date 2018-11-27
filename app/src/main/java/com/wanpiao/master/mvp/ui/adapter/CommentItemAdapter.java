package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.bean.CommentBean;
import com.wanpiao.master.mvp.model.bean.InformationBean;
import com.wanpiao.master.utils.TimeHMSSwitchUtil;
import com.wanpiao.master.widgets.views.RatingBar;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class CommentItemAdapter extends BaseMultiItemQuickAdapter<CommentBean, BaseViewHolder>{

    private Context context;
    private MediaPlayer mediaPlayer;
    private ImageView lastVoicePlayImg;
    private ImageView lastVoicePlayImgGif;
    @Inject
    public CommentItemAdapter( List<CommentBean> data, Context context, MediaPlayer mediaPlayer) {
        super(data);
        Log.d("jinyangyang+","PrevueMovieAdapter Constructor be called");
        addItemType(InformationBean.TYPE_NORMAL, R.layout.item_comment);
        this.context = context;
        this.mediaPlayer = mediaPlayer;
    }
    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {
        ImageView imageView = (ImageView) helper.itemView.findViewById(R.id.user_head_img);
        ImageView voice_state = (ImageView)helper.getView(R.id.voice_state);
        ImageView voice_state_gif = (ImageView)helper.getView(R.id.voice_state_gif);
        Glide.with(mContext).load(R.mipmap.icon_yuyin1).into(voice_state_gif);
        TextView item_comment = (TextView)helper.itemView.findViewById(R.id.item_comment);
        if(item.getUserHead().equals("")){
            imageView.setImageResource(R.mipmap.infor_def_hard);
        }else {
            User.showIcon(imageView, item.getUserHead());
        }
        helper.setText(R.id.user_comment_time, item.getCommentTime());
        helper.setText(R.id.user_name, item.getUserName());
        helper.setText(R.id.item_comment, item.getCommentDesc());
        helper.setText(R.id.comment_score, item.getScore()+"");
        voice_state.setVisibility(View.VISIBLE);
        voice_state_gif.setVisibility(View.GONE);
        float  dmScore = item.getScore();
        int mScore = Integer.parseInt(new java.text.DecimalFormat("0").format(dmScore))/2;
        if(mScore>5){
            mScore = 5;
        }
        RatingBar ratingBar = (RatingBar)helper.itemView.findViewById(R.id.rb_small);
        ratingBar.setSelectedNumber(mScore);
        if(!item.getVoiceUrl().equals("")&&item.getCommentDesc().equals("")){
            helper.itemView.findViewById(R.id.voice_releative).setVisibility(View.VISIBLE);
            int length = (int)item.getVoiceLength();
            helper.setText(R.id.voice_length, ""+TimeHMSSwitchUtil.getTime(length)+"'");
            //helper.setText(R.id.voice_length, ""+length+"'");
            helper.itemView.findViewById(R.id.voice_releative).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView itemImg = (ImageView) v.findViewById(R.id.voice_state);
                    ImageView itemImgGif = (ImageView)v.findViewById(R.id.voice_state_gif);
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
//                        if(mediaPlayer!=null){
//                            mediaPlayer.stop();
//                        }
                        //mediaPlayer = null;
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(item.getVoiceUrl());
                        mediaPlayer.prepare();
                        mediaPlayer.start();

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

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) item_comment.getLayoutParams();
            lp.topMargin = 0;
            lp.bottomMargin = 0;
            item_comment.setLayoutParams(lp);

//            android:layout_marginLeft="20dp"
//            android:layout_marginRight="20dp"
//            android:layout_marginBottom="10dp"
//            android:layout_marginTop="16dp"

        }else if(!item.getVoiceUrl().equals("")&&!item.getCommentDesc().equals("")){
            helper.itemView.findViewById(R.id.voice_releative).setVisibility(View.VISIBLE);
            int length = (int)item.getVoiceLength();
            helper.setText(R.id.voice_length, ""+TimeHMSSwitchUtil.getTime(length)+"'");
            //helper.setText(R.id.voice_length, ""+length+"'");
            helper.itemView.findViewById(R.id.voice_releative).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView itemImg = (ImageView) v.findViewById(R.id.voice_state);
                    ImageView itemImgGif = (ImageView)v.findViewById(R.id.voice_state_gif);
                    try{
                        if(mediaPlayer!=null){
                            if(mediaPlayer.isPlaying()){
                                Log.d("jinyangyang+","mediaPlayerUrl isPlaying");
                                mediaPlayer.stop();
                                if (lastVoicePlayImg!=null){
//                                    lastVoicePlayImg.setImageResource(R.mipmap.icon_yuyin);
                                    lastVoicePlayImg.setVisibility(View.VISIBLE);
                                    lastVoicePlayImgGif.setVisibility(View.GONE);

                                }
                                return;
                            }
                        }
//                        if(mediaPlayer!=null){
//                            mediaPlayer.stop();
//                        }
                        //mediaPlayer = null;
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(item.getVoiceUrl());
                        mediaPlayer.prepare();
                        mediaPlayer.start();

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
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) item_comment.getLayoutParams();
            lp.topMargin = 16;
            lp.bottomMargin = 10;
        }else {
            helper.itemView.findViewById(R.id.voice_releative).setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) item_comment.getLayoutParams();
            lp.topMargin = 16;
            lp.bottomMargin = 10;
            item_comment.setLayoutParams(lp);
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
