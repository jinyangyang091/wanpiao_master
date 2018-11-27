package com.wanpiao.master.retrofit;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.utils.L;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 描述: 在使用Retrofit过程中，通过服务器获取的数据，不一定是标准的json数据，
 * 可ToStringConverterFactory以把数据String直接获取到而不是解析好的数据
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-06-07 14:04
 */

public class ToStringConverterFactory extends Converter.Factory {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeToken<?> typeToken = TypeToken.get(type);
        Class<?> rawType = typeToken.getRawType();
        Log.i("EmptyString2Obj","type::::"+type);
        Class innerClazz = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        Log.i("innerClazz",innerClazz.getName());
        if (String.class.equals(type)) {
            return (Converter<ResponseBody, String>) value -> {
                Log.i("ToStringConverter","data : "+value.string());
                return value.string();
            };
        }
        //如果是用的BaseEntity<String>
        if (rawType.equals(BaseEntity.class)&&"java.lang.String".equals(innerClazz.getName())) {
            L.i("into ToStringConverter");
            return new ToStringConverter<>();
        }
        return null;
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return (Converter<String, RequestBody>) value -> RequestBody.create(MEDIA_TYPE, value);
        }
        return null;
    }

}
