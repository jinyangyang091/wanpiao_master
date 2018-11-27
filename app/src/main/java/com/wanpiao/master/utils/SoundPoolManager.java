package com.wanpiao.master.utils;

import android.content.Context;
import android.media.SoundPool;
import android.os.Build;

import com.wanpiao.master.R;

import androidx.annotation.RequiresApi;


/**
 * 描述: SoundPoolManager
 * --------------------------------------------
 * 工程:
 * #0000     @author mwy     创建日期: 2018-05-21 15:14
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SoundPoolManager {
    private  SoundPool.Builder builder;
    private SoundPool soundPool;
    private static SoundPoolManager instance;
    private int fenDanSoundId = 0;
    /**
     * 初始化
     *
     * @param context
     */
    private SoundPoolManager(Context context) {
        builder = new SoundPool.Builder();
        builder.setMaxStreams(10);
        soundPool = builder.build();
        soundPool.setOnLoadCompleteListener((soundPool, i, i1) -> {
            L.i("soundPool声音加载完毕，"+i+":"+i1);
        });
        //fenDanSoundId = soundPool.load(context, R.raw.fendan_push_sound, 1);
    }

    /**
     * 播放声音
     *
     * @param
     */
    public void playFenDan() {
        soundPool.play(fenDanSoundId, 1, 1, 0, 0, 1);
    }

    public static SoundPoolManager getInstance(Context context){
        if (instance == null) {
            synchronized (SoundPoolManager.class){
                if (instance == null){
                    instance = new SoundPoolManager(context);
                }
            }
        }
        return instance;
    }
}
