package com.wanpiao.master.mvp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.wanpiao.master.App;
import com.wanpiao.master.Constants;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityMyInformationBinding;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.glide.GlideApp;
import com.wanpiao.master.mvp.contract.MyInformationContract;
import com.wanpiao.master.mvp.presenter.MyInformationPresenter;
import com.wanpiao.master.utils.L;
import com.wanpiao.master.utils.SDCardUtil;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.SoftKeyboardControl;
import com.wanpiao.master.utils.ToastUtil;
import com.wanpiao.master.utils.UploadUtil;
import com.wanpiao.master.widgets.popup_windows.SexPopup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Description: #个人资料
 * #0000      @Author: tianxiao     2018/10/23      onCreate
 */
public class MyInformationActivity extends BaseActivity<MyInformationPresenter, ActivityMyInformationBinding>
        implements MyInformationContract.View , UploadUtil.OnUploadProcessListener {


    /** 	 * 去上传文件 	 */
    protected static final int TO_UPLOAD_FILE = 1;
    /** 	 * 上传文件响应 	 */
    protected static final int UPLOAD_FILE_DONE = 2;
    // 	/** 	 * 选择文件 	 */
    public static final int TO_SELECT_PHOTO = 3;
    /** 	 * 上传初始化 	 */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /** 	 * 上传中 	 */
    private static final int UPLOAD_IN_PROCESS = 5;
    /*** 	 * 这里的这个URL是我服务器的javaEE环境URL 	 */
    //private static String requestURL = "http://192.168.20.14:8081/hongkongmovie-mobile/user/rest/userUpload";
    private static String requestURL = "https://hk.enjoytickets.com/hongkongmovie-mobile/user/rest/userUpload";

    public static void startMyInformationActivity(Context context) {
        Intent intent = new Intent(context, MyInformationActivity.class);
        context.startActivity(intent);
    }

    private String imgFile="";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(MyInformationActivity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        bin.heardImageView.setOnClickListener(v -> {
            verifyStoragePermissions(this);
            openPictureSelector();
        });


        bin.ivBlack.setOnClickListener(v -> {
            this.finish();
        });
//        bin.llSex.setOnClickListener(v -> {
//            SexPopup.newInstance(this)
//                    .setSelelctClick(sex -> {
//                        bin.tvSex.setText(sex);
//                    }).showPopupWindow(v);
//        });

        bin.llSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftKeyboardControl.closeInputMethod(mContext, bin.userName);
                showPopwindow();
            }
        });


        bin.saveUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用上传头像接口
                L.i("imgFile++++++++++++", imgFile);
                //presenter.uploadHeaderImg(imgFile);
                //uploadImage("", imgFile);
                bin.uploadLoading.setVisibility(View.VISIBLE);
                if(imgFile.equals("")){
                    String userId = SPUtils.get(App.getInstance(), "id","");
                    String userName = bin.userName.getText().toString();
                    String userSex = "";
                    if(bin.tvSex.getText().toString().equals("男")){
                        userSex = "1";
                    }else if(bin.tvSex.getText().toString().equals("女")){
                        userSex = "0";
                    }
                    String userPortrait = SPUtils.get(App.getInstance(), "userPortrait", "");
                    presenter.updateUserInfo(userId, userName, userSex, userPortrait);
                }else {
                    toUploadFile();
                }
            }
        });


        String userPortrait = SPUtils.get(App.getInstance(), "userPortrait", "");
        String userName = SPUtils.get(App.getInstance(), "userName", "");
        String userSex = SPUtils.get(App.getInstance(), "userSex","");
        if(!userPortrait.equals("")){
            User.showIcon(bin.heardImageView, userPortrait);
        }
        if(!userName.equals("")){
            bin.userName.setText(userName);
        }
        if(userSex.equals("1")){
            bin.tvSex.setText("男");
        }else {
            bin.tvSex.setText("女");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : localMedia) {
                        if(media.getCompressPath() instanceof String){
                            Log.d("jinyangyang+"," media.getPath() type is String");
                        }
                        L.i("图片-----》", media.getCompressPath());
                        imgFile = media.getCompressPath();
                        L.i("imgFile-----》", imgFile);
                        if (media.isCut()) {
                            GlideApp.with(this).load(
                                    media.getCompressPath())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(bin.heardImageView);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 启动图片选择
     */
    public void openPictureSelector() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(MyInformationActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                //.maxSelectNum(maxSelectNum)// 最大图片选择数量
                //.minSelectNum(1)// 最小选择数量
                //.imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                //.previewVideo(cb_preview_video.isChecked())// 是否可预览视频
                //.enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath(getPath())// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                //.isGif(cb_isGif.isChecked())// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                //.circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                //.openClickSound(cb_voice.isChecked())// 是否开启点击声音
                // .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(300)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private String getPath() {
        String path = SDCardUtil.getSDCardPath() + "/images/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }


    // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
    public void scavengingCaching() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(MyInformationActivity.this);
                } else {
                    Toast.makeText(MyInformationActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_information;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onDestroy() {
        scavengingCaching();
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showUploadHeaderData(String data) {
        Log.d("jinyangyang+","showUploadHeaderData data ========= "+data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            String messageId = jsonObject.optString("messageId");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void upDateUserInfo(String data) {
        try{
            JSONObject jsonObject = new JSONObject(data);
            String messageId = jsonObject.optString("messageId");
            if(messageId.equals("200")){
                bin.uploadLoading.setVisibility(View.GONE);
                ToastUtil.show(App.getInstance(), "更新用户头像和用户信息成功！");
                presenter.requestLanding(this, SPUtils.get(App.getInstance(), "userAccount",""), SPUtils.get(App.getInstance(), "userPassword",""));
            }
        }catch (JSONException e){

        }
    }

    @Override
    public void landingSuccess(String s) {

    }


    public static void uploadImage(String url, String imagePath) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("jinyangyang+","uploadImage be called");
                try{
                    String uploadUrl = "http://192.168.20.14:8081/hongkongmovie-mobile/user/rest/userUpload";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Log.d("imagePath ======== ", imagePath);
                    File file = new File(imagePath);
                    Log.d("jinyangyang+","file is "+file);
                    RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("imgFile", imagePath, image)
                            .addFormDataPart("methodName","123456")
                            .build();
                    Request request = new Request.Builder()
                            .url(uploadUrl)
                            .post(requestBody)
                            //.addHeader("token",SPUtils.get(App.getInstance(), "token",""))
                            .build();
                    //Response response = okHttpClient.newCall(request).execute();
                    Response response = okHttpClient.newCall(request).execute();
                    Log.d("jinyangyang+","back is "+response.body().string());
                    JSONObject jsonObject = new JSONObject(response.body().string());

                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
        //return jsonObject.optString("image");
    }
    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = TO_UPLOAD_FILE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
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
                            Log.d("jinyangyang+","上传用户头像成功！");
                            JSONObject jsonObject = new JSONObject(result);
                            String messageId = jsonObject.optString("messageId");
                            Log.d("jinyangyang+","messageId ======== "+messageId);
                            if(messageId.equals("200")){
                                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                                String userHeaderUrl = jsonObject1.optString("userPortrait");
                                Log.d("jinyangyang+","userHeaderUrl ======== "+userHeaderUrl);
                                ImageView imageView = bin.heardImageView;
                                User.showIcon(imageView, userHeaderUrl);
                                //这里需要更新用户信息接口
                                String userId = SPUtils.get(App.getInstance(), "id","");
                                String userName = bin.userName.getText().toString();
                                String userSex = "";
                                if(bin.tvSex.getText().toString().equals("男")){
                                    userSex = "1";
                                }else if(bin.tvSex.getText().toString().equals("女")){
                                    userSex = "0";
                                }
                                String userPortrait = userHeaderUrl;
                                presenter.updateUserInfo(userId, userName, userSex, userPortrait);
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

    private void toUploadFile() {
        String fileKey = "imgFile";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(this);
        //设置监听器监听上传状态
        Map<String, String> params = new HashMap<String, String>();
        params.put("methodName", "123456");
        uploadUtil.uploadFile(imgFile, fileKey, requestURL, params);
    }

    @Override
    public void onUploadProcess(int uploadSize) {

    }

    @Override
    public void initUpload(int fileSize) {

    }

    private void showPopwindow() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.popu_select, null);

        TextView tv_man = (TextView) popView.findViewById(R.id.tv_man);
        TextView tv_woman = (TextView) popView.findViewById(R.id.tv_woman);
        TextView tv_cancel = (TextView) popView.findViewById(R.id.tv_cancel);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView,width,height);
        //popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setBackgroundDrawable(new BitmapDrawable());

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);// 设置允许在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_man:
                        bin.tvSex.setText(tv_man.getText().toString());
                        break;
                    case R.id.tv_woman:
                        bin.tvSex.setText(tv_woman.getText().toString());
                        break;
                    case R.id.tv_cancel:

                        break;
                }
                popWindow.dismiss();
            }
        };
        tv_man.setOnClickListener(listener);
        tv_woman.setOnClickListener(listener);
        tv_cancel.setOnClickListener(listener);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

}
