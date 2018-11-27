package com.wanpiao.master.retrofit.api;


import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.RegisterModel;
import com.wanpiao.master.utils.SPUtils;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.wanpiao.master.retrofit.ApiContstants.ABOUT;
import static com.wanpiao.master.retrofit.ApiContstants.ACCESSTOKEN;
import static com.wanpiao.master.retrofit.ApiContstants.COMMENTFILM;
import static com.wanpiao.master.retrofit.ApiContstants.DIANZANORCANCEL;
import static com.wanpiao.master.retrofit.ApiContstants.FILMDETAIL;
import static com.wanpiao.master.retrofit.ApiContstants.HOMEADV;
import static com.wanpiao.master.retrofit.ApiContstants.HOMEINFOPREEXINFO;
import static com.wanpiao.master.retrofit.ApiContstants.LOGOUT;
import static com.wanpiao.master.retrofit.ApiContstants.MOVIELIST;
import static com.wanpiao.master.retrofit.ApiContstants.MYCOMMENT;
import static com.wanpiao.master.retrofit.ApiContstants.MYINFO;
import static com.wanpiao.master.retrofit.ApiContstants.PRIVACY;
import static com.wanpiao.master.retrofit.ApiContstants.REGISTER;
import static com.wanpiao.master.retrofit.ApiContstants.SELECTCOMMENTLIST;
import static com.wanpiao.master.retrofit.ApiContstants.SERVER;
import static com.wanpiao.master.retrofit.ApiContstants.SPLASHADV;
import static com.wanpiao.master.retrofit.ApiContstants.UPDATEUSERINFO;
import static com.wanpiao.master.retrofit.ApiContstants.UPLOADHEADER;
import static com.wanpiao.master.retrofit.ApiContstants.USER;
import static com.wanpiao.master.retrofit.ApiContstants.VERSIONUPDATE;


/**
 * 描述: 一个公用的Service
 * --------------------------------------------
 * 工程:
 */
public interface CommonService {
    String token = SPUtils.get(App.getInstance(), "token","");
    @FormUrlEncoded
    @POST(USER)
    Observable<BaseEntity<User>> getUserInfo(@Field("firms_id") String firms_id);

    @Headers({"Content-Type:application/json"})
    @POST(USER)
    Observable<BaseEntity<String>> requestLanding(@Body String json);

    @Headers({"Content-Type:application/json"})
    @POST(REGISTER)
    Observable<BaseEntity<String>> register(@Body String json);

    //退出登录
    @Headers({"Content-Type:application/json"})
    @POST(LOGOUT)
    Observable<BaseEntity<String>> LogOut(@Body String json);


    //首页广告数据
    @Headers({"Content-Type:application/json"})
    @POST(HOMEADV)
    Observable<BaseEntity<String>> homeBannerData(@Body String json);

    //首页资讯
    @Headers({"Content-Type:application/json"})
    @POST(HOMEINFOPREEXINFO)
    Observable<BaseEntity<String>> homeInfo(@Body String json);

    //预告片、专访
    @Headers({"Content-Type:application/json"})
    @POST(HOMEINFOPREEXINFO)
    Observable<BaseEntity<String>> homePre(@Body String json);

    //专访
    @Headers({"Content-Type:application/json"})
    @POST(HOMEINFOPREEXINFO)
    Observable<BaseEntity<String>> homeExInfo(@Body String json);

    //版本更新数据
    @Headers({"Content-Type:application/json"})
    @POST(VERSIONUPDATE)
    Observable<BaseEntity<String>> versionUpdate(@Body String json);

    //电影列表页数据
    @Headers({"Content-Type:application/json"})
    @POST(MOVIELIST)
    Observable<BaseEntity<String>> movieTabOneData(@Body String json);

    //电影列表页数据
    @Headers({"Content-Type:application/json"})
    @POST(MOVIELIST)
    Observable<BaseEntity<String>> movieTabTwoData(@Body String json);


    //上传头像，这种格式不正确，不能使用
    @Headers({"Content-Type:application/json"})
    @POST(UPLOADHEADER)
    Observable<BaseEntity<String>> uploadHeader(@Body String json);

    //更新个人用户信息，比如用户昵称和性别
    @Headers({"Content-Type:application/json"})
    @POST(UPDATEUSERINFO)
    Observable<BaseEntity<String>> upDateUserInfo(@Body String json);

    //电影详情页面
    @Headers({"Content-Type:application/json"})
    @POST(FILMDETAIL)
    Observable<BaseEntity<String>> requestDetailFilm(@Body String json);

    //点赞或取消点赞
    @Headers({"Content-Type:application/json"})
    @POST(DIANZANORCANCEL)
    Observable<BaseEntity<String>> requestDianzanData(@Body String json);

    //通知或者取消通知
    @Headers({"Content-Type:application/json"})
    @POST(DIANZANORCANCEL)
    Observable<BaseEntity<String>> requestInfoData(@Body String json);

    //评论电影节目
    @Headers({"Content-Type:application/json"})
    @POST(COMMENTFILM)
    Observable<BaseEntity<String>> sendCommentFilm(@Body String json);

    //查询电影评论列表
    @Headers({"Content-Type:application/json"})
    @POST(SELECTCOMMENTLIST)
    Observable<BaseEntity<String>> selectCommentList(@Body String json);

    //开机广告
    @Headers({"Content-Type:application/json"})
    @POST(SPLASHADV)
    Observable<BaseEntity<String>> selectSplashAdv(@Body String json);

    //我的通知
    @Headers({"Content-Type:application/json"})
    @POST(MYINFO)
    Observable<BaseEntity<String>> selectMyInfo(@Body String json);

    //我的通知
    @Headers({"Content-Type:application/json"})
    @POST(MYCOMMENT)
    Observable<BaseEntity<String>> selectMyComment(@Body String json);

    //隐私
    @Headers({"Content-Type:application/json"})
    @POST(PRIVACY)
    Observable<BaseEntity<String>> privacy(@Body String json);

    //隐私
    @Headers({"Content-Type:application/json"})
    @POST(SERVER)
    Observable<BaseEntity<String>> server(@Body String json);

    //关于我们
    @Headers({"Content-Type:application/json"})
    @POST(ABOUT)
    Observable<BaseEntity<String>> about(@Body String json);

    //post请求方式之一，拼接方式
//    @FormUrlEncoded
//    @POST(REGISTER)
//    //Observable<BaseEntity<String>> homeBannerData(@Field ("jsonObject")JSONObject jsonObject);
//    Observable<BaseEntity<String>> movieTabOneData(@Field("userSex") String userSex,
//                                                       @Field("userAccount") String userAccount,
//                                                       @Field("userName") String userName);






//    @GET("do/getuseralldo")
//    Observable getUserallDo(@Query("page") String page
//            , @Query("size") String size);

    //@Headers({"Content-Type:application/json"})
//    @GET(PRIVACY)
//    Observable <String>getPriVacy();

}
