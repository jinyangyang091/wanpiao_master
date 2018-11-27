package com.wanpiao.master.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonIOException;
import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.contract.RegisterContract;
import com.wanpiao.master.mvp.model.bean.UserSaveBean;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/18      onCreate
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View,RegisterContract.Model> implements RegisterContract.Presenter {

    @Inject
    public RegisterPresenter(RegisterContract.View rootView, RegisterContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void registerUser(String userName, int userSex , String userAccount, String userPassword) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.show(App.getInstance(), "您好，用户昵称不能为空");
            return;
        }
        Log.d("jinyangyang+","   userSex    ======== "+userSex);
        if (userSex==0 || userSex==1){

        }else{
            ToastUtil.show(App.getInstance(), "您好，请选择性别");
            return;
        }

        if (!checkEmaile(userAccount)) {//邮箱是否验证成功
            ToastUtil.show(App.getInstance(),"请输入正确的邮箱地址");
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            ToastUtil.show(App.getInstance(), "您好，密码不能为空");
            return;
        }
        if(userPassword.length()<6){
            ToastUtil.show(App.getInstance(), "您好，密码最少六位字符");
            return;
        }
        mModel.register(userName, userSex , userAccount,   userPassword)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","s ========= "+s);
                        try {
                            //  "messageId": 401,
                            //  "message": "Unauthorized",
                            //  "timestamp": "2018-10-24T07:55:12.089+0000",
                            JSONObject jsonObject = new JSONObject(s);
                            String messageId = jsonObject.optString("messageId");
                            String message = jsonObject.optString("message");
                            String timestamp = jsonObject.optString("timestamp");
                            Log.d("jinyangyang+","messageId = "+messageId+", message = "+message+" timestamp = "+timestamp);
                            if (messageId.equals("200")){
                                ToastUtil.show(App.getInstance(), "注册成功");
                                mRootView.registerSuccess(userAccount, userPassword);
                                return;
                            }else if(messageId.equals("400")){
                                ToastUtil.show(App.getInstance(), "注册失败,重复的账号");
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void onFinally() {
                        super.onFinally();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d("jinyangyang+","data error e is "+e);
                    }
                });
    }

    @Override
    public void requestLanding(Context context, String mail, String pwd) {
        mModel.requestLanding(context,mail,pwd )
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","s ----------  "+s);
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            int messageId = jsonObject.optInt("messageId");
                            String message = jsonObject.optString("message");
                            String token = jsonObject.optString("token");
                            Log.d("jinyangyang+","messageId ========++++++++++++  "+messageId);
                            Log.d("jinyangyang+","token ========++++++++++++  "+token);
                            //message字段代表token,登录成功之后需要把token保存到本地
                            //判定登录是否成功,message
                            if (messageId == 200 && message!=""){
                                SPUtils.put(App.getInstance(), "token",token);
                                //SPUtils.put("userAccount",);
//                                JSONArray jsonArray = jsonObject.optJSONArray("data");
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);


                                JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                                String userAccount = jsonObject1.optString("userAccount");
                                String userPassword = jsonObject1.optString("userPassword");
                                String userName = jsonObject1.optString("userName");
                                String id = jsonObject1.optString("id");
                                String userSex = jsonObject1.optString("userSex");
                                String userPortrait = jsonObject1.optString("userPortrait");
                                String isLogin = jsonObject1.optString("isLogin");
                                SPUtils.put(App.getInstance(), "isLogin", isLogin);
                                SPUtils.put(App.getInstance(),"userAccount", userAccount);
                                SPUtils.put(App.getInstance(),"userPassword", userPassword);
                                SPUtils.put(App.getInstance(), "userName", userName);
                                SPUtils.put(App.getInstance(),"id",""+id);
                                SPUtils.put(App.getInstance(),"userSex",""+userSex);
                                SPUtils.put(App.getInstance(), "userPortrait", userPortrait);

                                //ToastUtil.show(App.getInstance(),"登录成功！");
                                Log.d("jinyangyang+ ","token is is is "+ SPUtils.get(App.getInstance(), "token",""));
                                UserSaveBean userSaveBean = new UserSaveBean();


                                mRootView.landingSuccess();
                            }else {
                                ToastUtil.show(App.getInstance(), "对不起，登录失败！");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void onFinally() {
                        super.onFinally();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d("jinyangyang+","data error e is "+e);
                    }
                });
    }

    //正则表达式
    public static boolean checkEmaile(String emaile) {
        /**
         *   ^匹配输入字符串的开始位置
         *   $结束的位置
         *   \转义字符 eg:\. 匹配一个. 字符  不是任意字符 ，转义之后让他失去原有的功能
         *   \t制表符
         *   \n换行符
         *   \\w匹配字符串  eg:\w不能匹配 因为转义了
         *   \w匹配包括字母数字下划线的任何单词字符
         *   \s包括空格制表符换行符
         *   *匹配前面的子表达式任意次
         *   .小数点可以匹配任意字符
         *   +表达式至少出现一次
         *   ?表达式0次或者1次
         *   {10}重复10次
         *   {1,3}至少1-3次
         *   {0,5}最多5次
         *   {0,}至少0次 不出现或者出现任意次都可以 可以用*号代替
         *   {1,}至少1次  一般用+来代替
         *   []自定义集合     eg:[abcd]  abcd集合里任意字符
         *   [^abc]取非 除abc以外的任意字符
         *   |  将两个匹配条件进行逻辑“或”（Or）运算
         *   [1-9] 1到9 省略123456789
         *    邮箱匹配 eg: ^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$
         *
         */
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emaile);
        //进行正则匹配\
        return m.matches();
    }

}
