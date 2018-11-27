package com.wanpiao.master.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivitySoundRecordBinding;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.contract.SoundRecordContract;
import com.wanpiao.master.mvp.presenter.SoundRecordPresenter;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;
import com.wanpiao.master.utils.UploadUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SoundRecordActivity extends BaseActivity<SoundRecordPresenter, ActivitySoundRecordBinding> implements SoundRecordContract.View ,UploadUtil.OnUploadProcessListener{

    private static String requestURL = "https://hk.enjoytickets.com/hongkongmovie-mobile/user/rest/userUpload";
    // 要申请的权限
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    // 系统的音频文件
    File soundFile;
    MediaRecorder mRecorder;
    MediaPlayer  mediaPlayer = new MediaPlayer();
    MediaPlayer  mediaPlayerUrl = new MediaPlayer();
    private static final int TO_UPLOAD_FILE = 12;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void startSoundRecordActivity(Context context) {
        Intent intent = new Intent(context, SoundRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        requestPermissions();
        bin.startSoundRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            ToastUtil.show(App.getInstance(), "SD卡不存在，请插入SD卡！");
                            break;
                        }else {
                            Log.d("jinyangyang+","SD卡存在");
                        }
                        try {
                            // 创建保存录音的音频文件
                            soundFile = new File(Environment.getExternalStorageDirectory() .getCanonicalFile() + "/wanpiao_sound_record.mp3");
                            mRecorder = new MediaRecorder();
                            // 设置录音的声音来源
                            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            // 设置录制的声音的输出格式（必须在设置声音编码格式之前设置）
                            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            // 设置声音编码格式
                            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                            mRecorder.setOutputFile(soundFile.getAbsolutePath());
                            mRecorder.prepare();
                            // 开始录音
                            mRecorder.start();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        bin.startSoundRecord.setText("松开 结束");
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        //移动事件发生后执行代码的区域
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
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
                        bin.startSoundRecord.setText("按住 说话");
                        toUploadFile(soundFile.getPath());
                        break;
                    }
                }
                return false;
            }
        });


        bin.startPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
////                    Uri playUri = getImageContentUri(SoundRecordActivity.mContext,soundFile);
////
////                    player.setDataSource(playUri);
//
//                    String sdCard=Environment.getExternalStorageDirectory().getPath();
//                    Log.d("jinyangyang+"," ====sdCard ============"+sdCard);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }

                try {

                    //播放网络音频文件
//                    String sdCard=Environment.getExternalStorageDirectory().getPath();
//                    Log.d("jinyangyang+"," ====sdCard ============"+sdCard);
//                    mediaPlayer.setDataSource("http://wanpiao-hk-test.oss-cn-shanghai.aliyuncs.com/11111.mp3");
//                    mediaPlayer.prepare();//准备播放
//                    mediaPlayer.start();//播放
//                    int playerLength = mediaPlayer.getDuration();
//                    Log.d("jinyangyang+","playerLength ============ "+playerLength);

                    //播放SD卡中的视频文件
                    String sdCard=Environment.getExternalStorageDirectory().getPath();
                    Log.d("jinyangyang+"," ====sdCard ============"+sdCard);
                    mediaPlayer.setDataSource(soundFile.getPath());
                    mediaPlayer.prepare();//准备播放
                    mediaPlayer.start();//播放
                    //获取时长可以在不播放的情况之下进行，也可以在播放的时候获取
                    int playerLength = mediaPlayer.getDuration();
                    Log.d("jinyangyang+","playerLength ============ "+playerLength);
                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    soundFile = new File(Environment.getExternalStorageDirectory() .getCanonicalFile() + "/wanpiao_sound_record.mp3");
                    if(soundFile.exists()){
                        Log.d("jinyangyang+","soundFile ++++++++++++ "+soundFile);
                        String sdCard=Environment.getExternalStorageDirectory().getPath();
                        Log.d("jinyangyang+"," ====sdCard ============"+sdCard);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                // mediaPlayer.setDataSource(soundFile);
            }
        });
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_sound_record;
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
        }
        if(mediaPlayerUrl!=null){
            mediaPlayerUrl.release();
        }
    }

    private void toUploadFile(String imgFile) {
        String fileKey = "imgFile";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(this);
        //设置监听器监听上传状态
        Map<String, String> params = new HashMap<String, String>();
        params.put("methodName", "123456");
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
                                String userHeaderUrl = jsonObject1.optString("userPortrait");
                                Log.d("jinyangyang+","userHeaderUrl ======== "+userHeaderUrl);
                                try {
                                    //播放SD卡中的视频文件
                                    String sdCard=Environment.getExternalStorageDirectory().getPath();
                                    Log.d("jinyangyang+"," ====sdCard ============"+sdCard);
                                    mediaPlayer.setDataSource(soundFile.getPath());
                                    mediaPlayer.prepare();//准备播放
                                    //mediaPlayer.start();//播放
                                    //获取时长可以在不播放的情况之下进行，也可以在播放的时候获取
                                    int playerLength = mediaPlayer.getDuration();
                                    Log.d("jinyangyang+","playerLength ============ "+playerLength);

                                    Log.d("jinyangyang+","playerSoundUrl ======== "+userHeaderUrl);
                                    mediaPlayerUrl.setDataSource(userHeaderUrl);
                                    mediaPlayerUrl.prepare();
                                    mediaPlayerUrl.start();


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
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = TO_UPLOAD_FILE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {

    }

    @Override
    public void initUpload(int fileSize) {

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
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
