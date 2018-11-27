package com.wanpiao.master.retrofit;

/**
 * 描述:
 * API 连接常量
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-13 10:09
 */
public class ApiContstants {
    //登录
    public static final String USER = "user/rest/login";
    //注册
    public static final String REGISTER = "user/rest/register";
    //登出
    public static final String LOGOUT = "user/rest/loginOut";
    //首页广告
    public static final String HOMEADV = "advertisement/rest/AdervitiseList";
    //首页资讯、预告片、专访
    public static final String HOMEINFOPREEXINFO = "information/rest/informationList";
    //电影列表页面
    public static final String MOVIELIST = "movie/rest/getMovieList";
    //版本更新
    public static final String VERSIONUPDATE = "rest/version/getVersion";
    //上传头像
    public static final String UPLOADHEADER = "user/rest/userUpload";
    //更新用户信息包括昵称和性别
    public static final String UPDATEUSERINFO = "user/rest/update";
    //点赞和点赞取消
    public static final String DIANZANORCANCEL = "user/rest/userOperation";
    //电影详情页面
    public static final String FILMDETAIL = "movie/rest/getMovieByKey";
    //评论影片
    public static final String COMMENTFILM = "movie/rest/insertMovieComment";
    //获取评论列表
    public static final String SELECTCOMMENTLIST = "movie/rest/getMovieComment";
    //开机广告
    public static final String SPLASHADV = "advertisement/rest/getAppStartAdver";
    //我的通知
    public static final String MYINFO = "movie/rest/getNotifyMovieList";
    //我的评论
    public static final String MYCOMMENT = "movie/rest/myMovieComment";
    //隐私
    public static final String PRIVACY = "setting/rest/privacy";
    //服务
    public static final String SERVER = "setting/rest/server";
    //关于我们
    public static final String ABOUT = "setting/rest/about";
    //获取语音识别所需要的accessToken
    public static final String ACCESSTOKEN = "user/rest/getAccessToken";




}
