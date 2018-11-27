package com.wanpiao.master.mvp.ui.activity;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityGallerydemoBinding;
import com.wanpiao.master.mvp.contract.GalleryDemoContract;
import com.wanpiao.master.mvp.presenter.GalleryDemoPresenter;
import com.wanpiao.master.mvp.ui.adapter.GalleryDemoRecyclerAdapter;
import com.wanpiao.master.utils.BlurBitmapUtil;
import com.wanpiao.master.utils.gallery.AnimManager;
import com.wanpiao.master.utils.gallery.DLog;
import com.wanpiao.master.utils.gallery.GalleryRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryDemoActivity extends BaseActivity<GalleryDemoPresenter, ActivityGallerydemoBinding> implements GalleryDemoContract.View, GalleryRecyclerView.OnItemClickListener, GalleryDemoRecyclerAdapter.OnItemPhotoChangedListener{
    public static final String TAG = "MainActivity_TAG";

    private GalleryRecyclerView mRecyclerView;
    private RelativeLayout mContainer;
    private SeekBar mSeekbar;

    private Map<String, Drawable> mTSDraCacheMap = new HashMap<>();
    private static final String KEY_PRE_DRAW = "key_pre_draw";

    /**
     * 获取虚化背景的位置
     */
    private int mLastDraPosition = -1;


    @Override
    protected void initViews(Bundle savedInstanceState) {
        DLog.setDebug(true);

        DLog.d(TAG, "MainActivity onCreate()");

        mRecyclerView = findViewById(R.id.rv_list);
        mContainer = findViewById(R.id.rl_container);
        mSeekbar = findViewById(R.id.seekBar);


        final GalleryDemoRecyclerAdapter adapter = new GalleryDemoRecyclerAdapter(this, getDatas());
        adapter.setOnItemPhotoChangedListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView
                // 设置滑动速度（像素/s）
                .initFlingSpeed(9000)
                // 设置页边距和左右图片的可见宽度，单位dp
                .initPageParams(0, 40)
                // 设置切换动画的参数因子
                .setAnimFactor(0.1f)
                // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                // 设置点击事件
                .setOnItemClickListener(this)
                // 设置自动播放
                .autoPlay(false)
                // 设置自动播放间隔时间 ms
                .intervalTime(2000)
                // 设置初始化的位置
                .initPosition(1)
                // 在设置完成之后，必须调用setUp()方法
                .setUp();
        adapter.notifyDataSetChanged();
        // 背景高斯模糊 & 淡入淡出
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    setBlurImage(false);

                    //mSeekbar.setProgress(mRecyclerView.getScrolledPosition());
                }
            }
        });
        setBlurImage(false);

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //mRecyclerView.smoothScrollToPosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gallerydemo;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 设置背景高斯模糊
     */
    public void setBlurImage(boolean forceUpdate) {
        GalleryDemoRecyclerAdapter adapter = (GalleryDemoRecyclerAdapter) mRecyclerView.getAdapter();
        final int mCurViewPosition = mRecyclerView.getScrolledPosition();

        boolean isSamePosAndNotUpdate = (mCurViewPosition == mLastDraPosition) && !forceUpdate;

        if (adapter == null || mRecyclerView == null || isSamePosAndNotUpdate) {
            return;
        }
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                //如果是Fragment的话，需要判断Fragment是否Attach当前Activity，否则getResource会报错
                /*if (!isAdded()) {
                    // fix fragment not attached to Activity
                    return;
                }*/
                // 获取当前位置的图片资源ID
                int resourceId = ((GalleryDemoRecyclerAdapter) mRecyclerView.getAdapter()).getResId(mCurViewPosition);
                // 将该资源图片转为Bitmap
                Bitmap resBmp = BitmapFactory.decodeResource(getResources(), resourceId);
                // 将该Bitmap高斯模糊后返回到resBlurBmp
                Bitmap resBlurBmp = BlurBitmapUtil.blurBitmap(mRecyclerView.getContext(), resBmp, 15f);
                // 再将resBlurBmp转为Drawable
                Drawable resBlurDrawable = new BitmapDrawable(resBlurBmp);
                // 获取前一页的Drawable
                Drawable preBlurDrawable = mTSDraCacheMap.get(KEY_PRE_DRAW) == null ? resBlurDrawable : mTSDraCacheMap.get(KEY_PRE_DRAW);

                /* 以下为淡入淡出效果 */
                Drawable[] drawableArr = {preBlurDrawable, resBlurDrawable};
                TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                mContainer.setBackgroundDrawable(transitionDrawable);
                transitionDrawable.startTransition(500);

                // 存入到cache中
                mTSDraCacheMap.put(KEY_PRE_DRAW, resBlurDrawable);
                // 记录上一次高斯模糊的位置
                mLastDraPosition = mCurViewPosition;
            }
        });
    }


    /***
     * 测试数据
     * @return List<Integer>
     */
    public List<Integer> getDatas() {
        TypedArray ar = getResources().obtainTypedArray(R.array.test_arr);
        final int[] resIds = new int[ar.length()];
        for (int i = 0; i < ar.length(); i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        List<Integer> tDatas = new ArrayList<>();
        for (int resId : resIds) {
            tDatas.add(resId);
        }
        return tDatas;
    }

    @Override
    public void onItemPhotoChanged() {
        setBlurImage(true);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
