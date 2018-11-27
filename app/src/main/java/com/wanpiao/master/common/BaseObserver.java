package com.wanpiao.master.common;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.wanpiao.master.App;
import com.wanpiao.master.mvp.ui.activity.LoginActivity;
import com.wanpiao.master.receiver.GlobalReceiver;
import com.wanpiao.master.utils.L;
import com.wanpiao.master.utils.NetworkUtils;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;

import java.net.SocketException;
import java.net.UnknownHostException;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 描述: Observer的基础类
 * --------------------------------------------
 * 工程:
 *
 * @author Administrator
 */

public abstract class BaseObserver<T> extends DisposableObserver<BaseEntity<T>> {
    private final String SUCCESS_CODE = "200";
    private final String ERROR_CODE = "400";
    private final String TOKEN_INVALID_CODE = "001";
    private final String APP_NEED_UPDATE_CODE = "100";

    private final String LOGIN_FAIL = "1400";
    private final String TOKEN_Invalid = "1401";
    private final String TOKEN_Overdue = "1402";
    private final String ONLY_LOGIN_ONE = "1403";
    private final String REGISTER_FAIL = "1404";
    private final String SERVER_EXCPTION = "500";

//    1400 登录失败
//    1401 token无效
//    1402 token过期
//    1403 一账号只能登录一个手机
//    1404 注册失败
//    500  服务器异常

    @Override
    public void onNext(@NonNull BaseEntity<T> tBaseEntity) {
        Log.d("jinyangyang+","tBaseEntity.getMessageId() ============ "+tBaseEntity.getMessageId());
        if (SUCCESS_CODE.equals(tBaseEntity.getMessageId())) {
            T t = tBaseEntity.getData();
            T allData = tBaseEntity.getAllData();
            //onHandleSuccess(t);
            onHandleSuccess(allData);
        }
        /*

        else if (ERROR_CODE.equals(tBaseEntity.getMessageId())) {
//            if (TOKEN_INVALID_CODE.equals(tBaseEntity.getError())) {
//                //TOKE 失效 需要重新登录
//                Intent intent = new Intent(GlobalReceiver.ACTION_APP_LOGIN_OUT);
//                intent.setComponent(new ComponentName("com.youjuke.merchantbizmanage",
//                        "com.youjuke.merchantbizmanage.receiver.GlobalReceiver"));
//                App.getInstance().sendOrderedBroadcast(intent,null);
//            }else if(APP_NEED_UPDATE_CODE.equals(tBaseEntity.getError())){
//                //app需要更新
//                Intent intent = new Intent(GlobalReceiver.ACTION_APP_NEED_UPDATE);
//                intent.setComponent(new ComponentName("com.youjuke.merchantbizmanage",
//                        "com.youjuke.merchantbizmanage.receiver.GlobalReceiver"));
//                App.getInstance().sendOrderedBroadcast(intent,null);
//            }else{
//                onHandleFailed(tBaseEntity.getError(), tBaseEntity.getMessage());
//            }
            T t = tBaseEntity.getData();
            T allData = tBaseEntity.getAllData();
            //onHandleSuccess(t);
            onHandleSuccess(allData);
        } else {
            L.e("BaseObserver", tBaseEntity.getMessageId() + "--" + tBaseEntity.getMessage());
//            ToastUtil.show(App.getInstance(),
//                    App.getInstance().getString(R.string.service_error_tips));
            T t = tBaseEntity.getData();
            T allData = tBaseEntity.getAllData();
            //onHandleSuccess(t);
            onHandleSuccess(allData);
        }

*/

        else if(SERVER_EXCPTION.equals(tBaseEntity.getMessageId())){
            if(tBaseEntity.getMessage().equals("")){
                ToastUtil.show(App.getInstance(), "服务器异常");
                return;
            }else {
                ToastUtil.show(App.getInstance(), tBaseEntity.getMessage());
                return;
            }
        }else if(REGISTER_FAIL.equals(tBaseEntity.getMessageId())){
            if(tBaseEntity.getMessage().equals("")){
                ToastUtil.show(App.getInstance(), "注册失败");
                return;
            }else {
                ToastUtil.show(App.getInstance(), tBaseEntity.getMessage());
                return;
            }
        }else if(LOGIN_FAIL.equals(tBaseEntity.getMessageId())){
            if(tBaseEntity.getMessage().equals("")){
                ToastUtil.show(App.getInstance(), "登录失败");
                return;
            }else {
                ToastUtil.show(App.getInstance(), tBaseEntity.getMessage());
                return;
            }
        }else if(TOKEN_Invalid.equals(tBaseEntity.getMessageId())||TOKEN_Overdue.equals(tBaseEntity.getMessageId())||ONLY_LOGIN_ONE.equals(tBaseEntity.getMessageId())){
            SPUtils.put(App.getInstance(), "token", tBaseEntity.getToken());
            SPUtils.put(App.getInstance(), "isLogin", "");
            Log.d("jinyangyang+"," TOKEN_Invalid ============ "+TOKEN_Invalid);
            Intent intent = new Intent(GlobalReceiver.ACTION_TOKEN_QUESTION);
                //intent.setComponent(new ComponentName("com.youjuke.merchantbizmanage", "com.youjuke.merchantbizmanage.receiver.GlobalReceiver"));
            App.getInstance().sendBroadcast(intent);
        }else {
            ToastUtil.show(App.getInstance(), tBaseEntity.getMessageId());
            return;
        }

    }

//    @Override
//    public void onError(@NonNull Throwable e) {
//        e.printStackTrace();
//        if (e instanceof JsonSyntaxException
//                || e instanceof JSONException
//                || e instanceof IllegalStateException) {
//            //ToastUtil.show(App.getInstance(), "数据异常，请稍后再试");
//        } else if (e instanceof UnknownHostException || e instanceof SocketException || e instanceof HttpException) {
//            //ToastUtil.show(App.getInstance(), "服务器异常，请稍后再试");
//        }else if (e instanceof NullPointerException){
//            //ToastUtil.show(App.getInstance(), "数据异常，请稍后再试");
//        }else {
//            //ToastUtil.show(App.getInstance(), "数据异常，请稍后再试");
//        }
//        if (!NetworkUtils.isConnected(App.getInstance())) {
//            ToastUtil.show(App.getInstance(), "网络连接不可用，检查你的网络设置");
//        }
//        onFinally();
//    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        if ( e instanceof HttpException){

            L.d(" code"+((HttpException) e).code());
            if (((HttpException) e).code()==401){
                Log.d("jinyangyang+"," code is "+((HttpException) e).code());

            }

        }

        if (e instanceof JsonSyntaxException
                || e instanceof JSONException
                || e instanceof IllegalStateException) {
            //ToastUtil.show(App.getInstance(), "数据异常，请稍后再试");
            Log.d("jinyangyang+", "数据异常，请稍后再试");
        } else if (e instanceof UnknownHostException || e instanceof SocketException ) {
            String f = e.getMessage();
            String es = e.toString();
            Log.d("jinyangyang+","es ============ "+es);
            Log.d("jinyangyang+", "f ============ "+f);
            Log.d("jinyangyang+", "服务器异常，请稍后再试");
            //ToastUtil.show(App.getInstance(), "服务器异常，请稍后再试");
        }else if (e instanceof NullPointerException){
            //ToastUtil.show(App.getInstance(), "数据异常，请稍后再试");
            Log.d("jinyangyang+","数据异常，请稍后再试");
        }else {
            //ToastUtil.show(App.getInstance(), "数据异常，请稍后再试");
            Log.d("jinyangyang+","数据异常，请稍后再试");
        }
        if (!NetworkUtils.isConnected(App.getInstance())) {
            ToastUtil.show(App.getInstance(), "网络连接不可用，检查你的网络设置");
        }
        Log.d("jinyangyang+","onError be called ========");
//        SPUtils.put(App.getInstance(), "isLogin", "");
//        Intent intent = new Intent(GlobalReceiver.ACTION_TOKEN_QUESTION);
//        //intent.setComponent(new ComponentName("com.youjuke.merchantbizmanage", "com.youjuke.merchantbizmanage.receiver.GlobalReceiver"));
//        App.getInstance().sendBroadcast(intent);
        onFinally();
    }

    @Override
    public void onComplete() {
        onFinally();
    }

    protected abstract void onHandleSuccess(T t);

    protected void onFinally() {

    }

    protected void onHandleFailed(String error_code, String message) {
        ToastUtil.show(App.getInstance(), message);
    }
}
