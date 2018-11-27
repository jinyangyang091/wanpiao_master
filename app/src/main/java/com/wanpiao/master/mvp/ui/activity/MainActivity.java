package com.wanpiao.master.mvp.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.common.BaseFragment;
import com.wanpiao.master.databinding.ActivityHomeLayoutBinding;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.contract.MainContract;
import com.wanpiao.master.mvp.presenter.MainPresenter;
import com.wanpiao.master.mvp.ui.fragment.HomePageFragment;
import com.wanpiao.master.mvp.ui.fragment.InlandFragment;
import com.wanpiao.master.mvp.ui.fragment.MoviePageFragment;
import com.wanpiao.master.mvp.ui.fragment.MyFragment;
import com.wanpiao.master.mvp.ui.fragment.UpDateFragmentS;
import com.wanpiao.master.utils.DownloadManagerUtil;
import com.wanpiao.master.utils.GetVersionInfoUtils;
import com.wanpiao.master.utils.MessageEvent;
import com.wanpiao.master.utils.MyAlertDialogUtils;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import cn.jpush.android.api.JPushInterface;

public  class MainActivity extends BaseActivity<MainPresenter, ActivityHomeLayoutBinding>implements MainContract.View {
    public static final String TAG = "wanpiao";
    private String jsonString = "[{\"id\":24,\"image_back\":\"https://enjoytickets.oss-cn-beijing.aliyuncs.com/advingImg/CJRAG7BTXy.jpg\",\"url\":\"http://enjoytickets.sit.cinemapos.com/test.html\",\"title\":\"广告1\",\"start_time\":\"2018-09-14 17:02:56\",\"end_time\":\"2024-12-31 17:02:56\",\"system_type\":\"W\",\"is_show\":\"Y\",\"hot\":5,\"page\":\"index\",\"go_type\":\"h5\",\"go_id\":\"\"},{\"id\":13,\"image_back\":\"https://enjoytickets.oss-cn-beijing.aliyuncs.com/advingImg/DcByR2SesY.jpg\",\"url\":\"\",\"title\":\"1111\",\"start_time\":\"2018-09-06 14:54:36\",\"end_time\":\"2019-01-31 14:54:36\",\"system_type\":\"W\",\"is_show\":\"Y\",\"hot\":2,\"page\":\"index\",\"go_type\":\"movie\",\"go_id\":\"359945\"},{\"id\":12,\"image_back\":\"https://enjoytickets.oss-cn-beijing.aliyuncs.com/advingImg/Ae824hYQ3H.jpg\",\"url\":\"\",\"title\":\"1111\",\"start_time\":\"2018-09-06 14:54:36\",\"end_time\":\"2019-01-31 14:54:36\",\"system_type\":\"W\",\"is_show\":\"Y\",\"hot\":1,\"page\":\"index\",\"go_type\":\"movie\",\"go_id\":\"228549\"}]";
    private User user = new User();
    private JSONArray jsonArray;
    private HomePageFragment homePageFragment;
    private MoviePageFragment moviePageFragment;
    private MyFragment myFragment;
    private InlandFragment inlandFragment;
    private BaseFragment currentFrg;
    private Fragment currentFragment;
    public static String ACTION_ITEMVIEW_LISTCLICK = "com.wanpiao.mainactivity.homefragment";
    public static String ACTION_JIGUANG_LISTCLICK = "com.wanpiao.jiguang.cn";
    public static String ACTION_LOGOUT_BACK = "com.wanpiao.logout.back.cn";
    public static String ACTION_LOGOUT_FAIL = "com.wanpiao.logout.fail.cn";
    private BroadcastReceiver Receiver;
    private String[] tags = {"homepage", "movie","inland","my"};
    private int currentIndex=1;
    private Bundle savedInstanceState;

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        //showDialog();
        //JSONArray必须抛出异常，否则报错
        try{
            jsonArray = new JSONArray(jsonString);
            //假数据处理首页的所有布局
//            switchFragment(hongKongHomePageFragment, false);
        }catch (JSONException e){
            e.printStackTrace();
        }
        bin.mainRadiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            Log.d("jinyangyang+","mainRadiogroup setOnCheckedChangeListener be called");
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            hideAllFragment(ft);
            switch(checkedId){
                //rb_home_page, rb_message, rb_management, rb_me
                case R.id.rb_home_page:
                    bin.rbHomePageImg.setVisibility(View.VISIBLE);
                    bin.rbMessageImage.setVisibility(View.INVISIBLE);
                    bin.rbManagementImage.setVisibility(View.INVISIBLE);
                    bin.rbMeImage.setVisibility(View.INVISIBLE);

                    bin.rbHomePage.setVisibility(View.INVISIBLE);
                    bin.rbMessage.setVisibility(View.VISIBLE);
                    bin.rbManagement.setVisibility(View.VISIBLE);
                    bin.rbMe.setVisibility(View.VISIBLE);


                    ViewCompat.animate(bin.rbHomePageImg)
                            .setDuration(500)
                            .scaleX(1.1f)
                            .scaleY(1.1f)
                            .start();
                    ViewCompat.animate(bin.rbMessageImage)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();
                    ViewCompat.animate(bin.rbManagementImage)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();
                    ViewCompat.animate(bin.rbMeImage)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();




//                    if (homePageFragment == null){
//                        homePageFragment =  HomePageFragment.newInstance(MainActivity.this);
//                    }else {
//
//                    }
//                    switchFragment(homePageFragment, false);


//                    if (homePageFragment == null){
//                        homePageFragment =  HomePageFragment.newInstance(MainActivity.this);
//                        ft.add(R.id.frame_container, homePageFragment);
//                    }else {
//                        ft.show(homePageFragment);
//                    }
//                    ft.commit();
                    if(homePageFragment == null){
                        homePageFragment =  HomePageFragment.newInstance(MainActivity.this);
                    }
                    currentIndex = 0;
                    addOrShowFragment(homePageFragment, currentIndex);
                    break;
                case R.id.rb_message:

                    bin.rbHomePageImg.setVisibility(View.INVISIBLE);
                    bin.rbMessageImage.setVisibility(View.VISIBLE);
                    bin.rbManagementImage.setVisibility(View.INVISIBLE);
                    bin.rbMeImage.setVisibility(View.INVISIBLE);


                    bin.rbHomePage.setVisibility(View.VISIBLE);
                    bin.rbMessage.setVisibility(View.INVISIBLE);
                    bin.rbManagement.setVisibility(View.VISIBLE);
                    bin.rbMe.setVisibility(View.VISIBLE);


                    ViewCompat.animate(bin.rbHomePageImg)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();
                    ViewCompat.animate(bin.rbMessageImage)
                            .setDuration(500)
                            .scaleX(1.1f)
                            .scaleY(1.1f)
                            .start();
                    ViewCompat.animate(bin.rbManagementImage)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();
                    ViewCompat.animate(bin.rbMeImage)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();

//                    if (moviePageFragment == null){
//                        moviePageFragment = MoviePageFragment.newInstance(MainActivity.this);
//                    }
//                    switchFragment(moviePageFragment, false);


//                    if (moviePageFragment == null){
//                        moviePageFragment =  MoviePageFragment.newInstance(MainActivity.this);
//                        ft.add(R.id.frame_container, moviePageFragment);
//                    }else {
//                        ft.show(moviePageFragment);
//                    }
//                    ft.commit();

                    if(moviePageFragment == null){
                        moviePageFragment = MoviePageFragment.newInstance(MainActivity.this);
                    }
                    currentIndex = 1;
                    addOrShowFragment(moviePageFragment, currentIndex);
                    break;
                case R.id.rb_management:
                    bin.rbHomePageImg.setVisibility(View.INVISIBLE);
                    bin.rbMessageImage.setVisibility(View.INVISIBLE);
                    bin.rbManagementImage.setVisibility(View.VISIBLE);
                    bin.rbMeImage.setVisibility(View.INVISIBLE);

                    bin.rbHomePage.setVisibility(View.VISIBLE);
                    bin.rbMessage.setVisibility(View.VISIBLE);
                    bin.rbManagement.setVisibility(View.INVISIBLE);
                    bin.rbMe.setVisibility(View.VISIBLE);
                    ViewCompat.animate(bin.rbHomePageImg)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();
                    ViewCompat.animate(bin.rbMessageImage)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();
                    ViewCompat.animate(bin.rbManagementImage)
                            .setDuration(500)
                            .scaleX(1.1f)
                            .scaleY(1.1f)
                            .start();
                    ViewCompat.animate(bin.rbMeImage)
                            .setDuration(0)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start();
                    if (inlandFragment == null){
                        inlandFragment = InlandFragment.newInstance(MainActivity.this);
                    }
                    currentIndex = 2;
                    addOrShowFragment(inlandFragment, currentIndex);

//                    switchFragment(inlandFragment, false);
                    //SettingActivity.startSettingActivity(MainActivity.this);
                    break;
                case R.id.rb_me:
                    //需要判定是否登录
                    if(SPUtils.get(App.getInstance(), "isLogin","").equals("1")){
                        bin.rbHomePageImg.setVisibility(View.INVISIBLE);
                        bin.rbMessageImage.setVisibility(View.INVISIBLE);
                        bin.rbManagementImage.setVisibility(View.INVISIBLE);
                        bin.rbMeImage.setVisibility(View.VISIBLE);

                        bin.rbHomePage.setVisibility(View.VISIBLE);
                        bin.rbMessage.setVisibility(View.VISIBLE);
                        bin.rbManagement.setVisibility(View.VISIBLE);
                        bin.rbMe.setVisibility(View.INVISIBLE);
                        ViewCompat.animate(bin.rbHomePageImg)
                                .setDuration(0)
                                .scaleX(1f)
                                .scaleY(1f)
                                .start();
                        ViewCompat.animate(bin.rbMessageImage)
                                .setDuration(0)
                                .scaleX(1f)
                                .scaleY(1f)
                                .start();
                        ViewCompat.animate(bin.rbManagementImage)
                                .setDuration(0)
                                .scaleX(1f)
                                .scaleY(1f)
                                .start();
                        ViewCompat.animate(bin.rbMeImage)
                                .setDuration(500)
                                .scaleX(1.1f)
                                .scaleY(1.1f)
                                .start();
//                        if (myFragment == null){
//                            myFragment = MyFragment.newInstance(MainActivity.this);
//                        }
//                        switchFragment(myFragment, false);

//                        if (myFragment == null){
//                            myFragment =  MyFragment.newInstance(MainActivity.this);
//                            ft.add(R.id.frame_container, myFragment);
//                        }else {
//                            ft.show(myFragment);
//                        }
//                        ft.commit();

                        if(myFragment == null){
                            myFragment = MyFragment.newInstance(MainActivity.this);
                        }
                        currentIndex = 3;
                        addOrShowFragment(myFragment, currentIndex);
                    }else {
                        bin.mainRadiogroup.check(R.id.rb_home_page);
                        //跳转登录页面
                        LoginActivity.startLoginActivity(mContext);
                    }
                    break;
            }
        });
        bin.mainRadiogroup.check(R.id.rb_message);
        Log.d("jinyangyang+","initViews be called");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    @Override
    public void initToolBar() {
    }

    /**
     * 如果需要eventbus 就返回true
     * @return
     */
    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    protected void initData() {
        //user = presenter.getmModel().getUser(this);
        //启动系统下载
        //upDatePackage();
//        String diveceId = getDeviceId(mContext);
//        Log.d("diveceId =========== ", diveceId);
        Log.d("jinyangyang+","initData be called");
        if (savedInstanceState !=null){
            savedInstanceState = null;
            return;
        }
        initBroadcastListener();
        presenter.versionUpdate();
    }

    @Override
    public void onBackPressed() {
        // moveTaskToBack(true);
    }

    @Override
    public void queryUserSuccess(User user) {

    }

    @Override
    public void showVersionUpdateView(String response) {
        Log.d("jinyangyang+","showVersionUpdateView be called s ============ "+response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.optJSONObject("data");
            int getversionCode = data.optInt("versionCode");
            UpDateFragmentS upDateFragmentS = new UpDateFragmentS();
            Bundle bundle = new Bundle();
            bundle.putString("updateData",response);//这里的values就是我们要传的值
            upDateFragmentS.setArguments(bundle);

            //在这里获取当前版本号
            int VersionCode = GetVersionInfoUtils.getAppVersionCode(App.getInstance());
            String VersionName = GetVersionInfoUtils.getAppVersionName(App.getInstance());
            Log.d("jinyangyang+", "VersionCode ============ "+VersionCode);
            Log.d("jinyangyang+","VersionName ============ "+VersionName);
            Log.d("jinyangyang+","getversionCode ============ "+getversionCode);
            if(getversionCode>VersionCode){
                //upDateFragmentS.setCancelable(false);
                upDateFragmentS.show(getSupportFragmentManager(),"android");
            }else{

            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void startDownloadNewVersion(String downloadUrl) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jinyangyang+","onCreate be called");
        if (savedInstanceState != null) {
            Log.d("jinyangyang+","currentIndex ======== "+currentIndex);
            currentIndex = savedInstanceState.getInt("currentIndex");
            String curTag = tags[currentIndex];
            getSupportFragmentManager().findFragmentByTag(curTag);
            currentFragment = getSupportFragmentManager().findFragmentByTag(curTag);
            homePageFragment = (HomePageFragment) getSupportFragmentManager().findFragmentByTag(tags[0]);
            moviePageFragment = (MoviePageFragment) getSupportFragmentManager().findFragmentByTag(tags[1]);
            inlandFragment = (InlandFragment)getSupportFragmentManager().findFragmentByTag(tags[2]);
            myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag(tags[3]);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("jinyangyang+","onSaveInstanceState currentIndex ============ "+currentIndex);
        outState.putInt("currentIndex", currentIndex);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        Log.d("jinyangyang+","   messageEvent ============ "+messageEvent.getMessage());
        recreate();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiverListener();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * 用于切换RadioGroup中的fragment
     *
     * @param frg 将要显示的fragment
     * @param bs  是否加入回退栈
     */
    /*
        moviePageFragment = MoviePageFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_container, moviePageFragment).commitAllowingStateLoss();
     */

    public void switchFragment(BaseFragment frg, boolean bs) {
        if (currentFrg != frg) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!frg.isAdded()) {
                if (currentFrg != null) {
                    if (bs) {
                        ft.hide(currentFrg).add(R.id.frame_container, frg).addToBackStack(null).commitAllowingStateLoss();
                    } else {
                        ft.hide(currentFrg).add(R.id.frame_container, frg).commitAllowingStateLoss();
                    }
                } else {
                    if (bs) {
                        ft.add(R.id.frame_container, frg).addToBackStack(null).commitAllowingStateLoss();
                    } else {
                        ft.add(R.id.frame_container, frg).commitAllowingStateLoss();
                    }
                }
            } else {
                if (currentFrg != null) {
                    ft.hide(currentFrg).show(frg).commitAllowingStateLoss();
                } else {
                    ft.show(frg).commitAllowingStateLoss();
                }
            }
            currentFrg = frg;
        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(homePageFragment != null)fragmentTransaction.hide(homePageFragment);
        if(moviePageFragment != null)fragmentTransaction.hide(moviePageFragment);
        if(inlandFragment != null)fragmentTransaction.hide(inlandFragment);
        if(myFragment != null)fragmentTransaction.hide(myFragment);
    }

    private void addOrShowFragment(Fragment fragment, int currentIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment == fragment) return;
        if (currentFragment == null) {
            transaction.add(R.id.frame_container, fragment, tags[currentIndex]).commitAllowingStateLoss();
        } else if (!fragment.isAdded()) {
            // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.frame_container, fragment, tags[currentIndex]).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }
        currentFragment = fragment;
    }


    public void upDatePackage(){
        Log.d("jinyangyang+"," upDatePackage be called");
        String url = "http://wanpiao-hk-test.oss-cn-shanghai.aliyuncs.com/wanpiao-hk/information/app-debug.apk";
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
    public static String getDeviceId(Context context){
        String  deviceId = null;
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        }
        return deviceId;
    }


    private void initBroadcastListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ITEMVIEW_LISTCLICK);
        intentFilter.addAction(ACTION_JIGUANG_LISTCLICK);
        intentFilter.addAction(ACTION_LOGOUT_BACK);
        intentFilter.addAction(ACTION_LOGOUT_FAIL);
        Receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(intent.getAction().equals(ACTION_ITEMVIEW_LISTCLICK)) {
                    Log.v("jinyangyang", ACTION_ITEMVIEW_LISTCLICK + "," + intent.getIntExtra("position", -1));
                    bin.mainRadiogroup.check(R.id.rb_message);
                }else if(intent.getAction().equals(ACTION_JIGUANG_LISTCLICK)){
                    abortBroadcast();
                    Log.d("jinyangyang+"," MainActivity  ACTION_JIGUANG_LISTCLICK   ============ "+ACTION_JIGUANG_LISTCLICK);
                    Log.d("jinyangyang+","is MainActivity ============ "+getClass().getSimpleName());
                    MyAlertDialogUtils.showAlertDialog(mContext, intent);
                }else if(intent.getAction().equals(ACTION_LOGOUT_BACK)){
                    Log.d("jinyangyang+", " ACTION_LOGOUT_BACK is "+ACTION_LOGOUT_BACK);
                    bin.mainRadiogroup.check(R.id.rb_home_page);
                }else if(intent.getAction().equals(ACTION_LOGOUT_FAIL)){
                    Log.d("jinyangyang+","ACTION_LOGOUT_FAIL  is "+ACTION_LOGOUT_FAIL);
                    bin.mainRadiogroup.check(R.id.rb_home_page);
                }
            }
        };
        registerReceiver(Receiver, intentFilter);
    }

    private void unregisterReceiverListener(){
        if (Receiver!=null){
            unregisterReceiver(Receiver);
            Receiver=null;
        }
    }


    public void showDialog(Context context, Intent intent){
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        View view = View
//                .inflate(mContext, R.layout.alert_dialog_notice, null);
//        builder.setView(view);
//        builder.setCancelable(true);
//        AlertDialog dialog = builder.create();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialog.show();


        AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("下线通知");
        dialogBuilder.setMessage("你的账户在其他地方登录 Over");
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton("知道了",
                    new DialogInterface.OnClickListener() {
                @Override
                    public void onClick(DialogInterface dialog, int which) {

                }
                 });

        AlertDialog alertDialog = dialogBuilder.create();
        //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        alertDialog.show();
    }
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if(currentIndex == 1){
                if(BaseFragment.switchFlag.equals("1")){
                    if (secondTime - firstTime > 2000) {
                        ToastUtil.show(App.getInstance(), "再按一次退出程序");
                        firstTime = secondTime;
                        return true;
                    } else{
                        finish();
                        finish();
                        finish();
                        finish();
                        finish();
                        finish();
                    }
                }else{
                    Intent intent2 = new Intent(MoviePageFragment.ACTION_SWITCH_LISTCLICK);
                    mContext.sendBroadcast(intent2);
                }
            }else {
                if (secondTime - firstTime > 2000) {
                    ToastUtil.show(App.getInstance(), "再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else{
                    finish();
                    finish();
                    finish();
                    finish();
                    finish();
                    finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //bin.mainRadiogroup.check(R.id.rb_message);
    }

}
