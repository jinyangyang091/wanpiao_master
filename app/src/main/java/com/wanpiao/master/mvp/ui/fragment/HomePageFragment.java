package com.wanpiao.master.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseFragment;
import com.wanpiao.master.databinding.FragmentHomePageBinding;
import com.wanpiao.master.mvp.contract.HomePageFragmentContract;
import com.wanpiao.master.mvp.model.bean.Advertising;
import com.wanpiao.master.mvp.model.bean.InformationBean;
import com.wanpiao.master.mvp.model.bean.InformationBean2;
import com.wanpiao.master.mvp.presenter.HomePageFragmentPresenter;
import com.wanpiao.master.mvp.ui.BannerLayout;
import com.wanpiao.master.mvp.ui.activity.CurrencyWebActivity;
import com.wanpiao.master.mvp.ui.activity.DetailsFilmActivity;
import com.wanpiao.master.mvp.ui.activity.MainActivity;
import com.wanpiao.master.mvp.ui.activity.VideoPlayerActivity;
import com.wanpiao.master.mvp.ui.adapter.InterviewMovieAdpter2;
import com.wanpiao.master.mvp.ui.adapter.PrevueMovieAdapter;
import com.wanpiao.master.mvp.ui.adapter.ViewpagerAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomePageFragment extends BaseFragment<HomePageFragmentPresenter, FragmentHomePageBinding>
        implements HomePageFragmentContract.View, SpringView.OnFreshListener {
    private String jsonString = "[{\"id\":24,\"image_back\":\"https://enjoytickets.oss-cn-beijing.aliyuncs.com/advingImg/CJRAG7BTXy.jpg\",\"url\":\"http://enjoytickets.sit.cinemapos.com/test.html\",\"title\":\"广告1\",\"start_time\":\"2018-09-14 17:02:56\",\"end_time\":\"2024-12-31 17:02:56\",\"system_type\":\"W\",\"is_show\":\"Y\",\"hot\":5,\"page\":\"index\",\"go_type\":\"h5\",\"go_id\":\"\"},{\"id\":13,\"image_back\":\"https://enjoytickets.oss-cn-beijing.aliyuncs.com/advingImg/DcByR2SesY.jpg\",\"url\":\"\",\"title\":\"建国大业\",\"start_time\":\"2018-09-06 14:54:36\",\"end_time\":\"2019-01-31 14:54:36\",\"system_type\":\"W\",\"is_show\":\"Y\",\"hot\":2,\"page\":\"index\",\"go_type\":\"movie\",\"go_id\":\"359945\"},{\"id\":12,\"image_back\":\"https://enjoytickets.oss-cn-beijing.aliyuncs.com/advingImg/Ae824hYQ3H.jpg\",\"url\":\"\",\"title\":\"孙悟空\",\"start_time\":\"2018-09-06 14:54:36\",\"end_time\":\"2019-01-31 14:54:36\",\"system_type\":\"W\",\"is_show\":\"Y\",\"hot\":1,\"page\":\"index\",\"go_type\":\"movie\",\"go_id\":\"228549\"},{\"id\":12,\"image_back\":\"https://enjoytickets.oss-cn-beijing.aliyuncs.com/advingImg/Ae824hYQ3H.jpg\",\"url\":\"\",\"title\":\"无双\",\"start_time\":\"2018-09-06 14:54:36\",\"end_time\":\"2019-01-31 14:54:36\",\"system_type\":\"W\",\"is_show\":\"Y\",\"hot\":1,\"page\":\"index\",\"go_type\":\"movie\",\"go_id\":\"228549\"},{\"id\":12,\"image_back\":\"https://enjoytickets.oss-cn-beijing.aliyuncs.com/advingImg/Ae824hYQ3H.jpg\",\"url\":\"\",\"title\":\"影\",\"start_time\":\"2018-09-06 14:54:36\",\"end_time\":\"2019-01-31 14:54:36\",\"system_type\":\"W\",\"is_show\":\"Y\",\"hot\":1,\"page\":\"index\",\"go_type\":\"movie\",\"go_id\":\"228549\"}]";
    private String jsonString2 = "[{\"id\":525339,\"name\":\"无双\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i1/TB11.34cVzqK1RjSZSgXXcpAVXa_.jpg_300x300.jpg\",\"saleType\":1,\"actor\":\"周润发,郭富城,张静初,冯文娟,廖启智,周家怡,王耀庆,方中信\",\"director\":\"麦兆辉, 庄文强\",\"description\":\"《无双》讲述了以代号“画家”（周润发饰）为首的犯罪团伙，掌握了制造伪钞技术，难辨真伪，并在全球进行交易获取利益，引起警方高度重视。然而“画家”和其他成员的身份一直成谜，警方的破案进度遭受到了前所未有的挑战。在关键时刻，擅长绘画的李问（郭富城饰）打开了破案的突破口，而“画家”的真实身份却让众人意想不到……\",\"score\":8.9,\"show_mark\":\"2D IMAX\",\"open_time\":\"2018-09-30\",\"background_picture\":\"i3/TB1DQe7tbZnBKNjSZFGXXbt3FXa_.jpg\",\"like_num\":4,\"length\":0,\"poster\":\"i1/TB11.34cVzqK1RjSZSgXXcpAVXa_.jpg\"},{\"id\":519073,\"name\":\"宝贝儿\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i4/TB1r94_pHorBKNjSZFjXXc_SpXa_.jpg_300x300.jpg\",\"saleType\":2,\"actor\":\"杨幂,郭京飞,李鸿其\",\"director\":\"刘杰\",\"description\":\"《宝贝儿》是由侯孝贤监制、刘杰执导的纪实风格文艺片，讲述的是一个因为严重先天缺陷而被父母抛弃的弃儿江萌（杨幂 饰），拯救另一个被父母宣判了“死刑”的缺陷婴儿的故事。\",\"score\":0.0,\"open_time\":\"2018-10-19\",\"like_num\":1,\"length\":0,\"poster\":\"i4/TB1r94_pHorBKNjSZFjXXc_SpXa_.jpg\"},{\"id\":333986,\"name\":\"我的间谍前男友\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i2/TB15JGmwVooBKNjSZPhXXc2CXXa_.jpg_300x300.jpg\",\"saleType\":2,\"actor\":\"米拉·库妮丝,凯特·麦克金农,贾斯汀·塞洛克斯,山姆·修汉\",\"director\":\"苏珊娜·福格尔\",\"description\":\"奥黛丽（米拉·库妮丝 饰）和摩根（凯特·迈克金农 饰）两位好闺蜜，因奥黛丽前男友的间谍身份，意外被卷入一场国际阴谋，在欧洲大陆上演惊心动魄又啼笑皆非的国际逃亡之旅。\",\"score\":0.0,\"show_mark\":\"2D 中国巨幕\",\"open_time\":\"2018-10-19\",\"like_num\":0,\"length\":0,\"poster\":\"i2/TB15JGmwVooBKNjSZPhXXc2CXXa_.jpg\"},{\"id\":214157,\"name\":\"影\",\"bannerVsmallUrl\":\"https://gw.alicdn.com/tfscom//i1/TB1a0iOX3TqK1RjSZPhXXXfOFXa_.jpg_300x300.jpg\",\"saleType\":1,\"actor\":\"邓超,孙俪,郑恺,王千源,王景春,胡军,关晓彤,吴磊\",\"director\":\"张艺谋\",\"description\":\"这是一个关于替身的故事。替身自古有之，人称“影子”。有刺杀，就有影子，影子必须在危急关头挺身而出，替主人博回一命；影子又必须与真身互为一体，令旁人真假难辨如同孪生。若无真身，影子何在？关于影子的来龙去脉，真身从来忌讳莫深，不愿提及而令真相扑朔迷离。是愿做真身，还是愿做影子？是明处做人，还是暗处做鬼，换作是你，如何选择？\",\"score\":8.3,\"show_mark\":\"2D IMAX\",\"open_time\":\"2018-09-30\",\"background_picture\":\"i2/TB1s1XRXrvpK1RjSZFqXXcXUVXa_.jpg\",\"like_num\":1,\"length\":0,\"poster\":\"i1/TB1a0iOX3TqK1RjSZPhXXXfOFXa_.jpg\"}]";

    //资讯
    private ViewpagerAdapter viewpagerAdapter2;

    //预告片变量
    private PrevueMovieAdapter prevueMovieAdapter;
    private List<InformationBean> informationBeans = new ArrayList<>();
    //专访片变量
    private InterviewMovieAdpter2 interviewMovieAdpter2;
    private List<InformationBean2> informationBean2s = new ArrayList<>();

    private static Context context;


    @Override
    protected void initView(View view) {
        //初始化节点
        //Log.d("jinyangyang+","HomePageFragment initView be called");

        bin.svHome.setHeader(new AliHeader(mContext, true));
        //设置上拉加载更多数据
        bin.svHome.setFooter(new AliFooter(mContext, true));
        bin.svHome.setType(SpringView.Type.FOLLOW);
        bin.svHome.setListener(this);

        LinearLayoutManager prevueLinearLayoutManager = new LinearLayoutManager(mContext);
        prevueLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bin.prevue.setLayoutManager(prevueLinearLayoutManager);
        bin.prevue.addItemDecoration(new SpacesItemDecoration(20));
        //设置该属性让recyclerview滑动没有黏连的感觉
        bin.prevue.setNestedScrollingEnabled(false);


        LinearLayoutManager interviewLinearLayoutManager = new LinearLayoutManager(mContext);
        interviewLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bin.interview.setLayoutManager(interviewLinearLayoutManager);
        bin.interview.addItemDecoration(new SpacesItemDecoration2(0));
        //设置该属性让recyclerview滑动没有黏连的感觉
        bin.interview.setNestedScrollingEnabled(false);
        setAllAdapter();

    }

    public void setAllAdapter(){
        //资讯

        //预告片adapter
        prevueMovieAdapter = new PrevueMovieAdapter(informationBeans, mContext);
        bin.prevue.setAdapter(prevueMovieAdapter);
        prevueMovieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("jinyangyang+","prevueMovieAdapter position is "+position);
                //获取对应的播放器播放地址
                //String url = "http://222.73.132.149:80/variety.tc.qq.com/Aeu8cAEdlpS2_54pTYKSqxucsLY6Og7LTrhsOYCsTo5s/z0200uu9mlu.p201.1.mp4?vkey=87C3C6709972DEC4512F6B5F208AAD72CC55FE09B433D057D356DA11CC17C11A60551FE6D14FAAFCED96D7E434D3686A65A96757AB69877485EC5F81ACB64C9D3E0E27ED5C1DD19AA654DF57357231894A1875692245966493B538C2AFF74C46A0C7A188736B713DA73696A557E68F9ADDC4846BAB6080DB&platform=10201&sdtfrom=&fmt=shd&level=0&locid=30ae687b-b44f-4324-9c06-df779469bf3e&size=2578671&ocid=2588612012";
                String url = informationBeans.get(position).getPlayerUrl();
                String name = informationBeans.get(position).getName();
                Log.d("jinyangyang+","Pre playerUrl is "+url);
                Log.d("jinyangyang+","Pre playerName is "+name);
                //startSystemPlyer(url);
                VideoPlayerActivity.startVideoPlayerActivity(mContext, url, name);
            }
        });

        //专访adapter
        interviewMovieAdpter2 = new InterviewMovieAdpter2(informationBean2s,mContext);
        bin.interview.setAdapter(interviewMovieAdpter2);
        interviewMovieAdpter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("jinyangyang+","interviewMovieAdpter2 position is "+ position);
                //获取对应的播放器播放地址
                //String url = "http://222.73.132.149:80/variety.tc.qq.com/Aeu8cAEdlpS2_54pTYKSqxucsLY6Og7LTrhsOYCsTo5s/z0200uu9mlu.p201.1.mp4?vkey=87C3C6709972DEC4512F6B5F208AAD72CC55FE09B433D057D356DA11CC17C11A60551FE6D14FAAFCED96D7E434D3686A65A96757AB69877485EC5F81ACB64C9D3E0E27ED5C1DD19AA654DF57357231894A1875692245966493B538C2AFF74C46A0C7A188736B713DA73696A557E68F9ADDC4846BAB6080DB&platform=10201&sdtfrom=&fmt=shd&level=0&locid=30ae687b-b44f-4324-9c06-df779469bf3e&size=2578671&ocid=2588612012";
                String url = informationBean2s.get(position).getPlayerUrl();
                String name = informationBean2s.get(position).getName();
                Log.d("jinyangyang+","ExInfo playerUrl is "+url);
                //startSystemPlyer(url);
                VideoPlayerActivity.startVideoPlayerActivity(mContext, url, name);
            }
        });


    }
    public static HomePageFragment newInstance(Context context) {
        HomePageFragment fragment = new HomePageFragment();
        HomePageFragment.context = context;
        return fragment;
    }
    private int pageNum = 0;
    @Override
    protected void initData() {
        pageNum = 0;
        presenter.requestBannerData();
        presenter.requestInfoData();
        presenter.requestPreData();
        presenter.requestExInfoData(pageNum+"");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void showBannerView(String data) {
        Log.d("jinyangyang+","showBannerView bannerData is  ----------  "+data);
        try{
            JSONObject jsonObject = new JSONObject(data);
            int messageId = jsonObject.optInt("messageId");
            String message = jsonObject.optString("message");
            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            JSONArray jsonArray = jsonObject1.optJSONArray("rows");
            final List<Advertising> advertisings = new ArrayList<>();
            List<String> imgs = new ArrayList<>();
            imgs.clear();
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
                    if (bin.banner != null) {
                        LinearLayout guideSelect = (LinearLayout)bin.guideSelect;
                        bin.banner.setViewUrls(imgs, null, guideSelect);
                        bin.banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
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
                                    CurrencyWebActivity.startCurrencyWeb(context, advertisings.get(position).getTurnUrl(),"");
                                }else if(jumpType.equals("2")){
                                    //startSystemPlyer(advertisings.get(position).getTurnUrl());
                                    VideoPlayerActivity.startVideoPlayerActivity(mContext, advertisings.get(position).getTurnUrl(), "");
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
                bin.banner.setVisibility(View.GONE);
            }


        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    public void showInfoShow(String data) {
        try{
            JSONObject jsonObject = new JSONObject(data);
            int messageId = jsonObject.optInt("messageId");
            String message = jsonObject.optString("message");
            Log.d("jinyangyang+","showInfoShow InfoData is  ----------  "+data);
            //加载资讯数据
            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            JSONArray jsonArray = jsonObject1.optJSONArray("rows");
            List list2 = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                //Log.d("jinyangyang+","img is "+jsonObject.optString("image_back"));
                InformationBean informationBean = new InformationBean();
                informationBean.setBackground_picture(jsonObject3.optString("infoPoster"));
                informationBean.setInfoDetailUrl(jsonObject3.optString("infoDetailUrl"));
                list2.add(informationBean);
            }
            viewpagerAdapter2 = new ViewpagerAdapter(getActivity(), list2);
            bin.viewpagerInfo.setAdapter(viewpagerAdapter2);
            viewpagerAdapter2.notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showPreShow(String data) {
        try{
            Log.d("jinyangyang+","showPreShow PreData is  ----------  "+data);
            JSONObject jsonObject = new JSONObject(data);
            int messageId = jsonObject.optInt("messageId");
            String message = jsonObject.optString("message");

            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            JSONArray jsonArray = jsonObject1.optJSONArray("rows");

            //加载预告片数据
            List<InformationBean> movieBeans = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                InformationBean informationBean = new InformationBean();
                informationBean.setBackground_picture(jsonObject2.optString("infoPoster"));
                informationBean.setPlayerUrl(jsonObject2.optString("infoContent"));
                informationBean.setName(jsonObject2.optString("infoTitle"));
                movieBeans.add(informationBean);
            }
            informationBeans.clear();
            informationBeans.addAll(movieBeans);
            prevueMovieAdapter.notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showExInfoShow(String data) {
        try {
            Log.d("jinyangyang+","showExInfoShow ExInfoData is  ----------  "+data);
            JSONObject jsonObject = new JSONObject(data);
            int messageId = jsonObject.optInt("messageId");
            String message = jsonObject.optString("message");
            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            JSONArray jsonArray = jsonObject1.optJSONArray("rows");
            if (jsonArray.length() > 0) {
                bin.svHome.onFinishFreshAndLoad();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    InformationBean2 InterViewBean2 = new InformationBean2();
                    InterViewBean2.setBackground_picture(jsonObject2.optString("infoPoster"));
                    InterViewBean2.setName(jsonObject2.optString("infoTitle"));
                    InterViewBean2.setPlayerUrl(jsonObject2.optString("infoContent"));
                    InterViewBean2.setMovieLength("04:38");
                    informationBean2s.add(InterViewBean2);
                }
                interviewMovieAdpter2.setNewData(informationBean2s);
            }else{
                bin.svHome.onFinishFreshAndLoad();
            }
        } catch (JSONException e) {
            e.printStackTrace();
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

    class SpacesItemDecoration2 extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration2(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
        }
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        informationBean2s.clear();
        presenter.requestBannerData();
        presenter.requestInfoData();
        presenter.requestPreData();
        presenter.requestExInfoData(pageNum+"");
    }

    @Override
    public void onLoadmore() {
        pageNum++;
        presenter.requestExInfoData(pageNum+"");
    }
    //启动系统播放器方法
    public void startSystemPlyer(String url){
        //示例，实际填你的网络视频链接
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);mediaIntent.setDataAndType(Uri.parse(url), mimeType);startActivity(mediaIntent);
    }
}
