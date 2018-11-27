package com.wanpiao.master.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityUserReviewsBinding;
import com.wanpiao.master.mvp.contract.UserReviewsContract;
import com.wanpiao.master.mvp.presenter.UserReviewPresenter;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.SoftKeyboardControl;
import com.wanpiao.master.utils.TimeHMSSwitchUtil;
import com.wanpiao.master.utils.ToastUtil;
import com.wanpiao.master.utils.UploadUtil;
import com.wanpiao.master.widgets.views.RatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @Description: #
 * 评论页面
 * #0000      @Author: tianxiao     2018/10/21      onCreate
 */
public class UserReviewsActivity extends BaseActivity<UserReviewPresenter, ActivityUserReviewsBinding> implements UserReviewsContract.View ,UploadUtil.OnUploadProcessListener, GestureDetector.OnGestureListener{

    public static void startUserReviewsActivity(Context context) {
        Intent intent = new Intent(context, UserReviewsActivity.class);
        context.startActivity(intent);
    }
    private String JoinId;
    private String movieName;
    private String comment="";
    private int selectedNumInt;

    private static String requestURL = "https://hk.enjoytickets.com/hongkongmovie-mobile/user/rest/userUpload";
    // 要申请的权限
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    // 系统的音频文件
    File soundFile;
    MediaRecorder mRecorder;
    MediaPlayer mediaPlayer = new MediaPlayer();
    MediaPlayer  mediaPlayerUrl = new MediaPlayer();
    private static final int TO_UPLOAD_FILE = 16;
    private String userHeaderUrl="";
    private boolean send = true;
    private boolean overtime = false;
    private CountDownTimer countDownTimer;
    private static int MaxTime = 60;
    private String miao=MaxTime+"";


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Intent intent =getIntent();
        Bundle data=intent.getExtras();
        JoinId = data.getString("JoinId");
        movieName = data.getString("movieName");
        Log.d("jinyangyang+","JoinId movieName ============ "+JoinId+", "+movieName);
//        bin.textView2.setText(movieName);
        bin.textView2.setText(movieName);

        //请求开启录音权限
        //requestPermissions();
        verifyStoragePermissions(UserReviewsActivity.this);
        bin.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bin.sendClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Glide.with(this).load(R.mipmap.icon_yuyin1).into(bin.voiceStateGif);

        bin.sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = (RatingBar)bin.rb;
                float selectedNum = ratingBar.getmSelectedNumber();
                selectedNumInt = (int)selectedNum;
                Log.d("selectedNumInt ======" , selectedNumInt+"");
                comment = bin.commentsEdit.getText().toString();
                if(selectedNumInt == 0){
                    ToastUtil.show(mContext, "请选择星星");
                    return;
                }
                SoftKeyboardControl.closeInputMethod(mContext, bin.commentsEdit);
                presenter.callComments("1", SPUtils.get(App.getInstance(), "id",""), JoinId, comment, selectedNumInt);
            }
        });

        bin.speekBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startY = 0;
                float endY = 0;
                Log.d("jinyangyang+"," send is "+send);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.d("jinyangyang+","MotionEvent.ACTION_DOWN ============ "+MotionEvent.ACTION_DOWN);
                        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            ToastUtil.show(App.getInstance(), "SD卡不存在，请插入SD卡！");
                            break;
                        }else {
                            Log.d("jinyangyang+","SD卡存在");
                        }
                        try {
                            // 创建保存录音的音频文件
                            String curTimeString = ""+System.currentTimeMillis();
                            Log.d("jinyangyang+"," ============= curTimeString ==========="+curTimeString);

                            SimpleDateFormat sDateFormat = new SimpleDateFormat(curTimeString);
                            String time = sDateFormat.format(new java.util.Date());

                            Log.d("jinyangyang+"," =========== time ============"+time);


                            //soundFile = new File(Environment.getExternalStorageDirectory() .getCanonicalFile() + "/"+"wanpiao_"+curTimeString+"_wanpiao_sound_record.amr");
                            //soundFile = new File(Environment.getExternalStorageDirectory() .getCanonicalFile() + "/"+"wanpiao_"+curTimeString+"_wanpiao_sound_record.aac");
                            soundFile = new File(Environment.getExternalStorageDirectory() .getCanonicalFile() + "/"+"wanpiao_"+curTimeString+"_wanpiao_sound_record.mp3");
                            mRecorder = null;
                            mRecorder = new MediaRecorder();
                            // 设置录音的声音来源
                            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);


                            // 设置录制的声音的输出格式（必须在设置声音编码格式之前设置）
                            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            // 设置声音编码格式
                            //mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

                            mRecorder.setOutputFile(soundFile.getAbsolutePath());
                            mRecorder.prepare();
                            // 开始录音
                            mRecorder.start();
                            Log.d("jinyangyang+","mRecorder start");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        bin.changeStateText.setText("手指上滑，取消評論");
                        countDownTimer = null;
                        if(countDownTimer == null){

                        }else {

                        }
                        countDownTimer = new CountDownTimer((MaxTime) * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                miao = (Integer.parseInt((millisUntilFinished / 1000 % 60) + "")) < 10 ? "" + (Integer.parseInt((millisUntilFinished / 1000 % 60) + "")) : (Integer.parseInt((millisUntilFinished / 1000 % 60) + "")) + "";
                                //一秒调用一次
                                Log.d("jinyangyang+","miao is "+miao);

                            }
                            //读秒计数器完成之后需要直接跳转到首页
                            @Override
                            public void onFinish() {

                            }
                        };
                        countDownTimer.start();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        //移动事件发生后执行代码的区域
                        Log.d("jinyangyang+","MotionEvent.ACTION_MOVE ============ "+MotionEvent.ACTION_MOVE);
                        float moveY = event.getY();
                        Log.d("jinyangyang+","moveY ============ "+moveY);
                        int instance = (int) Math.abs((moveY - startY));
                        Log.d("jinyangyang+", "--action move--instance:"+instance);
                        if (moveY - startY > 0) {
                            //向下滑動
                            Log.d("jinyangyang+", "--action move--instance: UP");
                        }
                        if (moveY < 0) {
                            send = false;
                        } else {
                            send = true;
                        }
                        if(Integer.parseInt(miao)<=0){
                            if (mRecorder != null && soundFile.exists()) {
                                // 停止录音
                                mRecorder.stop();
                                // 释放资源
                                mRecorder.release();
                                mRecorder = null;
                                Log.d("jinyangyang+","soundFile2 ++++++++++++ "+soundFile);
                                overtime = true;
                            }
                            countDownTimer.cancel();
                            //ToastUtil.show(App.getInstance(), "您好，最长可以录制60秒哦！");
                            bin.changeStateText.setText("長按錄製，語音評論");
                            toUploadFile(soundFile.getPath());
                            break;
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d("jinyangyang+","MotionEvent.ACTION_UP ============ "+MotionEvent.ACTION_UP);
                        countDownTimer.cancel();
                        if (Integer.parseInt(miao)>=59) {
                            if(soundFile.exists()){
                                File file = new File(soundFile.getPath());
                                file.delete();
                            }
                            if(send){
                                ToastUtil.show(App.getInstance(), "录制时间太短");
                            }else {
                                ToastUtil.show(App.getInstance(), "上划取消发送");
                            }
                            break;
                        }else if(Integer.parseInt(miao)<=0){
                            //ToastUtil.show(App.getInstance(), "录制时间最长60秒，已经发送60秒之前的语音");
                            // 停止录音
                            //mRecorder.stop();
                            // 释放资源
//                            mRecorder.release();
//                            mRecorder = null;

//                            if (mRecorder != null && soundFile.exists()) {
//                                // 停止录音
//                                mRecorder.stop();
//                                // 释放资源
//                                mRecorder.release();
//                                mRecorder = null;
//                                Log.d("jinyangyang+","soundFile2 is "+soundFile);
//                                if (soundFile.exists()){
//                                    Log.d("jinyangyang+","   soundFile2 ============    "+soundFile);
//                                }
//                            }
//                            bin.changeStateText.setText("長按錄製，語音評論");
//                            toUploadFile(soundFile.getPath());

                            break;
                        }else {
                            if(send){
                                Log.d("jinyangyang+","send");
                                if (mRecorder != null && soundFile.exists()) {
                                    // 停止录音
                                    mRecorder.stop();
                                    // 释放资源
                                    mRecorder.release();
                                    mRecorder = null;
                                    Log.d("jinyangyang+","soundFile is "+soundFile);
                                    if (soundFile.exists()){
                                        Log.d("jinyangyang+","   soundFile ============    "+soundFile);
                                    }
                                }
                                bin.changeStateText.setText("長按錄製，語音評論");
                                if(overtime){
                                    //录制时间已经超时了
                                    Log.d("jinyangyang+","  overtime ============  "+overtime);
                                    overtime = false;
                                }else {
                                    toUploadFile(soundFile.getPath());
                                }
                            }else {
                                Log.d("jinyangyang+"," no send" +
                                        "");
                                ToastUtil.show(App.getInstance(), "上划，取消发送");
                                bin.changeStateText.setText("長按錄製，語音評論");
                                if(soundFile.exists()){
                                    File file = new File(soundFile.getPath());
                                    file.delete();
                                }

                            }
                        }
                        miao=MaxTime+"";
                        overtime = false;
                        break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_reviews;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecorder != null && soundFile.exists()) {
            // 停止录音
            mRecorder.stop();
            // 释放资源
            mRecorder.release();
            mRecorder = null;
        }
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if(mediaPlayerUrl!=null){
            mediaPlayerUrl.release();
            mediaPlayerUrl =null;
        }
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void showCommentState(String response) {
        finish();
    }

    @Override
    public void showVoliceCommentState(String response) {
        bin.voiceReleative.setVisibility(View.VISIBLE);
        bin.voiceCancel.setVisibility(View.VISIBLE);
        bin.voiceDesc.setVisibility(View.GONE);
        bin.voiceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayerUrl!=null){
                    mediaPlayerUrl.stop();
                    mediaPlayerUrl.release();
                    mediaPlayerUrl = null;
                    bin.voiceReleative.setVisibility(View.GONE);
                    bin.voiceCancel.setVisibility(View.GONE);
                    bin.voiceDesc.setVisibility(View.VISIBLE);
                    bin.voiceState.setVisibility(View.VISIBLE);
                    bin.voiceStateGif.setVisibility(View.GONE);
                }
            }
        });
        bin.voiceReleative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(mediaPlayerUrl!=null){
                        if(mediaPlayerUrl.isPlaying()){
                            Log.d("jinyangyang+","mediaPlayerUrl isPlaying");
                            mediaPlayerUrl.stop();
                            bin.voiceState.setVisibility(View.VISIBLE);
                            bin.voiceStateGif.setVisibility(View.GONE);
                            return;
                        }
                    }
                    mediaPlayerUrl = new MediaPlayer();
                    mediaPlayerUrl.setDataSource(userHeaderUrl);
                    mediaPlayerUrl.prepare();
                    mediaPlayerUrl.start();
                    bin.voiceState.setVisibility(View.GONE);
                    bin.voiceStateGif.setVisibility(View.VISIBLE);
                    mediaPlayerUrl.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            //当网络流文件播放完成时候调用的方法
                            //ToastUtil.show(App.getInstance(), "语音文件播放结束");
                            bin.voiceState.setVisibility(View.VISIBLE);
                            bin.voiceStateGif.setVisibility(View.GONE);
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onUploadDone(int responseCode, String message) {
        Log.d("jinyangyang+","onUploadDone be called");
        Log.d("jinyangyang+","message ============ "+message);
        Message msg = Message.obtain();
        msg.what = TO_UPLOAD_FILE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Log.d("jinyangyang+","  ============ uploadSize "+uploadSize);
    }

    @Override
    public void initUpload(int fileSize) {

    }

    private void toUploadFile(String imgFile) {
        String fileKey = "imgFile";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(this);
        //设置监听器监听上传状态
        Map<String, String> params = new HashMap<String, String>();
        params.put("methodName", "123456");
        Log.d("jinyangyang+","imgFile ========== "+imgFile);
        Log.d("jinyangyang+","fileKey ========== "+fileKey);


        uploadUtil.uploadFile(imgFile, fileKey, requestURL, params);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    String result = (String) msg.obj;
                    Log.d("jinyangyang+","  result ++++++++++++ "+result);
                    if(result == null || result.equals(null)){

                    }else{
                        //此处为上传头像返回的数据，可进行UI处理
                        try {
                            Log.d("jinyangyang+","上传语音文件成功！");
                            JSONObject jsonObject = new JSONObject(result);
                            String messageId = jsonObject.optString("messageId");
                            Log.d("jinyangyang+","messageId ======== "+messageId);
                            if(messageId.equals("200")){
                                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                                userHeaderUrl = jsonObject1.optString("userPortrait");
                                Log.d("jinyangyang+","userHeaderUrl ======== "+userHeaderUrl);
                                try {
                                    //播放SD卡中的视频文件
                                    String sdCard=Environment.getExternalStorageDirectory().getPath();
                                    Log.d("jinyangyang+"," ====sdCard ============"+sdCard);
                                    mediaPlayer = null;
                                    mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setDataSource(soundFile.getPath());
                                    mediaPlayer.prepare();//准备播放
                                    //mediaPlayer.start();//播放
                                    //获取时长可以在不播放的情况之下进行，也可以在播放的时候获取
                                    int playerLength = mediaPlayer.getDuration();
                                    bin.voiceLength.setText(""+TimeHMSSwitchUtil.getTime(playerLength/1000)+"'");
                                    Log.d("jinyangyang+","playerLength ============ "+playerLength);
                                    Log.d("jinyangyang+","playerSoundUrl ++++++++++++ "+userHeaderUrl);
                                    if(soundFile.exists()){
                                        File file = new File(soundFile.getPath());
                                        file.delete();
                                    }
                                    mediaPlayerUrl = null;
                                    mediaPlayerUrl = new MediaPlayer();
                                    mediaPlayerUrl.setDataSource(userHeaderUrl);
                                    mediaPlayerUrl.prepare();
                                    //mediaPlayerUrl.start();
                                    double dplayerLength = playerLength/1000;
                                    RatingBar ratingBar = (RatingBar)bin.rb;
                                    float selectedNum = ratingBar.getmSelectedNumber();
                                    selectedNumInt = (int)selectedNum;
                                    Log.d("selectedNumInt ======" , selectedNumInt+"");
                                    comment = bin.commentsEdit.getText().toString();
                                    if(selectedNumInt == 0){
                                        ToastUtil.show(mContext, "请选择星星");
                                        return;
                                    }

                                    presenter.callVoiceComments("1", SPUtils.get(App.getInstance(), "id",""), JoinId, comment, selectedNumInt, userHeaderUrl, dplayerLength);
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }else{
                                //ToastUtil.show(App.getInstance(), "更新用户信息失败！");
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    //Android6.0以上需要手动添加申请的权限
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
        }
    }

    private void requestPermissions() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, permissions, 321);
            }
        }
    }

}
