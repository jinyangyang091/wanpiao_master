package com.wanpiao.master.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseFragment;
import com.wanpiao.master.databinding.FragmentMovietabOneBinding;
import com.wanpiao.master.mvp.contract.MovieTabFragmentOneContract;
import com.wanpiao.master.mvp.model.bean.Advertising;
import com.wanpiao.master.mvp.model.bean.MovieItemBean;
import com.wanpiao.master.mvp.presenter.MovieTabFragmentOnePresenter;
import com.wanpiao.master.mvp.ui.BannerLayout;
import com.wanpiao.master.mvp.ui.activity.CurrencyWebActivity;
import com.wanpiao.master.mvp.ui.activity.DetailsFilmActivity;
import com.wanpiao.master.mvp.ui.activity.MainActivity;
import com.wanpiao.master.mvp.ui.adapter.MovieOneTabItemAdapter;
import com.wanpiao.master.mvp.ui.adapter.RecyclerAdapter;
import com.wanpiao.master.utils.BlurBitmapUtil;
import com.wanpiao.master.utils.TimeSwitchUtils;
import com.wanpiao.master.utils.gallery.AnimManager;
import com.wanpiao.master.utils.gallery.GalleryRecyclerView;
import com.wanpiao.master.widgets.views.RatingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;

public class MovieTabFragmentOne extends BaseFragment<MovieTabFragmentOnePresenter, FragmentMovietabOneBinding> implements MovieTabFragmentOneContract.View,SpringView.OnFreshListener, RecyclerAdapter.OnItemPhotoChangedListener{
    private static Map<String, Drawable> mTSDraCacheMap = new HashMap<>();
    /**
     * 获取虚化背景的位置
     */
    private int mLastDraPosition = -1;
    private static final String KEY_PRE_DRAW = "key_pre_draw";
    Boolean enabled = false;
    private  MovieOneTabItemAdapter movieOneTabItemAdapter;
    private  List<MovieItemBean> MovieItemBeans = new ArrayList<>();
    private  List<MovieItemBean> MovieItemBeans2 = new ArrayList<>();
    private RecyclerAdapter adapter;
    private String flag;
    private static GalleryRecyclerView galleryRecyclerView;
    private static RelativeLayout rl_container;
    private int pageNum = 0;
    private int pageSize = 9;
    private View headView;
    private List<Bitmap> allBitmapList = new ArrayList<Bitmap>();
    private Timer timer;
    private int secondIndex = 0;

    @Inject
    public MovieTabFragmentOne(){

    }

    public static MovieTabFragmentOne newInstance() {
        MovieTabFragmentOne fragment = new MovieTabFragmentOne();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        bin.movieOneSpringview.setHeader(new AliHeader(mContext, true));
        //设置上拉加载更多数据
        bin.movieOneSpringview.setFooter(new AliFooter(mContext, true));
        bin.movieOneSpringview.setType(SpringView.Type.FOLLOW);
        bin.movieOneSpringview.setListener(this);
        MyGridLayoutManager myGridLayoutManager = new MyGridLayoutManager(mContext, 3);
//        myGridLayoutManager.setScrollEnabled(false);
        bin.movieOne.setLayoutManager(myGridLayoutManager);
        galleryRecyclerView = (GalleryRecyclerView)view.findViewById(R.id.g_list);
        rl_container = (RelativeLayout)view.findViewById(R.id.rl_container);
        switchCurState();
        setAllAdapter();
    }

    @Override
    protected void initData() {
        pageNum = 0;
        MovieItemBeans.clear();
        MovieItemBeans2.clear();
        presenter.requestTabBanner(mContext);
        presenter.requestMovieTabOneData(mContext, pageNum, pageSize);
        presenter.requestMovieTabOneGalleryData(mContext, pageNum, 100);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_movietab_one;
    }

    public void setAllAdapter(){
        headView = View.inflate(mContext, R.layout.common_banner, null);
        movieOneTabItemAdapter = new MovieOneTabItemAdapter(MovieItemBeans, mContext);
        movieOneTabItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //此处需要传递一个电影ID去详情页进行查询电影详情信息
                Intent intent = new Intent(mContext, DetailsFilmActivity.class);
                Bundle b=new Bundle();
                b.putString("movieId",MovieItemBeans.get(position).getId()+"");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        movieOneTabItemAdapter.addHeaderView(headView);
        //解决RecyclerView加载布局之后卡顿问题
        bin.movieOne.setHasFixedSize(true);
        bin.movieOne.setNestedScrollingEnabled(false);
        bin.movieOne.setAdapter(movieOneTabItemAdapter);
    }

    @Override
    public void showMovieTabOne(String data) {
        try{
            JSONObject jsonObject = new JSONObject(data);
            int messageId = jsonObject.optInt("messageId");
            String message = jsonObject.optString("message");
            Log.d("jinyangyang+","showMovieTabOne InfoData is  ----------  "+data);
            //加载电影即日预售数据
            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            JSONArray jsonArray = jsonObject1.optJSONArray("rows");
            List<MovieItemBean> beans = new ArrayList<>();
            if (jsonArray.length() > 0) {
                bin.movieOneSpringview.onFinishFreshAndLoad();
                Log.d("jinyangyang+","pageNum ============ "+pageNum);
                if(pageNum == 0){
                    MovieItemBeans.clear();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    MovieItemBean bean = new MovieItemBean();
                    bean.setBackground_picture(jsonObject2.optString("mPoster"));
                    bean.setId(jsonObject2.optInt("id"));
                    bean.setName(jsonObject2.optString("mName"));
                    bean.setUserGoodNum(jsonObject2.optString("userGoodNum"));
                    Double dmScore = new Double(jsonObject2.optDouble("mScore"));
                    Float fmScore = dmScore.floatValue();
                    bean.setRate(fmScore);
                    bean.setCommentNums(jsonObject2.optInt("commentNum"));
                    bean.setLaudNums(jsonObject2.optInt("mGoodNum"));
                    MovieItemBeans.add(bean);
                }
                MovieItemBeans.addAll(beans);
                //movieOneTabItemAdapter.notifyDataSetChanged();
                movieOneTabItemAdapter.setNewData(MovieItemBeans);
            }else {
                bin.movieOneSpringview.onFinishFreshAndLoad();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showBannerTabOne(String s) {
        Log.d("jinyangyang+","  showBannerTabOne ============ "+s);
        BannerLayout banner = (BannerLayout)headView.findViewById(R.id.common_banner);
        try{
            JSONObject jsonObject = new JSONObject(s);
            int messageId = jsonObject.optInt("messageId");
            String message = jsonObject.optString("message");
            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            JSONArray jsonArray = jsonObject1.optJSONArray("rows");
            final List<Advertising> advertisings = new ArrayList<>();
            List<String> imgs = new ArrayList<>();
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    Log.d("jinyangyang+", jsonObject3.optString("advertiseUrl"));
                    Advertising advertising = new Advertising();
                    advertising.setImgUrl(jsonObject3.optString("advertiseUrl"));
                    advertising.setTurnUrl(jsonObject3.optString("advertiseJumpUrl"));
                    advertising.setJumpType(jsonObject3.optString("jumpType"));
                    advertising.setJumpId(jsonObject3.optString("jumpId"));
                    advertisings.add(advertising);
                    imgs.add(jsonObject3.optString("advertiseUrl"));
                    if (banner != null) {
                        banner.setViewUrls(imgs, null, null);
                        banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                String jumpType = advertisings.get(position).getJumpType();
                                Log.d("jinyangyang+","jumpType ++++++++++++ "+jumpType);
                                if(jumpType.equals("0")){
                                    Intent intent = new Intent(mContext, DetailsFilmActivity.class);
                                    Bundle b=new Bundle();
                                    b.putString("movieId",advertisings.get(position).getJumpId());
                                    intent.putExtras(b);
                                    startActivity(intent);
                                }else if(jumpType.equals("1")){
                                    CurrencyWebActivity.startCurrencyWeb(mContext, advertisings.get(position).getTurnUrl(),"");
                                }else if(jumpType.equals("2")){
                                    startSystemPlyer(advertisings.get(position).getTurnUrl());
                                }else if(jumpType.equals("3")){
                                    Log.d("jinyangyang+","jumpType ============ "+jumpType);
                                    //此处可以跳转到任意Fragment然后定位到任意顶部Tab
                                    //Intent intent = new Intent(MoviePageFragment.ACTION_ITEMVIEW_LISTCLICK);
                                    //intent.putExtra("position", position);
                                    //mContext.sendBroadcast(intent);
                                    Intent intent2 = new Intent(MainActivity.ACTION_ITEMVIEW_LISTCLICK);
                                    intent2.putExtra("position", position);
                                    mContext.sendBroadcast(intent2);
                                }
                            }
                        });
                    }
                }
            }else{
                banner.setVisibility(View.GONE);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showMovieTabOneGallery(String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);
            int messageId = jsonObject.optInt("messageId");
            String message = jsonObject.optString("message");
            Log.d("jinyangyang+","showMovieTabOneGallery InfoData is  ----------  "+s);
            //加载电影即日预售数据
            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            JSONArray jsonArray = jsonObject1.optJSONArray("rows");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    MovieItemBean bean = new MovieItemBean();
                    bean.setBackground_picture(jsonObject3.optString("mBigPoster"));
                    bean.setId(jsonObject3.optInt("id"));
                    bean.setName(jsonObject3.optString("mName"));
                    Double  dmScore = new Double(jsonObject3.optDouble("mScore"));
                    Float fmScore = dmScore.floatValue();
                    bean.setRate(fmScore);
                    bean.setCommentNums(jsonObject3.optInt("commentNum"));
                    bean.setLaudNums(jsonObject3.optInt("mGoodNum"));
                    String mPlayTime = jsonObject3.optString("mPlayTime");
                    if(!mPlayTime.equals("")){
                        bean.setShowtime(TimeSwitchUtils.dealDateFormat(mPlayTime));
                    }
                    Log.d("commentNum, mGoodNum", ""+bean.getCommentNums()+","+bean.getLaudNums());
                    MovieItemBeans2.add(bean);
                }
                //加载本地Gallery数据
                //RecyclerAdapter adapter = new RecyclerAdapter(mContext, getDatas(), "1");
                adapter = new RecyclerAdapter(MovieItemBeans2, mContext, "1");
                adapter.setOnItemPhotoChangedListener(this);
                bin.gList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                bin.gList.setAdapter(adapter);
                bin.gList
                        // 设置滑动速度（像素/s）
                        .initFlingSpeed(2000)
                        // 设置页边距和左右图片的可见宽度，单位dp
                        .initPageParams(0, 40)
                        // 设置切换动画的参数因子
                        .setAnimFactor(0.1f)
                        // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                        //.setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                        // 设置点击事件
                        .setOnItemClickListener(new GalleryRecyclerView.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int i) {
                                Log.d("jinyangyang+"," onItemClick be called");
                                //Toast.makeText(mContext, "这是第 "+ (i+1)+" 个电影节目", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, DetailsFilmActivity.class);
                                Bundle b=new Bundle();
                                b.putString("movieId",""+MovieItemBeans2.get(i).getId());
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        })
                        // 设置自动播放
                        //.autoPlay(false)
                        // 设置自动播放间隔时间 ms
                        //.intervalTime(2000)
                        // 设置初始化的位置
                        .initPosition(1)
                        // 在设置完成之后，必须调用setUp()方法
                        .setUp();
//                adapter.notifyDataSetChanged();
                //returnAllBitmap2();

                // 背景高斯模糊 & 淡入淡出
                bin.gList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            Log.d("jinyangyang+","addOnScrollListener be called");
                            setBlurImage(false);
                            //bin.mSeekbar.setProgress(bin.gList.getScrolledPosition());
                        }

                        Log.d("jinyangyang+","newState is "+newState);
                    }
                });
                Log.d("jinyangyang+","setBlurImg be called");
                setBlurImage(false);

//                timer = new Timer();
//
//                TimerTask task = new TimerTask(){
//                    @Override
//                    public void run() {
//                        // 背景高斯模糊 & 淡入淡出
//                        bin.gList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                            @Override
//                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                                super.onScrollStateChanged(recyclerView, newState);
//                                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                                    Log.d("jinyangyang+","addOnScrollListener be called");
//                                    setBlurImage(false);
//                                    //bin.mSeekbar.setProgress(bin.gList.getScrolledPosition());
//                                }
//
//                                Log.d("jinyangyang+","newState is "+newState);
//                            }
//                        });
//                        Log.d("jinyangyang+","setBlurImg be called");
//                        setBlurImage(false);
//                        //timer.cancel();
//                        Log.d("jinyangyang+","sssssssssssss");
//                        secondIndex=secondIndex+1;
//                        if (secondIndex == 2){
//                            timer.cancel();
//                        }
//                    }
//                };
//                timer.schedule(task,0,2000);
            }else {

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        MovieItemBeans.clear();
//        MovieItemBeans2.clear();
        presenter.requestTabBanner(mContext);
        presenter.requestMovieTabOneData(mContext, pageNum, pageSize);
//        presenter.requestMovieTabOneGalleryData(mContext, pageNum, 100);
    }

    @Override
    public void onLoadmore() {
        pageNum ++;
        presenter.requestMovieTabOneData(mContext, pageNum, pageSize);
    }

    @Override
    public void onItemPhotoChanged() {
        Log.d("jinyangyang+","onItemPhotoChanged be called");
        setBlurImage(true);
    }

    public class MyGridLayoutManager extends GridLayoutManager {
        private boolean isScrollEnabled = false;
        public MyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) { super(context, attrs, defStyleAttr, defStyleRes); }
        public MyGridLayoutManager(Context context, int spanCount) { super(context, spanCount); }
        public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) { super(context, spanCount, orientation, reverseLayout); }
        public void setScrollEnabled(boolean flag) { this.isScrollEnabled = flag; }
        @Override
        public boolean canScrollVertically() { return true ; }
    }

    //Gallery显示效果布局
    public List<Integer> getDatas() {
        TypedArray ar = getResources().obtainTypedArray(R.array.test_arr);
        final int[] resIds = new int[ar.length()];
        for (int i = 0; i < ar.length(); i++) {
            resIds[i] = ar.getResourceId(i, 0);
            Log.d("jinyangyang+","resIds["+i+"] ======== "+resIds[i]);
        }
        ar.recycle();
        List<Integer> tDatas = new ArrayList<>();
        for (int resId : resIds) {
            Log.d("jinyangyang+","resId ======== "+resId);
            tDatas.add(resId);
        }
        return tDatas;
    }

    /**
     * 设置背景高斯模糊
     */
    public void setBlurImage(boolean forceUpdate) {
        adapter = (RecyclerAdapter) bin.gList.getAdapter();
        //获取当前Item滚动到的位置
        final int mCurViewPosition = bin.gList.getScrolledPosition();

        Log.d("jinyangyang+","  mCurViewPosition  ============ "+mCurViewPosition);

        boolean isSamePosAndNotUpdate = (mCurViewPosition == mLastDraPosition) && !forceUpdate;
        if (adapter == null || bin.gList == null || isSamePosAndNotUpdate) {
            return;
        }

//        bin.gList.post(new Runnable() {
//            @Override
//            public void run() {
//                //if (MovieItemBeans2.get(mCurViewPosition).getName().equals("")||MovieItemBeans2.get(mCurViewPosition).getName() == null){
//                bin.centerTitle.setText(MovieItemBeans2.get(mCurViewPosition).getName());
//                // }
//                bin.commentTextOne.setText("");
//                bin.wantTextOne.setText("");
//                bin.centerText.setText("");
//
//                bin.commentTextOne.setText(""+MovieItemBeans2.get(mCurViewPosition).getCommentNums());
//                bin.wantTextOne.setText(""+MovieItemBeans2.get(mCurViewPosition).getLaudNums());
//                bin.centerText.setText(""+MovieItemBeans2.get(mCurViewPosition).getShowtime());
//                Bitmap bitmap = (Bitmap)allBitmapList.get(mCurViewPosition);
//                Bitmap resBlurBmp = BlurBitmapUtil.blurBitmap(galleryRecyclerView.getContext(), bitmap, 5f);
//                // 再将resBlurBmp转为Drawable
//                Drawable resBlurDrawable = new BitmapDrawable(resBlurBmp);
//                // 获取前一页的Drawable
//                Drawable preBlurDrawable = mTSDraCacheMap.get(KEY_PRE_DRAW) == null ? resBlurDrawable : mTSDraCacheMap.get(KEY_PRE_DRAW);
//                /* 以下为淡入淡出效果 */
//                Drawable[] drawableArr = {preBlurDrawable, resBlurDrawable};
//                TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
//                rl_container.setBackgroundDrawable(transitionDrawable);
//                transitionDrawable.startTransition(200);
//                // 存入到cache中
//                mTSDraCacheMap.put(KEY_PRE_DRAW, resBlurDrawable);
//                // 记录上一次高斯模糊的位置
//                mLastDraPosition = mCurViewPosition;
//            }
//        });
        bin.centerTitle.setText(MovieItemBeans2.get(mCurViewPosition).getName());
        bin.commentTextOne.setText("");
        bin.wantTextOne.setText("");
        bin.centerText.setText("");
        bin.commentTextOne.setText(""+MovieItemBeans2.get(mCurViewPosition).getCommentNums());
        bin.wantTextOne.setText(""+MovieItemBeans2.get(mCurViewPosition).getLaudNums());
        bin.centerText.setText(""+MovieItemBeans2.get(mCurViewPosition).getShowtime());
        bin.commentScore.setText(""+MovieItemBeans2.get(mCurViewPosition).getRate());
        float  dmScore = MovieItemBeans2.get(mCurViewPosition).getRate();
        int mScore = Integer.parseInt(new java.text.DecimalFormat("0").format(dmScore))/2;
        if(mScore>5){
            mScore = 5;
        }
        bin.rbSmall.setSelectedNumber(mScore);
        mLastDraPosition = mCurViewPosition;
        String imageurl = MovieItemBeans2.get(mCurViewPosition).getBackground_picture();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL imageurlx = new URL(imageurl);
                    HttpURLConnection conn = (HttpURLConnection) imageurlx.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    //在主线程里面来控制高斯模糊
                    Message msg = new Message();
                    msg.what = 15;
                    msg.obj = bitmap;
                    myHandler.sendMessage(msg);//用activity中的handler发送消息
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 15:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    Bitmap resBlurBmp = BlurBitmapUtil.blurBitmap(galleryRecyclerView.getContext(), bitmap, 1f);
                    // 再将resBlurBmp转为Drawable
                    Drawable resBlurDrawable = new BitmapDrawable(resBlurBmp);
                    // 获取前一页的Drawable
                    Drawable preBlurDrawable = mTSDraCacheMap.get(KEY_PRE_DRAW) == null ? resBlurDrawable : mTSDraCacheMap.get(KEY_PRE_DRAW);
                    /* 以下为淡入淡出效果 */
                    Drawable[] drawableArr = {preBlurDrawable, resBlurDrawable};
                    TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                    rl_container.setBackgroundDrawable(transitionDrawable);
                    transitionDrawable.startTransition(200);
                    // 存入到cache中
                    mTSDraCacheMap.put(KEY_PRE_DRAW, resBlurDrawable);
                    break;
                case 12:
                    Log.d("jinyangyang+","====================allBitmapList size is "+allBitmapList.size());
                    // 背景高斯模糊 & 淡入淡出
                    bin.gList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                Log.d("jinyangyang+","addOnScrollListener be called");
                                setBlurImage(false);
                                //bin.mSeekbar.setProgress(bin.gList.getScrolledPosition());
                            }

                            Log.d("jinyangyang+","newState is "+newState);
                        }
                    });
                    Log.d("jinyangyang+","setBlurImg be called");
                    setBlurImage(false);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public List<Bitmap> returnAllBitmap2(){
        Log.d("jinyangyang+","MovieItemBeans2 size is "+MovieItemBeans2.size());

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<MovieItemBeans2.size();i++){
                    try{
                        URL imageurlx = new URL(MovieItemBeans2.get(i).getBackground_picture());
                        Log.d("jinyangyang","============sadsadsadsadassadas");
                        HttpURLConnection conn = (HttpURLConnection) imageurlx.openConnection();
                        Log.d("jinyangyang","============conn is "+conn);
                        conn.setDoInput(true);
                        conn.connect();
                        Log.d("jinyangyang","============ conn2 is "+conn);
                        InputStream is = conn.getInputStream();
                        Log.d("jinyangyang","============ IO is "+is);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        Log.d("jinyangyang","============ bitmap is is "+bitmap);
                        is.close();
                        allBitmapList.add(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                //在主线程里面来控制高斯模糊
                Message msg = new Message();
                msg.what = 12;
                msg.obj = allBitmapList;
                myHandler.sendMessage(msg);//用activity中的handler发送消息
            }
        }).start();
        return allBitmapList;
    }


    interface CallBack{
        public void BackCall(int pos);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public  void switchCurState(){
        if(BaseFragment.switchFlag == "1"){
            bin.movieOneSpringview.setVisibility(View.VISIBLE);
            bin.galleryRelativeOne.setVisibility(View.INVISIBLE);
        }else{
            bin.movieOneSpringview.setVisibility(View.INVISIBLE);
            bin.galleryRelativeOne.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("jinyangyang+","MovieTabFragmentOne onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("jinyangyang+","MovieTabFragmentOne onStart");
    }

    //启动系统播放器方法
    public void startSystemPlyer(String url){
        //示例，实际填你的网络视频链接
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);mediaIntent.setDataAndType(Uri.parse(url), mimeType);startActivity(mediaIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
        if (bin.gList != null) {
            // 释放资源
            bin.gList.release();
        }
    }
}
