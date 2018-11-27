package com.wanpiao.master.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityDetailsFilmBinding;
import com.wanpiao.master.glide.GlideApp;
import com.wanpiao.master.mvp.contract.DetailsFilmActivityContract;
import com.wanpiao.master.mvp.model.bean.CommentBean;
import com.wanpiao.master.mvp.model.bean.InformationBean;
import com.wanpiao.master.mvp.presenter.DetailsFilmPresenter;
import com.wanpiao.master.mvp.ui.adapter.CommentItemAdapter;
import com.wanpiao.master.mvp.ui.adapter.FilmTypeAdapter;
import com.wanpiao.master.mvp.ui.fragment.HomePageFragment;
import com.wanpiao.master.utils.MyAlertDialogUtils;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.TimeSwitchUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @Description: #影视详情
 * <p>
 * #0000      @Author: tianxiao     2018/10/19      onCreate
 */
public class DetailsFilmActivity extends BaseActivity<DetailsFilmPresenter, ActivityDetailsFilmBinding> implements DetailsFilmActivityContract.View ,SpringView.OnFreshListener{
    public static void startDetailsFilmActivity(Context context) {
        Intent intent = new Intent(context, DetailsFilmActivity.class);
        context.startActivity(intent);
    }
    public static String ACTION_JIGUANG_LISTCLICK = "com.wanpiao.jiguang.cn";
    private BroadcastReceiver Receiver;
    private boolean dianzanFlag = false;
    private  String movieId;
    private  String movieName;
    private  int pageNum = 0;
    private FilmTypeAdapter filmTypeAdapter;
    List<InformationBean> informationBeans;
    private List<CommentBean> commentBeans = new ArrayList<>();
    String defaultImg = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540539177&di=4603490622e9966b7782727b1abf512c&imgtype=jpg&er=1&src=http%3A%2F%2Fimage.tmdb.org%2Ft%2Fp%2Foriginal%2FpPcg9z8M9AqxZeCHOwjU0Wk4iF8.jpg";
    private CommentItemAdapter commentItemAdapter;
    private int commentFlag = 0;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void initViews(Bundle savedInstanceState) {
        bin.ivOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bin.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });


//        GlideApp.with(this).load(
//                new GlideUrl(defaultImg))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(bin.ivMovieBg);

        bin.dianzanReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPUtils.get(App.getInstance(), "isLogin", "").equals("1")){
                    if(dianzanFlag){
//                        ToastUtil.show(App.getInstance(), "不能取消点赞哦！");
                        Log.d("jinyangyang+","不能取消点赞哦！");
                    }else {
                        presenter.requestDianZanData(SPUtils.get(App.getInstance(), "id",""),movieId, "1", "0" );
                    }
                }else {
                    LoginActivity.startLoginActivity(mContext);
                }
            }
        });
        bin.commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String comment = "影片不错哦！";
                //presenter.commentFilm(movieId, "1", SPUtils.get(App.getInstance(), "id",""),comment);
                //UserReviewsActivity.startUserReviewsActivity(mContext);
                //判定是否登录状态
                if(SPUtils.get(App.getInstance(), "isLogin", "").equals("1")){
                    Intent intent = new Intent(mContext, UserReviewsActivity.class);
                    Bundle b=new Bundle();
                    b.putString("JoinId",movieId);
                    b.putString("movieName", movieName);
                    intent.putExtras(b);
                    startActivity(intent);
                }else {
                    LoginActivity.startLoginActivity(mContext);
                }
            }
        });
        bin.svHome.setHeader(new AliHeader(mContext, true));
        //设置上拉加载更多数据
        bin.svHome.setFooter(new AliFooter(mContext, true));
        bin.svHome.setType(SpringView.Type.FOLLOW);
        bin.svHome.setListener(this);

        LinearLayoutManager interviewLinearLayoutManager = new LinearLayoutManager(mContext);
        interviewLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bin.subComments.setLayoutManager(interviewLinearLayoutManager);
        bin.subComments.addItemDecoration(new DetailsFilmActivity.SpacesItemDecoration2(0));
        //设置该属性让recyclerview滑动没有黏连的感觉
        bin.subComments.setNestedScrollingEnabled(false);

        commentItemAdapter = new CommentItemAdapter(commentBeans, mContext, mediaPlayer);
        bin.subComments.setAdapter(commentItemAdapter);

        LinearLayoutManager interviewLinearLayoutManager2 = new LinearLayoutManager(mContext);
        interviewLinearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        bin.subFilmType.setLayoutManager(interviewLinearLayoutManager2);
        bin.subFilmType.addItemDecoration(new DetailsFilmActivity.SpacesItemDecoration(18));
        //设置该属性让recyclerview滑动没有黏连的感觉
        bin.subFilmType.setNestedScrollingEnabled(false);
        filmTypeAdapter = new FilmTypeAdapter(informationBeans, mContext);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_details_film;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {
        initBroadcastListener();
        Intent intent =getIntent();
        Bundle data=intent.getExtras();
        Log.d("jinyangyang+","data ============ "+data);
        movieId = data.getString("movieId");
        Log.d("jinyangyang+","movieId ========== "+movieId);
        //请求电影详情信息
        commentBeans.clear();
        presenter.requestDetailFilmData(movieId);
        //查询当前电影评论信息
        commentFlag = 1;
        presenter.selectComment(movieId, "0","10");
    }

    @Override
    public void showDetailFilmData(String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);
            String messageId = jsonObject.optString("messageId");
            if (messageId.equals("200")){
                JSONObject data = jsonObject.optJSONObject("data");
                JSONObject movie = data.optJSONObject("movie");
                if(movie != null){
                    String mName = movie.optString("mName");
                    movieName = mName;
                    String mBigPoster = movie.optString("mBackground");
                    String mLevel = movie.optString("mLevel");
                    String mType = movie.optString("mType");
                    Double  dmScore = new Double(movie.optDouble("mScore"));
                    Float fmScore = dmScore.floatValue();
                    int mScore = Integer.parseInt(new java.text.DecimalFormat("0").format(dmScore))/2;
                    //int mScore = 3;
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
                    int goodNum = movie.optInt("goodNum");
                    informationBeans = new ArrayList<>();
                    informationBeans.clear();
                    for(int i=0;i<filmTypeArray.length;i++){
                        InformationBean informationBean = new InformationBean();
                        informationBean.setActor(filmTypeArray[i]);
                        informationBeans.add(informationBean);
                    }

                    filmTypeAdapter.setNewData(informationBeans);
                    bin.subFilmType.setAdapter(filmTypeAdapter);
                    if(mBigPoster.equals("")){
                        mBigPoster = defaultImg;
                    }

                    GlideApp.with(this).load(
                            new GlideUrl(mBigPoster))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(bin.ivMovieBg);
                    bin.textView.setText(mName);
                    bin.rb.setSelectedNumber(mScore);
                    //bin.movieScore.setText(""+dmScore*2);
                    bin.movieScore.setText(""+fmScore);
                    bin.movieLength.setText(mLength+"分钟");
                    bin.movieLanguage.setText(mLanguage);
                    bin.movieLevel.setText(mLevel+"级");
                    bin.moviePublicTime.setText("2018年9月20日");
                    bin.stvIntroduce.setText(mDetails);
                    bin.dianzanNum.setText(""+mGoodNum);
                    if(mGoodNum.equals("")){
                        bin.dianzanNum.setText("0");
                    }
                    if(goodNum == 0){
                        dianzanFlag = false;
                        bin.land.setImageResource(R.mipmap.icon_land_pre);
                    }else {
                        dianzanFlag = true;
                        bin.land.setImageResource(R.mipmap.icon_land);
                    }
                    bin.mDirector.setText(mDirector);
                    bin.mToStar.setText(mToStar);
                    if(!mPlayTime.equals("")){
                        bin.moviePublicTime.setText(TimeSwitchUtils.dealDateFormat(mPlayTime));
                    }
                }
                JSONObject topComment = data.optJSONObject("topComment");
                if (topComment!=null){
                    String userName = topComment.optString("userName");
                    String userPortrait = topComment.optString("userPortrait");
                    String comment = topComment.optString("comment");
                    Double  dmScore = new Double(topComment.optDouble("comScore"));
                    Float fmScore = dmScore.floatValue();
                    int mScore = Integer.parseInt(new java.text.DecimalFormat("0").format(dmScore))/2;
                    bin.rbSmall.setSelectedNumber(mScore);
                    bin.topCommentScore.setText(""+fmScore);
                    bin.userName.setText(userName);
                    if(!userPortrait.equals("")){
                        GlideApp.with(this).load(
                                new GlideUrl(userPortrait))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(bin.ivUserHead);
                    }
                    bin.commentDesc.setText(comment);
                    bin.topCommentTime.setText(TimeSwitchUtils.dealDateFormat2(topComment.optString("comDate")));
                }else {
                    bin.topCommentTitle.setVisibility(View.GONE);
                    bin.topComment.setVisibility(View.GONE);
                }
//                try{
//                    String dd = DetailsFilmActivity.dealDateFormat(movie.optString("mPlayTime"));
//                    Log.d("dd============", dd);
//                }catch (ParseException e){
//                    e.printStackTrace();
//                }


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
                    bin.land.setImageResource(R.mipmap.icon_land);
                }else{
                    dianzanFlag = false;
                    int curdianzanNum = Integer.parseInt(bin.dianzanNum.getText().toString());
                    bin.dianzanNum.setText(""+(curdianzanNum-1));
                    bin.land.setImageResource(R.mipmap.icon_land_pre);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void commentFilm(String data) {
        ToastUtil.show(App.getInstance(), "评论成功");
    }

    @Override
    public void showCommentList(String commentlist) {
        //此处加载评论列表
        try {
            Log.d("showCommentList ", "showCommentList ============= "+commentlist);
            JSONObject jsonObject = new JSONObject(commentlist);
            String messageId = jsonObject.optString("messageId");
            if(messageId.equals("200")){
                JSONObject data = jsonObject.optJSONObject("data");
                String total = data.optString("total");
                Log.d("total ======== ", total);
                bin.commentNum.setText(total);
                JSONArray rows = data.optJSONArray("rows");
                if(rows.length()>0){
                    bin.svHome.onFinishFreshAndLoad();
                    for (int i = 0; i < rows.length(); i++) {
                        JSONObject itemJsonObject = rows.getJSONObject(i);
                        CommentBean commentBean = new CommentBean();
                        commentBean.setUserHead(itemJsonObject.optString("userPortrait"));
                        Log.d("userPortrait = ", itemJsonObject.optString("userPortrait"));
                        commentBean.setUserName(itemJsonObject.optString("userName"));
                        commentBean.setCommentDesc(itemJsonObject.optString("comment"));
                        Double dmScore = new Double(itemJsonObject.optDouble("comScore"));
                        Float fmScore = dmScore.floatValue();
                        commentBean.setScore(fmScore);
                        String comDate = itemJsonObject.optString("comDate");
                        if(!comDate.equals("")){
                            commentBean.setCommentTime(TimeSwitchUtils.dealDateFormat2(comDate));
                        }
                        commentBean.setVoiceUrl(itemJsonObject.optString("commentVoice"));
                        commentBean.setVoiceLength(itemJsonObject.optDouble("voiceLength"));
                        commentBeans.add(commentBean);
                    }
                    commentItemAdapter.setNewData(commentBeans);
                }else{
                    bin.svHome.onFinishFreshAndLoad();
                    //ToastUtil.show(App.getInstance(), "没有更多数据了！");
                    if(commentFlag ==1){
                        bin.commentTitle.setVisibility(View.GONE);
                        bin.commentDivline.setVisibility(View.GONE);
                        bin.commentList.setVisibility(View.GONE);
                        commentFlag = 0;
                    }else {

                    }


                }
            }else {

            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        //请求电影详情信息
        presenter.requestDetailFilmData(movieId);
        //查询当前电影评论信息
        pageNum = 0;
        commentBeans.clear();
        commentFlag = 1;
        presenter.selectComment(movieId, pageNum+"","10");
    }

    @Override
    public void onLoadmore() {
        commentFlag = 0;
        pageNum++;
        presenter.selectComment(movieId,pageNum+"","10");
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
    class SpacesItemDecoration2 extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration2(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
        }
    }

    public static String dealDateFormat(String oldDateStr) throws ParseException{
        //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        Date  date = df.parse(oldDateStr);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //  Date date3 =  df2.parse(date1.toString());
        return df2.format(date1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("jinyangyang+"," onStop DetailsFilmActivity onStop be called");
        if(mediaPlayer!=null){
            Log.d("jinyangyang+","onStop mediaPlayer is "+mediaPlayer);
            commentItemAdapter.stopMediaPlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiverListener();

    }

    private void initBroadcastListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_JIGUANG_LISTCLICK);
        Receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(intent.getAction().equals(ACTION_JIGUANG_LISTCLICK)){
                    abortBroadcast();
                    Log.d("jinyangyang+"," DetailsFilmActivity  ACTION_JIGUANG_LISTCLICK   ============ "+ACTION_JIGUANG_LISTCLICK);
                    Log.d("jinyangyang+","is DetailsFilmActivity ============ "+getClass().getSimpleName());
                    MyAlertDialogUtils.showAlertDialog(mContext, intent);
                }
            }
        };
        registerReceiver(Receiver, intentFilter);
    }

    private void unregisterReceiverListener(){
        if (Receiver!=null){
            unregisterReceiver(Receiver);
            Receiver=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //请求电影详情信息
        presenter.requestDetailFilmData(movieId);
        //查询当前电影评论信息
        pageNum = 0;
        commentBeans.clear();
        presenter.selectComment(movieId, pageNum+"","10");
    }


    private void showShare() {

        OnekeyShare oks = new OnekeyShare();

        //关闭sso授权

        oks.disableSSOWhenAuthorize();



        // title标题，微信、QQ和QQ空间等平台使用

        oks.setTitle(getString(R.string.actor));

        // titleUrl QQ和QQ空间跳转链接

        oks.setTitleUrl("http://sharesdk.cn");

        // text是分享文本，所有平台都需要这个字段

        oks.setText("我是分享文本");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数

        //oks.setImagePath("/sdcard/DCIM/Camera/goMeihuaTemp_mh1456108415396.jpg");//确保SDcard下面存在此张图片


        oks.setImageUrl("https://wanpiao-hk-test.oss-cn-shanghai.aliyuncs.com/1542793463280.jpg");

        // url在微信、微博，Facebook等平台中使用

        oks.setUrl("http://sharesdk.cn");

        // comment是我对这条分享的评论，仅在人人网使用

        oks.setComment("我是测试评论文本");

        // 启动分享GUI

        oks.show(this);

    }



}
