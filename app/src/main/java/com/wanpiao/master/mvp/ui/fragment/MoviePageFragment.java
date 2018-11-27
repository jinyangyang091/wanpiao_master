package com.wanpiao.master.mvp.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.tabs.TabLayout;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseFragment;
import com.wanpiao.master.databinding.FragmentMoviePageBinding;
import com.wanpiao.master.mvp.contract.MoviePageFragmentContract;
import com.wanpiao.master.mvp.presenter.MovieFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class MoviePageFragment extends BaseFragment<MovieFragmentPresenter, FragmentMoviePageBinding> implements MoviePageFragmentContract.View{
    private static  Context context;
    private MovieTabFragmentOne movieTabFragmentOne;
    private MovieTabFragmentTwo movieTabFragmentTwo;
    public static String ACTION_ITEMVIEW_LISTCLICK="com.com.wanpiao.master";
    public static String ACTION_SWITCH_LISTCLICK="com.wanpiao.master.movie.switch";
    private BroadcastReceiver Receiver;
    private String[]  mTitle = new String[]{
//            getResources().getString(R.string.tab_one),
//            getResources().getString(R.string.tab_two)
            "即日 / 預售","不日上映"
    };
    private String[] mData = new String[]{
            "这是第一个Fragment","这是第二个Fragment"
    };

    private List<Fragment> mList;
    private FragmentPagerAdapter mAdapter;

    public static MoviePageFragment newInstance(Context context) {
        MoviePageFragment fragment = new MoviePageFragment();
        MoviePageFragment.context = context;
        return fragment;
    }

    @Override
    protected void initView(View view) {
        initBroadcastListener();
        //初始化电影页面假数据
        mList = new ArrayList<>();
        movieTabFragmentOne = new MovieTabFragmentOne();
        movieTabFragmentTwo = new MovieTabFragmentTwo();
        mList.add(movieTabFragmentOne);
        mList.add(movieTabFragmentTwo);
        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }

            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };
        //tablayout_movie.setTabGravity(TabLayout.GRAVITY_CENTER);
        //tab的字体选择器,默认黑色,选择时红色
        bin.tablayoutMovie.setTabTextColors(getResources().getColor(R.color.col_movie_tab_normal), getResources().getColor(R.color.col_movie_tab_selected));
        //tab的下划线颜色,默认是粉红色
        bin.tablayoutMovie.setSelectedTabIndicatorColor(getResources().getColor(R.color.col_movie_tab_selected));
        bin.viewpagerMovie.setAdapter(mAdapter);
        bin.viewpagerMovie.setCurrentItem(0);
        bin.radioGroup.check(R.id.rd_refer_runnning);

        bin.rdReferRunnning.setBackgroundColor(Color.parseColor("#938665"));
        bin.rdReferHistory.setBackgroundColor(Color.parseColor("#2D2D2D"));
        bin.viewpagerMovie.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    bin.radioGroup.check(R.id.rd_refer_runnning);
                    bin.rdReferRunnning.setBackgroundColor(Color.parseColor("#938665"));
                    bin.rdReferHistory.setBackgroundColor(Color.parseColor("#2D2D2D"));
                } else {
                    bin.radioGroup.check(R.id.rd_refer_history);
                    bin.rdReferRunnning.setBackgroundColor(Color.parseColor("#2D2D2D"));
                    bin.rdReferHistory.setBackgroundColor(Color.parseColor("#938665"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bin.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd_refer_runnning:
                        bin.viewpagerMovie.setCurrentItem(0);
                        bin.rdReferRunnning.setBackgroundColor(Color.parseColor("#938665"));
                        bin.rdReferHistory.setBackgroundColor(Color.parseColor("#2D2D2D"));
                        break;
                    case R.id.rd_refer_history:
                        bin.viewpagerMovie.setCurrentItem(1);
                        bin.rdReferRunnning.setBackgroundColor(Color.parseColor("#2D2D2D"));
                        bin.rdReferHistory.setBackgroundColor(Color.parseColor("#938665"));
                        break;
                }
            }
        });

        //将ViewPager关联到TabLayout上
        bin.tablayoutMovie.setupWithViewPager(bin.viewpagerMovie);

//        bin.tablayoutMovie.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        bin.tablayoutMovie.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bin.switchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BaseFragment.switchFlag == "1"){
                    BaseFragment.switchFlag = "2";
                    //send(v);
                    Log.d("jinyangyang+","send send send send send ");
                    movieTabFragmentOne.bin.galleryRelativeOne.setVisibility(View.VISIBLE);
                    movieTabFragmentTwo.bin.galleryRelativeTwo.setVisibility(View.VISIBLE);
                    movieTabFragmentOne.bin.movieOneSpringview.setVisibility(View.GONE);
                    movieTabFragmentTwo.bin.movieTwoSpringview.setVisibility(View.GONE);
                    bin.switchText.setImageResource(R.mipmap.nav_icon_nine);
                }else{
                    BaseFragment.switchFlag = "1";
                    movieTabFragmentOne.bin.galleryRelativeOne.setVisibility(View.GONE);
                    movieTabFragmentTwo.bin.galleryRelativeTwo.setVisibility(View.GONE);
                    movieTabFragmentOne.bin.movieOneSpringview.setVisibility(View.VISIBLE);
                    movieTabFragmentTwo.bin.movieTwoSpringview.setVisibility(View.VISIBLE);
                    bin.switchText.setImageResource(R.mipmap.nav_icon_one);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_movie_page;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initBroadcastListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ITEMVIEW_LISTCLICK);
        intentFilter.addAction(ACTION_SWITCH_LISTCLICK);
        Receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(intent.getAction().equals(ACTION_ITEMVIEW_LISTCLICK)) {
                    Log.v("jinyangyang++", ACTION_ITEMVIEW_LISTCLICK + "," + intent.getIntExtra("position", -1));
                    //这里可以刷新局部数据，可以接受另外的一个fragment发过来的数据
                    bin.radioGroup.check(R.id.rd_refer_history);
                }else if(intent.getAction().equals(ACTION_SWITCH_LISTCLICK)){
                    Log.d("jinyangyang+","  ACTION_SWITCH_LISTCLICK ============ "+ACTION_SWITCH_LISTCLICK);

                    if(BaseFragment.switchFlag == "2"){
                        BaseFragment.switchFlag = "1";
                        //send(v);
                        Log.d("jinyangyang+","send send send send send ");
                        movieTabFragmentOne.bin.galleryRelativeOne.setVisibility(View.GONE);
                        movieTabFragmentTwo.bin.galleryRelativeTwo.setVisibility(View.GONE);
                        movieTabFragmentOne.bin.movieOneSpringview.setVisibility(View.VISIBLE);
                        movieTabFragmentTwo.bin.movieTwoSpringview.setVisibility(View.VISIBLE);
                        bin.switchText.setImageResource(R.mipmap.nav_icon_one);
                    }
                }
            }
        };
        mContext.registerReceiver(Receiver ,intentFilter);
    }

    private void unregisterReceiverListener(){
        if (Receiver!=null){
            mContext.unregisterReceiver(Receiver);
            Receiver=null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiverListener();
    }
}
