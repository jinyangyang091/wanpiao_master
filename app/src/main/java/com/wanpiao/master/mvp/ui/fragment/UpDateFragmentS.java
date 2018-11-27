package com.wanpiao.master.mvp.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.mvp.model.bean.MyCommentBean;
import com.wanpiao.master.mvp.ui.activity.MyCommentActivity;
import com.wanpiao.master.mvp.ui.adapter.ActorAdapter2;
import com.wanpiao.master.mvp.ui.adapter.VersionDescAdapter;
import com.wanpiao.master.utils.DownloadManagerUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UpDateFragmentS extends DialogFragment {
    String upDataUrl;
    private static Context context;

    public static UpDateFragmentS newInstance(Context context) {
        UpDateFragmentS fragment = new UpDateFragmentS();
        UpDateFragmentS.context = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_update, null);
        //init(view);
        Button update_btn = (Button)view.findViewById(R.id.update_btn);
        TextView hide_fg = (TextView)view.findViewById(R.id.hide_fg);
        RecyclerView version_desc = (RecyclerView)view.findViewById(R.id.version_desc);


        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        String message = null;
        JSONObject jsonObject = null;


        LinearLayoutManager interviewLinearLayoutManager = new LinearLayoutManager(context);
        interviewLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        version_desc.setLayoutManager(interviewLinearLayoutManager);
        version_desc.addItemDecoration(new UpDateFragmentS.SpacesItemDecoration(2));



        if(bundle!=null){
            message = bundle.getString("updateData");
            Log.d("jinyangyang+","message ++++++++++++ "+message);
            try {
                jsonObject = new JSONObject(message);
                JSONObject data = jsonObject.optJSONObject("data");
                int isUpdate = data.getInt("isUpdate");
                String versionDetails = data.optString("versionDetails");
                Log.d("isUpdate = ", isUpdate+"");
                if(isUpdate == 1){
                    hide_fg.setVisibility(View.GONE);
                }else {
                    hide_fg.setVisibility(View.VISIBLE);
                }
                upDataUrl = data.optString("downloadUrl");
                Log.d("jinyangyang+","upDataUrl ++++++++++++ "+upDataUrl);
                String[] actors = versionDetails.split("&&");
                List<MyCommentBean> myCommentBeans = new ArrayList<>();
                for(int i=0;i<actors.length;i++){
                    MyCommentBean myCommentBean = new MyCommentBean();
                    myCommentBean.setActor(actors[i]);
                    myCommentBeans.add(myCommentBean);
                }
                VersionDescAdapter actorAdapter = new VersionDescAdapter(myCommentBeans, context);
                actorAdapter.setNewData(myCommentBeans);
                version_desc.setAdapter(actorAdapter);
            }catch (JSONException object){
                object.printStackTrace();
            }

        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upDatePackage(upDataUrl);
                dismiss();
            }
        });

        hide_fg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void upDatePackage(String url){
        Log.d("jinyangyang+"," upDatePackage be called url is ++++++++++++ "+url);
        String title = "玩票娱乐香港版";
        String desc = "下载完成后，点击安装";
        DownloadManagerUtil downloadManagerUtil;
        long downloadId = 0;
        downloadManagerUtil = new DownloadManagerUtil(App.getInstance());
        if (downloadId != 0) {
            downloadManagerUtil.clearCurrentTask(downloadId);
        }
        downloadId = downloadManagerUtil.download(url, title, desc);
    }

    class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
        }
    }
}
