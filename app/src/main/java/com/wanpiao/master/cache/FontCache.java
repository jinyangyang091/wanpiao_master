package com.wanpiao.master.cache;

import android.content.Context;
import android.graphics.Typeface;
import android.util.ArrayMap;

/**
 * @Description: #
 * 由于每次都要操作assests文件夹，也会带来一定的开销，所以也有必要提供一个字体缓存FontCache
 *
 * #0000      @Author: tianxiao     2018/10/19      onCreate
 *
 */
public class FontCache {

    private static ArrayMap<String, Typeface> fontCache = new ArrayMap<String, Typeface>();

    public static Typeface getTypeface(String fontName, Context context) {
        Typeface tf = fontCache.get(fontName);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), fontName);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(fontName, tf);
        }
        return tf;
    }

}
