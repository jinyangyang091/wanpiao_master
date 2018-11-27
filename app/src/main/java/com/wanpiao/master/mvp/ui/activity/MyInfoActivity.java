package com.wanpiao.master.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.MyInformationBinding;
import com.wanpiao.master.mvp.contract.MyInfoContract;
import com.wanpiao.master.mvp.model.bean.InformationBean2;
import com.wanpiao.master.mvp.model.bean.MyInfoBean;
import com.wanpiao.master.mvp.presenter.MyInfoPresenter;
import com.wanpiao.master.mvp.presenter.SplashPresenter;
import com.wanpiao.master.mvp.ui.adapter.MyInfoAdapter;
import com.wanpiao.master.mvp.ui.fragment.HomePageFragment;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.TimeSwitchUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyInfoActivity extends BaseActivity<MyInfoPresenter, MyInformationBinding> implements MyInfoContract.View ,SpringView.OnFreshListener{
    private String jsonString2 = "[{\"id\":525339,\"name\":\"无双\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i1/TB11.34cVzqK1RjSZSgXXcpAVXa_.jpg_300x300.jpg\",\"saleType\":1,\"actor\":\"周润发,郭富城,张静初,冯文娟,廖启智,周家怡,王耀庆,方中信\",\"director\":\"麦兆辉, 庄文强\",\"description\":\"《无双》讲述了以代号“画家”（周润发饰）为首的犯罪团伙，掌握了制造伪钞技术，难辨真伪，并在全球进行交易获取利益，引起警方高度重视。然而“画家”和其他成员的身份一直成谜，警方的破案进度遭受到了前所未有的挑战。在关键时刻，擅长绘画的李问（郭富城饰）打开了破案的突破口，而“画家”的真实身份却让众人意想不到……\",\"score\":8.9,\"show_mark\":\"2D IMAX\",\"open_time\":\"2018-09-30\",\"background_picture\":\"i3/TB1DQe7tbZnBKNjSZFGXXbt3FXa_.jpg\",\"like_num\":4,\"length\":0,\"poster\":\"i1/TB11.34cVzqK1RjSZSgXXcpAVXa_.jpg\"},{\"id\":519073,\"name\":\"宝贝儿\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i4/TB1r94_pHorBKNjSZFjXXc_SpXa_.jpg_300x300.jpg\",\"saleType\":2,\"actor\":\"杨幂,郭京飞,李鸿其\",\"director\":\"刘杰\",\"description\":\"《宝贝儿》是由侯孝贤监制、刘杰执导的纪实风格文艺片，讲述的是一个因为严重先天缺陷而被父母抛弃的弃儿江萌（杨幂 饰），拯救另一个被父母宣判了“死刑”的缺陷婴儿的故事。\",\"score\":0.0,\"open_time\":\"2018-10-19\",\"like_num\":1,\"length\":0,\"poster\":\"i4/TB1r94_pHorBKNjSZFjXXc_SpXa_.jpg\"},{\"id\":333986,\"name\":\"我的间谍前男友\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i2/TB15JGmwVooBKNjSZPhXXc2CXXa_.jpg_300x300.jpg\",\"saleType\":2,\"actor\":\"米拉·库妮丝,凯特·麦克金农,贾斯汀·塞洛克斯,山姆·修汉\",\"director\":\"苏珊娜·福格尔\",\"description\":\"奥黛丽（米拉·库妮丝 饰）和摩根（凯特·迈克金农 饰）两位好闺蜜，因奥黛丽前男友的间谍身份，意外被卷入一场国际阴谋，在欧洲大陆上演惊心动魄又啼笑皆非的国际逃亡之旅。\",\"score\":0.0,\"show_mark\":\"2D 中国巨幕\",\"open_time\":\"2018-10-19\",\"like_num\":0,\"length\":0,\"poster\":\"i2/TB15JGmwVooBKNjSZPhXXc2CXXa_.jpg\"},{\"id\":214157,\"name\":\"影\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i1/TB1a0iOX3TqK1RjSZPhXXXfOFXa_.jpg_300x300.jpg\",\"saleType\":1,\"actor\":\"邓超,孙俪,郑恺,王千源,王景春,胡军,关晓彤,吴磊\",\"director\":\"张艺谋\",\"description\":\"这是一个关于替身的故事。替身自古有之，人称“影子”。有刺杀，就有影子，影子必须在危急关头挺身而出，替主人博回一命；影子又必须与真身互为一体，令旁人真假难辨如同孪生。若无真身，影子何在？关于影子的来龙去脉，真身从来忌讳莫深，不愿提及而令真相扑朔迷离。是愿做真身，还是愿做影子？是明处做人，还是暗处做鬼，换作是你，如何选择？\",\"score\":8.3,\"show_mark\":\"2D IMAX\",\"open_time\":\"2018-09-30\",\"background_picture\":\"i2/TB1s1XRXrvpK1RjSZFqXXcXUVXa_.jpg\",\"like_num\":1,\"length\":0,\"poster\":\"i1/TB1a0iOX3TqK1RjSZPhXXXfOFXa_.jpg\"}]";
    private List<MyInfoBean> myInfoBeans = new ArrayList<>();
    private MyInfoAdapter myInfoAdapter;
    private int pageNum = 0;
    private boolean isLastPage;
    public static void startMyInfoActivity(Context context) {
        Intent intent = new Intent(context, MyInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        pageNum = 0;
        presenter.selectMyInfo(SPUtils.get(App.getInstance(), "id",""), pageNum, 10);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        bin.svHome.setHeader(new AliHeader(mContext, true));
        //设置上拉加载更多数据
        bin.svHome.setFooter(new AliFooter(mContext, true));
        bin.svHome.setType(SpringView.Type.FOLLOW);
        bin.svHome.setListener(this);
        bin.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager interviewLinearLayoutManager = new LinearLayoutManager(this);
        interviewLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bin.myInfoRecyclerview.setLayoutManager(interviewLinearLayoutManager);
        bin.myInfoRecyclerview.addItemDecoration(new MyInfoActivity.SpacesItemDecoration(50));
        //设置该属性让recyclerview滑动没有黏连的感觉
        bin.myInfoRecyclerview.setNestedScrollingEnabled(false);
        myInfoAdapter = new MyInfoAdapter(myInfoBeans, MyInfoActivity.this);
        bin.myInfoRecyclerview.setAdapter(myInfoAdapter);
        myInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, DetailsFilmsActivity.class);
                Bundle b=new Bundle();
                b.putString("movieId",myInfoBeans.get(position).getId()+"");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.my_information;
    }

    public void initInformationLocal(String response){
        //加载专访网络本地数据
        try {
            JSONArray jsonArray2 = new JSONArray(jsonString2);
            if (jsonArray2.length() > 0) {
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsonObject = jsonArray2.getJSONObject(i);
                    MyInfoBean myInfoBean = new MyInfoBean();
                    myInfoBean.setBackground_picture(jsonObject.optString("bannerVsmallUrl"));
                    myInfoBean.setId(jsonObject.optInt("id"));
                    myInfoBean.setName(jsonObject.optString("name"));
                    myInfoBean.setScore(jsonObject.optDouble("mScore"));
                    String mPlayTime = jsonObject.optString("mPlayTime");
                    Log.d("mPlayTime is", mPlayTime);
                    if(!mPlayTime.equals("")){
                        myInfoBean.setShowtime(TimeSwitchUtils.dealDateFormat(mPlayTime));
                    }
                    if(i%4 == 0){
                        myInfoBean.setActor("古天樂,楊冪,謝霆鋒,周潤發");
                    }else if(i%4 == 1){
                        myInfoBean.setActor("古天樂,謝霆鋒,周潤發");
                    }else if(i%4 == 2){
                        myInfoBean.setActor("楊冪,謝霆鋒");
                    }else if(i%4 == 3){
                        myInfoBean.setActor("謝霆鋒");
                    }
                    myInfoBeans.add(myInfoBean);
                }
                myInfoAdapter.setNewData(myInfoBeans);
            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMyInfoList(String response) {
        initInformation(response);
    }

    public void initInformation(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.optJSONObject("data");
            isLastPage = data.optBoolean("isLastPage");
            JSONArray rows = data.optJSONArray("rows");
            if(rows.length()>0){
                bin.svHome.onFinishFreshAndLoad();
                for(int i=0;i<=rows.length()-1;i++){
                    JSONObject item = rows.getJSONObject(i);
                    MyInfoBean myInfoBean = new MyInfoBean();
                    myInfoBean.setBackground_picture(item.optString("mPoster"));
                    myInfoBean.setId(item.optInt("id"));
                    myInfoBean.setName(item.optString("mName"));
                    myInfoBean.setMovieType(item.optString("mType"));
                    myInfoBean.setStatus(item.optInt("status"));
                    String mPlayTime = item.optString("mPlayTime");
                    if(!mPlayTime.equals("")){
                        myInfoBean.setShowtime(TimeSwitchUtils.dealDateFormat(mPlayTime));
                    }
                    myInfoBeans.add(myInfoBean);
                }
                myInfoAdapter.setNewData(myInfoBeans);
                bin.noResultImg.setVisibility(View.GONE);
                bin.noResultLable.setVisibility(View.GONE);
                bin.myInfoRecyclerview.setVisibility(View.VISIBLE);
            }else {
                bin.svHome.onFinishFreshAndLoad();
                bin.noResultImg.setVisibility(View.VISIBLE);
                bin.noResultLable.setVisibility(View.VISIBLE);
                bin.myInfoRecyclerview.setVisibility(View.GONE);
                ToastUtil.show(App.getInstance(), "没有更多数据了");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        pageNum=0;
        myInfoBeans.clear();
        presenter.selectMyInfo(SPUtils.get(App.getInstance(), "id",""), pageNum, 10);
    }

    @Override
    public void onLoadmore() {
        if(!isLastPage){
            bin.svHome.onFinishFreshAndLoad();
            ToastUtil.show(App.getInstance(), "没有更多数据了");
        }else {
            pageNum++;
            presenter.selectMyInfo(SPUtils.get(App.getInstance(), "id",""), pageNum, 10);
        }
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
