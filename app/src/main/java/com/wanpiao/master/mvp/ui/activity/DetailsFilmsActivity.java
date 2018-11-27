package com.wanpiao.master.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityDetailsFilmsBinding;
import com.wanpiao.master.glide.GlideApp;
import com.wanpiao.master.mvp.contract.DetailsFilmsActivityContract;
import com.wanpiao.master.mvp.model.bean.CommentBean;
import com.wanpiao.master.mvp.model.bean.InformationBean;
import com.wanpiao.master.mvp.presenter.DetailsFilmsPresenter;
import com.wanpiao.master.mvp.ui.adapter.CommentItemAdapter;
import com.wanpiao.master.mvp.ui.adapter.FilmTypeAdapter;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.TimeSwitchUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailsFilmsActivity extends BaseActivity<DetailsFilmsPresenter, ActivityDetailsFilmsBinding> implements DetailsFilmsActivityContract.View,SpringView.OnFreshListener {
    public static void startDetailsFilmsActivity(Context context) {
        Intent intent = new Intent(context, DetailsFilmsActivity.class);
        context.startActivity(intent);
    }
    private  String movieId;
    private  String movieName;
    private  int pageNum = 0;
    private FilmTypeAdapter filmTypeAdapter;
    private List<CommentBean> commentBeans = new ArrayList<>();
    String s = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540539177&di=4603490622e9966b7782727b1abf512c&imgtype=jpg&er=1&src=http%3A%2F%2Fimage.tmdb.org%2Ft%2Fp%2Foriginal%2FpPcg9z8M9AqxZeCHOwjU0Wk4iF8.jpg";
    private CommentItemAdapter commentItemAdapter;
    private boolean noticeFlag = false;
    private boolean dianzanFlag = false;
    @Override
    public void showDetailFilmData(String s) {
        bin.svHome.onFinishFreshAndLoad();
        try{
            JSONObject jsonObject = new JSONObject(s);
            String messageId = jsonObject.optString("messageId");
            if (messageId.equals("200")){

                JSONObject data = jsonObject.optJSONObject("data");
                JSONObject movie = data.optJSONObject("movie");
                JSONObject topComment = data.optJSONObject("topComment");
                String mName = movie.optString("mName");
                movieName = mName;
                String mBigPoster = movie.optString("mBackground");
                String mLevel = movie.optString("mLevel");
                String mType = movie.optString("mType");
                String mGoodNum = movie.optString("mGoodNum");
                String mLanguage = movie.optString("mLanguage");
                String mDetails = movie.optString("mDetails");
                int mLength = movie.optInt("mTime");
                //String filmType = "悬疑,冒险,奇幻,动作";
                String filmType = mType;
                String[] filmTypeArray = filmType.split(",");
                String commentNum = movie.optString("commentNum");
                String mDirector = movie.optString("mDirector");
                String mToStar = movie.optString("mToStar");
                String mPlayTime = movie.optString("mPlayTime");

                int notice = movie.optInt("notice");
                int goodNum = movie.optInt("goodNum");
                LinearLayoutManager interviewLinearLayoutManager = new LinearLayoutManager(mContext);
                interviewLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                bin.subFilmType.setLayoutManager(interviewLinearLayoutManager);
                bin.subFilmType.addItemDecoration(new DetailsFilmsActivity.SpacesItemDecoration(18));
                //设置该属性让recyclerview滑动没有黏连的感觉
                bin.subFilmType.setNestedScrollingEnabled(false);
                List<InformationBean> informationBeans = new ArrayList<>();
                for(int i=0;i<filmTypeArray.length;i++){
                    InformationBean informationBean = new InformationBean();
                    informationBean.setActor(filmTypeArray[i]);
                    informationBeans.add(informationBean);
                }
                FilmTypeAdapter filmTypeAdapter = new FilmTypeAdapter(informationBeans, mContext);
                filmTypeAdapter.setNewData(informationBeans);
                bin.subFilmType.setAdapter(filmTypeAdapter);

                GlideApp.with(this).load(
                        new GlideUrl(mBigPoster))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(bin.ivMovieBg);
                bin.textView.setText(mName);
                bin.movieLength.setText(mLength+"分钟");
                bin.movieLanguage.setText(mLanguage);
                bin.movieLevel.setText(mLevel+"级");
                bin.moviePublicTime.setText("2018年9月20日");
                bin.stvIntroduce.setText(mDetails);
                bin.dianzanNum.setText(""+mGoodNum);
                bin.mDirector.setText(mDirector);
                bin.mToStar.setText(mToStar);
                if(!mPlayTime.equals("")){
                    bin.moviePublicTime.setText(TimeSwitchUtils.dealDateFormat(mPlayTime));
                    bin.mPlayTime.setText(TimeSwitchUtils.dealDateFormat(mPlayTime));
                }
                if(notice == 0){
                    noticeFlag = false;
                    bin.informPre.setImageResource(R.mipmap.icon_inform_pre);
                }else {
                    noticeFlag = true;
                    bin.informPre.setImageResource(R.mipmap.icon_inform_pre_sel);
                }
                if(goodNum == 0){
                    dianzanFlag = false;
                    bin.landPre.setImageResource(R.mipmap.icon_land_pre);
                }else {
                    dianzanFlag = true;
                    bin.landPre.setImageResource(R.mipmap.icon_land_pre_sel);
                }
            }else{
                Log.d("jinyangyang+",messageId);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void refreshDianzanState(String data) {
        try{
            JSONObject jsonObject = new JSONObject(data);
            String messageId = jsonObject.optString("messageId");
            if(messageId.equals("200")){
                if (dianzanFlag == false){
                    dianzanFlag = true;
                    int curdianzanNum = Integer.parseInt(bin.dianzanNum.getText().toString());
                    bin.dianzanNum.setText(""+(curdianzanNum+1));
                    bin.landPre.setImageResource(R.mipmap.icon_land_pre_sel);
                }else{
                    dianzanFlag = false;
                    int curdianzanNum = Integer.parseInt(bin.dianzanNum.getText().toString());
                    bin.dianzanNum.setText(""+(curdianzanNum-1));
                    bin.landPre.setImageResource(R.mipmap.icon_land_pre);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void refreshInfoState(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String messageId = jsonObject.optString("messageId");
            if(messageId.equals("200")){
                if (noticeFlag == false){
                    noticeFlag = true;
                    bin.informPre.setImageResource(R.mipmap.icon_inform_pre_sel);
                }else{
                    noticeFlag = false;
                    bin.informPre.setImageResource(R.mipmap.icon_inform_pre);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        bin.svHome.setHeader(new AliHeader(mContext, true));
        bin.svHome.setListener(this);
        bin.dianzanReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPUtils.get(App.getInstance(), "isLogin","").equals("1")){
                    if(dianzanFlag){
                        //ToastUtil.show(App.getInstance(), "不能取消点赞哦！");
                        Log.d("jinyangyang+","不能取消点赞哦！");
                    }else {
                        presenter.requestDianZanData(SPUtils.get(App.getInstance(), "id",""),movieId, "1", "0" );

                    }
                }else {
                    LoginActivity.startLoginActivity(mContext);
                }
            }
        });

        bin.commentMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPUtils.get(App.getInstance(), "isLogin","").equals("1")){
                    presenter.requestInfoData(SPUtils.get(App.getInstance(), "id",""),movieId, "1", "1" );
                }else {
                    LoginActivity.startLoginActivity(mContext);
                }
            }
        });
        bin.ivOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_details_films;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {
        Intent intent =getIntent();
        Bundle data=intent.getExtras();
        Log.d("jinyangyang+","data ============ "+data);
        movieId = data.getString("movieId");
        Log.d("jinyangyang+","movieId ========== "+movieId);
        presenter.requestDetailFilmData(movieId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onRefresh() {
        presenter.requestDetailFilmData(movieId);
    }

    @Override
    public void onLoadmore() {

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

}
