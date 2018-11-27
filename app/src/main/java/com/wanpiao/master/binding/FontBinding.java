package com.wanpiao.master.binding;

import android.graphics.Typeface;

import com.wanpiao.master.App;
import com.wanpiao.master.cache.FontCache;

import androidx.databinding.BindingConversion;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/19      onCreate
 */
public class FontBinding {

    @BindingConversion
    public static Typeface convertStringToFace(String fontName) {
            try {
                return FontCache.getTypeface(fontName, App.getInstance());
            } catch (Exception e) {
                throw e;
            }
    }

}
