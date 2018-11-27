package com.wanpiao.master.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wanpiao.master.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 描述: 解决后台返回data为空字符串时 替换成大括号 防止报错  修正json
 * --------------------------------------------
 * 工程:
 */
public class EmptyString2ObjConverter<T> implements Converter<ResponseBody,T> {
    Type type;

    public EmptyString2ObjConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        TypeToken<?> typeToken = TypeToken.get(type);
        Class<?> rawType = typeToken.getRawType();
        Class innerClazz = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
//        if (innerClazz == User.class){
//            L.i("type is user");
//        }else if(innerClazz == List.class){
//            L.i("type is List");
//        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.getString("data");
            boolean hasData = jsonObject.has("data");
            //if ("400".equals(jsonObject.getString("status"))){
                if (!hasData){
                    if (innerClazz != List.class){
                        jsonObject.put("data",new JSONObject("{}"));
                        L.i("jsonObject:"+jsonObject.toString());
                        return (T)new Gson().fromJson(jsonObject.toString(), this.type);
                    }else{
                        jsonObject.put("data",new JSONObject("[]"));
                        return (T)new Gson().fromJson(jsonObject.toString(), this.type);
                    }
                }else if("[]".equals(data.trim()) && innerClazz != List.class){
                    jsonObject.put("data",new JSONObject("{}"));
                    return (T)new Gson().fromJson(jsonObject.toString(), this.type);
                }else if("{}".equals(data.trim()) && innerClazz == List.class){
                    jsonObject.put("data",new JSONObject("[]"));
                    return (T)new Gson().fromJson(jsonObject.toString(), this.type);
                }
            //}
            return (T)new Gson().fromJson(json, this.type);
        } catch (JSONException e) {
            e.printStackTrace();
            if ("No value for data".equals(e.getMessage())){
                Log.i("jsonException","------ 没有data");
                return (T)new Gson().fromJson(json,type);
            }else {
                return null;
            }
        }
    }
}

