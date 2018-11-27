package com.wanpiao.master.widgets;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.wanpiao.master.R;
import com.wanpiao.master.utils.DensityUtil;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class LoadingDialog extends Dialog {
    private AVLoadingIndicatorView loadingIndicatorView;
    private Context context;
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null));
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = DensityUtil.dp2px(context, 100.0f);
        //宽高可设置具体大小
        lp.height = DensityUtil.dp2px(context, 100.0f);
        getWindow().setAttributes(lp);
        loadingIndicatorView = findViewById(R.id.av_loading_view);
        setCancelable(false);
    }

    @Override
    public void show() {
        if (loadingIndicatorView != null) {
            loadingIndicatorView.smoothToShow();
        }
        super.show();
    }

    @Override
    public void dismiss() {
        if (loadingIndicatorView != null) {
            loadingIndicatorView.smoothToShow();
        }
        super.dismiss();
    }

}
