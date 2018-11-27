package com.wanpiao.master.mvp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.idst.util.NlsClient;
import com.alibaba.idst.util.SpeechRecognizer;
import com.alibaba.idst.util.SpeechRecognizerCallback;
import com.alibaba.idst.util.SpeechRecognizerWithRecorder;
import com.alibaba.idst.util.SpeechRecognizerWithRecorderCallback;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityAliyunvoice2Binding;
import com.wanpiao.master.databinding.ActivityAliyunvoiceBinding;
import com.wanpiao.master.mvp.contract.AliyunVoiceContract;
import com.wanpiao.master.mvp.presenter.AliyunVoicePresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.Call;

import static android.media.AudioRecord.STATE_UNINITIALIZED;


@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class AliyunVoiceActivity  extends BaseActivity<AliyunVoicePresenter, ActivityAliyunvoice2Binding> implements AliyunVoiceContract.View {

    // 要申请的权限
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    private static final String TAG="AliSpeechDemo";

    private Button button;
    private EditText mFullEdit;
    private EditText mResultEdit;
    // Demo录音线程
    private MyRecorder myRecorder;
    private NlsClient client;

    public static void startAliyunVoiceActivity(Context context) {
        Intent intent = new Intent(context, AliyunVoiceActivity.class);
        context.startActivity(intent);
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
    protected void initViews(Bundle savedInstanceState) {
        requestPermissions();
        button = (Button) findViewById(R.id.button);
        mFullEdit = (EditText) findViewById(R.id.editText2);
        mResultEdit = (EditText) findViewById(R.id.editText);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.d(TAG,"Button touching.");
                        startRecognizer(view);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        //移动事件发生后执行代码的区域
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        stopRecognizer(view);
                        break;
                    }
                }
                return false;
            }
        });

        //第一步，创建client实例，client只需要创建一次，可以用它多次创建recognizer
        client = new NlsClient();
        myRecorder = new MyRecorder();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_aliyunvoice2;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {
        String url = "https://hk.enjoytickets.com/hongkongmovie-mobile/user/rest/getAccessToken";
        //String url = "https://hk.enjoytickets.com/hongkongmovie-mobile/setting/rest/about";
        OkHttpUtils.get().url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("jinyangyang+","e is "+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("jinyangyang+","response is "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    @Override
    protected void onDestroy() {
        // 等待录音线程完全停止
        if (myRecorder.getStatus() != MyRecorder.STATUS_READY){
            myRecorder.stop();
            while(myRecorder.getStatus() != MyRecorder.STATUS_READY) {
                try {
                    Thread.sleep(50);
                    Log.d(TAG, "waiting until finished...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // 最终，退出时释放client
        client.release();
        super.onDestroy();
    }


    /**
     * 启动录音和识别
     * @param view
     */
    public void startRecognizer(View view){
        mFullEdit.setText("");
        mResultEdit.setText("");

        //UI在主线程更新
        Handler handler = new MyHandler(this);
        // 第二步，新建识别回调类
        SpeechRecognizerCallback callback = new MyCallback(handler);

        // 第三步，创建识别request
        SpeechRecognizer speechRecognizer = client.createRecognizerRequest(callback);

        // 第四步，设置相关参数
        // 请使用https://help.aliyun.com/document_detail/72153.html 动态生成token
        speechRecognizer.setToken("c5877f0e48934987b4a0ee4379577683");
        // 请使用阿里云语音服务管控台(https://nls-portal.console.aliyun.com/)生成您的appkey
        speechRecognizer.setAppkey("dJaX8MY8D3YYWhWt");
        // 设置返回中间结果，更多参数请参考官方文档
        speechRecognizer.enableIntermediateResult(true);
        speechRecognizer.enableVoiceDetection(true);
//        speechRecognizer.setMaxStartSilence(10000);
//        speechRecognizer.setMaxEndSilence(500);
        speechRecognizer.enableInverseTextNormalization(true);

        //启动录音线程
        myRecorder.recordTo(speechRecognizer);


    }

    /**
     * 停止录音和识别
     * @param view
     */
    public void stopRecognizer(View view){
        // 停止录音
        myRecorder.stop();
    }

    // 语音识别回调类，用户在这里得到语音识别结果，加入您自己的业务处理逻辑
    // 注意不要在回调方法里调用recognizer.stop()方法
    // 注意不要在回调方法里执行耗时操作
    private static class MyCallback implements SpeechRecognizerCallback {

        private Handler handler;

        public MyCallback(Handler handler) {
            this.handler = handler;
        }
        // 识别开始
        @Override
        public void onRecognizedStarted(String msg, int code)
        {
            Log.d(TAG,"OnRecognizedStarted " + msg + ": " + String.valueOf(code));
        }

        // 请求失败
        @Override
        public void onTaskFailed(String msg, int code)
        {
            Log.d(TAG,"OnTaskFailed " + msg + ": " + String.valueOf(code));
            handler.sendEmptyMessage(0);
        }

        // 识别返回中间结果，只有开启相关选项时才会回调
        @Override
        public void onRecognizedResultChanged(final String msg, int code)
        {
            Log.d(TAG,"OnRecognizedResultChanged " + msg + ": " + String.valueOf(code));
            Message message = new Message();
            message.obj = msg;
            handler.sendMessage(message);
        }

        // 第七步，识别结束，得到最终完整结果
        @Override
        public void onRecognizedCompleted(final String msg, int code)
        {
            Log.d(TAG,"OnRecognizedCompleted " + msg + ": " + String.valueOf(code));
            Message message = new Message();
            message.obj = msg;
            handler.sendMessage(message);
        }

        // 请求结束，关闭连接
        @Override
        public void onChannelClosed(String msg, int code) {

            Log.d(TAG, "OnChannelClosed " + msg + ": " + String.valueOf(code));
        }

    };

    // 根据识别结果更新界面的代码
    private static class MyHandler extends Handler {
        private final WeakReference<AliyunVoiceActivity> mActivity;

        public MyHandler(AliyunVoiceActivity activity) {
            mActivity = new WeakReference<AliyunVoiceActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.obj == null) {
                Log.i(TAG, "Empty message received.");
                return;
            }
            String fullResult = (String)msg.obj;
            String result = null;
            if (!fullResult.equals("")){
                //JSONObject jsonObject = JSONObject.parseObject(fullResult);
                try{
                    JSONObject jsonObject = new JSONObject(fullResult);
                    JSONObject payload = jsonObject.optJSONObject("payload");
                    result = payload.optString("result");
                    Log.d("jinyangyang+","fullResult ============ "+fullResult);
                    Log.d("jinyangyang+","result ============ "+result);
//                    if (jsonObject.containsKey("payload")){
//                        result = jsonObject.getJSONObject("payload").getString("result");
//                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            mActivity.get().mFullEdit.setText(fullResult);
            mActivity.get().mResultEdit.setText(result);
        }
    }

    // 录音并发送给识别的代码，客户可以直接使用
    private static class MyRecorder implements Runnable {
        // 录音的状态
        final static int STATUS_READY = 0;
        final static int STATUS_WORKING = 1;
        final static int STATUS_STOPPING = 7;
        final static int STATUS_FAILED = -1;

        final static int SAMPLE_RATE = 16000;
        final static int SAMPLES_PER_FRAME = 640;

        private AudioRecord mAudioRecorder;
        private SpeechRecognizer recognizer;
        private int status = STATUS_READY;
        private Thread thread;

        /**
         *
         * @param recognizer
         */
        public void recordTo(SpeechRecognizer recognizer){
            if (status != STATUS_READY) {
                throw new IllegalStateException("Current state is: " + status);
            }
            status = STATUS_WORKING;
            this.recognizer = recognizer;

            Log.d(TAG,"Init audio recorder");
            int bufferSizeInBytes = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            mAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes * 2);

            if (mAudioRecorder == null || mAudioRecorder.getState() == STATE_UNINITIALIZED) {
                throw new IllegalStateException("Failed to initialize AudioRecord!");
            }
            mAudioRecorder.startRecording();
            thread = new Thread(this);
            thread.start();
        }

        public int getStatus() {
            return status;
        }

        public void stop(){
            if (status == STATUS_WORKING) {
                status = STATUS_STOPPING;
            }
            while (status != STATUS_READY) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            try {
                // 第五步，启动语音识别
                int startCode = recognizer.start();
                if(startCode < 0)
                {
                    Log.i(TAG, "Failed to start recognizer");
                    recognizer.stop();
                    status = STATUS_FAILED;
                    return;
                }
                Log.d(TAG,"Recognizer started");

                ByteBuffer buf = ByteBuffer.allocateDirect(SAMPLES_PER_FRAME);
                while(status == STATUS_WORKING){
                    buf.clear();
                    // 采集语音
                    int readBytes = mAudioRecorder.read(buf, SAMPLES_PER_FRAME);
                    byte[] bytes = new byte[SAMPLES_PER_FRAME];
                    buf.get(bytes, 0, SAMPLES_PER_FRAME);
                    if (readBytes>0 && status == STATUS_WORKING){
                        // 第六步，发送语音数据到识别服务
                        int code = recognizer.sendAudio(bytes, bytes.length);
                        if (code < 0) {
                            Log.i(TAG, "Failed to send audio!");
                            status = STATUS_STOPPING;
                            break;
                        }
                    }
                    buf.position(readBytes);
                    buf.flip();
                }
                mAudioRecorder.stop();
                // 第八步，停止本次识别
                recognizer.stop();
                status = STATUS_READY;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }




}
