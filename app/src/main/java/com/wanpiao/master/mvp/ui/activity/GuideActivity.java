package com.wanpiao.master.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityGuideBinding;
import com.wanpiao.master.mvp.contract.GuideContract;
import com.wanpiao.master.mvp.model.bean.GuideBean;
import com.wanpiao.master.mvp.presenter.GuidePresenter;
import com.wanpiao.master.mvp.ui.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

public class GuideActivity extends BaseActivity<GuidePresenter, ActivityGuideBinding> implements GuideContract.View {
    public static void startGuideActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }
    private List<GuideBean> guideBeans = new ArrayList<>();
    private GuideAdapter guideAdapter;
    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {
        int[] guideImg = {
                R.drawable.guide_imag_1,
                R.drawable.guide_imag_2,
                R.drawable.guide_imag_3,
        };
        for(int i=0;i<guideImg.length;i++){
            GuideBean guideBean = new GuideBean();
            guideBean.setLocalImg(guideImg[i]);
            if(i==guideImg.length-1){
                guideBean.setItemType(1);
            }else {
                guideBean.setItemType(0);
            }
            guideBeans.add(guideBean);
        }
        guideAdapter = new GuideAdapter(mContext, guideBeans);
        guideAdapter.bindActivity(this);
        bin.guideViewPager.setAdapter(guideAdapter);
        guideAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            return isCosumenBackKey();
        }
        return false;
    }

    private boolean isCosumenBackKey() {
        // 这儿做返回键的控制，如果自己处理返回键逻辑就返回true，如果返回false,代表继续向下传递back事件，由系统去控制
        return true;
    }
}
