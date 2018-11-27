package com.wanpiao.master.widgets.popup_windows;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.wanpiao.master.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/18      onCreate
 */
public class SexPopup extends BasePopupWindow implements View.OnClickListener{

    public static SexPopup newInstance(Context context) {
        SexPopup fragment = new SexPopup(context);
        return fragment;
    }
    private OnClick click;
    TextView woman;
    TextView man;
    TextView tv_cancel;
    private Context context;
    public SexPopup(Context context) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundColor(Color.TRANSPARENT);
        setAllowDismissWhenTouchOutside(true);
        setAllowInterceptTouchEvent(false);
        setAutoLocatePopup(true);
        woman = findViewById(R.id.tv_woman);
        man = findViewById(R.id.tv_man);
        woman.setOnClickListener(this);
        man.setOnClickListener(this);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        this.context = context;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popu_selelct_sex);
    }

    @Override
    public void showPopupWindow(View v) {
//        setOffsetX((int) (-v.getX()));
//        setOffsetY(-getHeight());
        onAnchorBottom(getContentView(), v);
        //setPopupGravity(Gravity.TOP);
        super.showPopupWindow(v);
    }

    @Override
    public Animation getShowAnimation() {
        return super.getShowAnimation();
    }


    public SexPopup setSelelctClick(OnClick click){
        this.click=click;
        return this;
    }

    public interface OnClick{
            void selectSex(String sex);
    }

    @Override
    public void onClick(View v) {
        if (click!=null) {
            switch (v.getId()) {
                case R.id.tv_woman:
                    click.selectSex(woman.getText().toString());
                    dismiss();
                    break;
                case R.id.tv_man:
                    click.selectSex(man.getText().toString());
                    dismiss();
                    break;
                case R.id.tv_cancel:
                    dismiss();
            }
        }
    }
}
