package com.wanpiao.master.mvp.presenter;

import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.SettingContract;
import com.wanpiao.master.mvp.ui.activity.LoginActivity;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import androidx.fragment.app.DialogFragment;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
public class SettingPresenter extends BasePresenter<SettingContract.View, SettingContract.Model> implements SettingContract.Presenter {

    @Inject
    public SettingPresenter(SettingContract.View rootView, SettingContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void requestLogOut() {
        if(SPUtils.get(App.getInstance(), "userPassword","").equals("")||SPUtils.get(App.getInstance(), "userAccount","").equals("")){
            ToastUtil.show(App.getInstance(), "您好，当前已经是登出状态，请重新登录");
            return;
        }
        mModel.logOut()
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>(){
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","s ----------  "+s);
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            int messageId = jsonObject.optInt("messageId");
                            //messageId = 1403;
                            String message = jsonObject.optString("message");
                            String token = jsonObject.optString("token");
                            Log.d("jinyangyang+","messageId ========++++++++++++  "+messageId);
                            //message字段代表token,登录成功之后需要把token保存到本地
                            //判定登录是否成功,message
                            if (messageId == 200 && message!=""){
                                //退出登录暂时不用置空token
                                //SPUtils.put(App.getInstance(), "token","");
                                ToastUtil.show(App.getInstance(),"登出成功！");
                                Log.d("jinyangyang+ ","token is is is "+ SPUtils.get(App.getInstance(), "token",""));
                                SPUtils.put(App.getInstance(), "token", token);
                                SPUtils.put(App.getInstance(), "isLogin", "");
                                SPUtils.put(App.getInstance(),"userAccount", "");
                                SPUtils.put(App.getInstance(),"userPassword", "");
                                SPUtils.put(App.getInstance(), "userName", "");
                                SPUtils.put(App.getInstance(),"id","");
                                SPUtils.put(App.getInstance(),"userSex","");
                                SPUtils.put(App.getInstance(), "userPortrait", "");
                                mRootView.logOutSucess();
                            }else if(messageId == 1403){
                                //登录失败重置成游客token
                                ToastUtil.show(App.getInstance(), "一账号只能登录一个手机: messageId "+messageId);
                                SPUtils.put(App.getInstance(), "token", token);
                                SPUtils.put(App.getInstance(), "isLogin", "");
                                SPUtils.put(App.getInstance(),"userAccount", "");
                                SPUtils.put(App.getInstance(),"userPassword", "");
                                SPUtils.put(App.getInstance(), "userName", "");
                                SPUtils.put(App.getInstance(),"id","");
                                SPUtils.put(App.getInstance(),"userSex","");
                                SPUtils.put(App.getInstance(), "userPortrait", "");
                                mRootView.logOutFail();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    protected void onFinally() {
                        super.onFinally();
                    }
                });
    }

    @Override
    public void privacy() {
        mModel.privacy()
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>(){
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","s ----------  "+s);
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            int messageId = jsonObject.optInt("messageId");
                            String message = jsonObject.optString("message");
                            Log.d("jinyangyang+","messageId ========++++++++++++  "+messageId);
                            //message字段代表token,登录成功之后需要把token保存到本地
                            //判定登录是否成功,message
                            if (messageId == 200 && message!=""){
                                //退出登录暂时不用置空token
                                //SPUtils.put(App.getInstance(), "token","");
                                mRootView.showPrivacy(s);
                            }else {

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    protected void onFinally() {
                        super.onFinally();
                    }
                });
    }
}
