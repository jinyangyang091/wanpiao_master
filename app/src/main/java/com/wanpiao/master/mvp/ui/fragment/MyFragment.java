package com.wanpiao.master.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseFragment;
import com.wanpiao.master.databinding.FragmentMyPageBinding;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.contract.MyFragmentContract;
import com.wanpiao.master.mvp.presenter.MyFragmentPresenter;
import com.wanpiao.master.mvp.ui.activity.AliyunVoiceActivity;
import com.wanpiao.master.mvp.ui.activity.GalleryDemoActivity;
import com.wanpiao.master.mvp.ui.activity.LoginActivity;
import com.wanpiao.master.mvp.ui.activity.MyCommentActivity;
import com.wanpiao.master.mvp.ui.activity.MyInfoActivity;
import com.wanpiao.master.mvp.ui.activity.SettingActivity;
import com.wanpiao.master.mvp.ui.activity.SoundRecordActivity;
import com.wanpiao.master.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFragment extends BaseFragment<MyFragmentPresenter, FragmentMyPageBinding> implements MyFragmentContract.View {
    private RelativeLayout myInform,myComments,mySet;
    private static Context context;
    public static MyFragment newInstance(Context context) {
        MyFragment fragment = new MyFragment();
        MyFragment.context = context;
        return fragment;
    }

    @Override
    protected void initData() {
        String userAccount = SPUtils.get(App.getInstance(), "userAccount","");
        String userPassword = SPUtils.get(App.getInstance(), "userPassword", "");
        presenter.requestLanding(context, userAccount, userPassword);
    }

    @Override
    protected void initView(View view) {
        myInform = (RelativeLayout)view.findViewById(R.id.myInform);
        myComments = (RelativeLayout)view.findViewById(R.id.myComments);
        mySet = (RelativeLayout)view.findViewById(R.id.mySet);

        myInform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyInfoActivity.startMyInfoActivity(context);
            }
        });

        myComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCommentActivity.startMyCommentActivity(context);
                Log.d("jinyangyang+","跳转到我的评论！");
            }
        });

        mySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.startSettingActivity(context);

            }
        });

        bin.aliyunVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AliyunVoiceActivity.startAliyunVoiceActivity(context);
                startActivity(new Intent(mContext, GalleryDemoActivity.class));
            }
        });

        bin.userHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginActivity.startLoginActivity(mContext);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_page;
    }


    @Override
    public void onResume() {
        super.onResume();
        String userPortrait = SPUtils.get(App.getInstance(), "userPortrait", "");
        if(!userPortrait.equals("")){
            ImageView imageView = bin.userHeadImg;
            User.showIcon(imageView, userPortrait);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String userPortrait = SPUtils.get(App.getInstance(), "userPortrait", "");
        String userName = SPUtils.get(App.getInstance(), "userName", "");
        if(!userPortrait.equals("")){
            ImageView imageView = bin.userHeadImg;
            User.showIcon(imageView, userPortrait);
        }
        if(!userName.equals("")){
            bin.userName.setText(userName);
        }
    }

    @Override
    public void landingSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            String userName = jsonObject1.optString("userName");
            String userPortrait = jsonObject1.optString("userPortrait");
            if(!userPortrait.equals("")){
                User.showIcon(bin.userHeadImg, userPortrait);
            }
            if(!userName.equals("")){
                bin.userName.setText(userName);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
