package com.wanpiao.master.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
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
import com.wanpiao.master.databinding.MyCommentBinding;
import com.wanpiao.master.mvp.contract.MyCommentContract;
import com.wanpiao.master.mvp.model.bean.MyCommentBean;
import com.wanpiao.master.mvp.presenter.MyCommentPresenter;
import com.wanpiao.master.mvp.ui.adapter.MyCommentAdapter;
import com.wanpiao.master.mvp.ui.adapter.MyInfoAdapter;
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

public class MyCommentActivity extends BaseActivity<MyCommentPresenter, MyCommentBinding> implements MyCommentContract.View ,SpringView.OnFreshListener {
    private String jsonString2 = "[{\"id\":525339,\"name\":\"无双\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i1/TB11.34cVzqK1RjSZSgXXcpAVXa_.jpg_300x300.jpg\",\"saleType\":1,\"actor\":\"周润发,郭富城,张静初,冯文娟,廖启智,周家怡,王耀庆,方中信\",\"director\":\"麦兆辉, 庄文强\",\"description\":\"《无双》讲述了以代号“画家”（周润发饰）为首的犯罪团伙，掌握了制造伪钞技术，难辨真伪，并在全球进行交易获取利益，引起警方高度重视。然而“画家”和其他成员的身份一直成谜，警方的破案进度遭受到了前所未有的挑战。在关键时刻，擅长绘画的李问（郭富城饰）打开了破案的突破口，而“画家”的真实身份却让众人意想不到……\",\"score\":8.9,\"show_mark\":\"2D IMAX\",\"open_time\":\"2018-09-30\",\"background_picture\":\"i3/TB1DQe7tbZnBKNjSZFGXXbt3FXa_.jpg\",\"like_num\":4,\"length\":0,\"poster\":\"i1/TB11.34cVzqK1RjSZSgXXcpAVXa_.jpg\"},{\"id\":519073,\"name\":\"宝贝儿\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i4/TB1r94_pHorBKNjSZFjXXc_SpXa_.jpg_300x300.jpg\",\"saleType\":2,\"actor\":\"杨幂,郭京飞,李鸿其\",\"director\":\"刘杰\",\"description\":\"《宝贝儿》是由侯孝贤监制、刘杰执导的纪实风格文艺片，讲述的是一个因为严重先天缺陷而被父母抛弃的弃儿江萌（杨幂 饰），拯救另一个被父母宣判了“死刑”的缺陷婴儿的故事。\",\"score\":0.0,\"open_time\":\"2018-10-19\",\"like_num\":1,\"length\":0,\"poster\":\"i4/TB1r94_pHorBKNjSZFjXXc_SpXa_.jpg\"},{\"id\":333986,\"name\":\"我的间谍前男友\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i2/TB15JGmwVooBKNjSZPhXXc2CXXa_.jpg_300x300.jpg\",\"saleType\":2,\"actor\":\"米拉·库妮丝,凯特·麦克金农,贾斯汀·塞洛克斯,山姆·修汉\",\"director\":\"苏珊娜·福格尔\",\"description\":\"奥黛丽（米拉·库妮丝 饰）和摩根（凯特·迈克金农 饰）两位好闺蜜，因奥黛丽前男友的间谍身份，意外被卷入一场国际阴谋，在欧洲大陆上演惊心动魄又啼笑皆非的国际逃亡之旅。\",\"score\":0.0,\"show_mark\":\"2D 中国巨幕\",\"open_time\":\"2018-10-19\",\"like_num\":0,\"length\":0,\"poster\":\"i2/TB15JGmwVooBKNjSZPhXXc2CXXa_.jpg\"},{\"id\":214157,\"name\":\"影\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i1/TB1a0iOX3TqK1RjSZPhXXXfOFXa_.jpg_300x300.jpg\",\"saleType\":1,\"actor\":\"邓超,孙俪,郑恺,王千源,王景春,胡军,关晓彤,吴磊\",\"director\":\"张艺谋\",\"description\":\"这是一个关于替身的故事。替身自古有之，人称“影子”。有刺杀，就有影子，影子必须在危急关头挺身而出，替主人博回一命；影子又必须与真身互为一体，令旁人真假难辨如同孪生。若无真身，影子何在？关于影子的来龙去脉，真身从来忌讳莫深，不愿提及而令真相扑朔迷离。是愿做真身，还是愿做影子？是明处做人，还是暗处做鬼，换作是你，如何选择？\",\"score\":8.3,\"show_mark\":\"2D IMAX\",\"open_time\":\"2018-09-30\",\"background_picture\":\"i2/TB1s1XRXrvpK1RjSZFqXXcXUVXa_.jpg\",\"like_num\":1,\"length\":0,\"poster\":\"i1/TB1a0iOX3TqK1RjSZPhXXXfOFXa_.jpg\"}]";
    private List<MyCommentBean> myCommentBeans = new ArrayList<>();
    private MyCommentAdapter myCommentAdapter;
    private int total = 0;
    private boolean isLastPage;
    private int totalPage = 0;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    public static void startMyCommentActivity(Context context) {
        Intent intent = new Intent(context, MyCommentActivity.class);
        context.startActivity(intent);
    }
    private int pageNum=0;
    private int pageSize=10;
    @Override
    public void initToolBar() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        bin.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bin.svHome.setHeader(new AliHeader(mContext, true));
        //设置上拉加载更多数据
        bin.svHome.setFooter(new AliFooter(mContext, true));
        bin.svHome.setType(SpringView.Type.FOLLOW);
        bin.svHome.setListener(this);

        LinearLayoutManager interviewLinearLayoutManager = new LinearLayoutManager(this);
        interviewLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bin.myCommentRecyclerview.setLayoutManager(interviewLinearLayoutManager);
        bin.myCommentRecyclerview.addItemDecoration(new MyCommentActivity.SpacesItemDecoration(30));
        //设置该属性让recyclerview滑动没有黏连的感觉
        bin.myCommentRecyclerview.setNestedScrollingEnabled(false);
        myCommentAdapter = new MyCommentAdapter(myCommentBeans, MyCommentActivity.this, mediaPlayer);
        bin.myCommentRecyclerview.setAdapter(myCommentAdapter);
    }

    @Override
    protected void initData() {
        //initInformation();
        pageNum = 0;
        presenter.selectMyComment(SPUtils.get(App.getInstance(), "id",""),pageNum, pageSize );
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_comment;
    }

    public void initInformation(){
        //加载专访网络本地数据
        try {
            JSONArray jsonArray2 = new JSONArray(jsonString2);
            if (jsonArray2.length() > 0) {
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsonObject = jsonArray2.getJSONObject(i);
                    MyCommentBean myCommentBean = new MyCommentBean();
                    myCommentBean.setBackground_picture(jsonObject.optString("bannerVsmallUrl"));
                    myCommentBean.setId(jsonObject.optInt("id"));
                    myCommentBean.setName(jsonObject.optString("name"));

                    if(i%4 == 0){
                        myCommentBean.setActor("古天樂,楊冪,謝霆鋒,周潤發");
                        myCommentBean.setCommentDesc("黃渤這壹次真的拼了，用壹群人荒島求生的故事外殼好好地搞了壹出人性實驗，黑色，荒誕，瘋狂，從生存的法則.部落的建立，文明的誕生，貨幣的誕生講到政權的形成以及烏托邦社會的破滅，而在其中穿插著壹個男人猶如摩西壹般的成長歷程，整個劇本的野心極大，不論是宏觀上還是微觀上都言之有物，連張藝興都會演戲了！");
                    }else if(i%4 == 1){
                        myCommentBean.setActor("古天樂,謝霆鋒,周潤發");
                        myCommentBean.setCommentDesc("黃渤這壹次真的拼了，用壹群人荒島求生的故事外殼好好地搞了壹出人性實驗，黑色，荒誕，瘋狂，從生存的法則.部落的建立，文明的誕生，貨幣的誕生講到政權的形成以及烏托邦社會的破滅，而在其中穿插著壹個男人猶如摩西壹般的成長歷程，整個劇本的野心極大，不論是宏觀上還是微觀上都言之有物，連張藝興都會演戲了！黃渤這壹次真的拼了，用壹群人荒島求生的故事外殼好好地搞了壹出人性實驗，黑色，荒誕，瘋狂，從生存的法則.部落的建立，文明的誕生，貨幣的誕生講到政權的形成以及烏托邦社會的破滅，而在其中穿插著壹個男人猶如摩西壹般的成長歷程，整個劇本的野心極大，不論是宏觀上還是微觀上都言之有物，連張藝興都會演戲了！");
                    }else if(i%4 == 2){
                        myCommentBean.setActor("楊冪,謝霆鋒");
                        myCommentBean.setCommentDesc("黃渤這壹次真的拼了，用壹群人荒島求生的故事外殼好好地搞了壹出人性實驗，黑色，荒誕，瘋狂，從生存的法則.部落的建立，文明的誕生，貨幣的誕生講到政權的形成以及烏托邦社會的破滅，而在其中穿插著壹個男人猶如摩西壹般的成長歷程，整個劇本的野心極大，不論是宏觀上還是微觀上都言之有物，連張藝興都會演戲了！黃渤這壹次真的拼了，用壹群人荒島求生的故事外殼好好地搞了壹出人性實驗，黑色，荒誕，瘋狂，從生存的法則.部落的建立，文明的誕生，貨幣的誕生講到政權的形成以及烏托邦社會的破滅，而在其中穿插著壹個男人猶如摩西壹般的成長歷程，整個劇本的野心極大，不論是宏觀上還是微觀上都言之有物，連張藝興都會演戲了！黃渤這壹次真的拼了，用壹群人荒島求生的故事外殼好好地搞了壹出人性實驗，黑色，荒誕，瘋狂，從生存的法則.部落的建立，文明的誕生，貨幣的誕生講到政權的形成以及烏托邦社會的破滅，而在其中穿插著壹個男人猶如摩西壹般的成長歷程，整個劇本的野心極大，不論是宏觀上還是微觀上都言之有物，連張藝興都會演戲了！");
                    }else if(i%4 == 3){
                        myCommentBean.setActor("謝霆鋒");
                        myCommentBean.setCommentDesc("黃渤這壹次真的拼了，用壹群人荒島求生的故事外殼好好地搞了壹出人性實驗，黑色，荒誕，瘋狂，從生存的法則.部落的建立");
                    }
                    myCommentBeans.add(myCommentBean);
                }
                myCommentAdapter.setNewData(myCommentBeans);
            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMyCommentList(String response) {
        try {
            JSONObject data = new JSONObject(response).optJSONObject("data");
            total = data.optInt("total");
            totalPage = total/10+1;
            Log.d("jinyangyang+","  totalPage  ======== "+totalPage);
            isLastPage = data.optBoolean("isLastPage");
            JSONArray list = data.optJSONArray("list");
            if(list.length()>0){
                bin.svHome.onFinishFreshAndLoad();
                bin.noResultImg.setVisibility(View.GONE);
                bin.noResultLable.setVisibility(View.GONE);
                bin.myCommentRecyclerview.setVisibility(View.VISIBLE);
                for (int i = 0; i < list.length(); i++) {
                    JSONObject item = list.getJSONObject(i);
                    MyCommentBean myCommentBean = new MyCommentBean();
                    myCommentBean.setBackground_picture(item.optString("mPoster"));
                    myCommentBean.setId(item.optInt("id"));
                    myCommentBean.setName(item.optString("mName"));
                    myCommentBean.setActor(item.optString("mType"));
                    myCommentBean.setCommentDesc(item.optString("comment"));
                    myCommentBean.setDirector(item.optString("mDirector"));
                    myCommentBean.setmScore(item.optDouble("mScore"));
                    String mPlayTime = item.optString("mPlayTime");
                    if(!mPlayTime.equals("")){
                        myCommentBean.setShowtime(TimeSwitchUtils.dealDateFormat(mPlayTime));
                    }
                    myCommentBean.setVoiceUrl(item.optString("commentVoice"));
                    myCommentBean.setVoiceLength(item.optDouble("voiceLength"));
                    myCommentBeans.add(myCommentBean);
                }
                myCommentAdapter.setNewData(myCommentBeans);
                myCommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(mContext, DetailsFilmActivity.class);
                        Bundle b=new Bundle();
                        b.putString("movieId",myCommentBeans.get(position).getId()+"");
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
            }else{
                bin.svHome.onFinishFreshAndLoad();
                bin.noResultImg.setVisibility(View.VISIBLE);
                bin.noResultLable.setVisibility(View.VISIBLE);
                bin.myCommentRecyclerview.setVisibility(View.GONE);
                ToastUtil.show(App.getInstance(), "没有更多数据了");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        myCommentBeans.clear();
        presenter.selectMyComment(SPUtils.get(App.getInstance(), "id",""),pageNum, pageSize );
    }

    @Override
    public void onLoadmore() {
        pageNum++;
        if(pageNum < totalPage){
            presenter.selectMyComment(SPUtils.get(App.getInstance(), "id",""),pageNum, pageSize );
        }else {
            bin.svHome.onFinishFreshAndLoad();
            ToastUtil.show(App.getInstance(), "您好，没有更多数据了");
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
            outRect.bottom = space;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer!=null){
            myCommentAdapter.stopMediaPlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
