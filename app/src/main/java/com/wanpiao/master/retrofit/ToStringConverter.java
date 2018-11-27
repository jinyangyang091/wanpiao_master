package com.wanpiao.master.retrofit;


import android.util.Log;

import com.wanpiao.master.common.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 描述: dada
 * ------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-01-12 18:37
 */
public class ToStringConverter<T> implements Converter<ResponseBody,T> {
    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        try {

//            "methodName": null,
//                    "messageId": 200,
//                    "message": "操作成功!",
//                    "timestamp": "2018-10-24T12:25:09.961+0000",
//                    "data": {
//
//            }

            //Log.d("jinyangyang+","json is "+ json);
            JSONObject jsonObject = new JSONObject(json);

            Log.d("jinyangyang+"," convert  jsonObject  ============  "+jsonObject);
            String data = jsonObject.getString("data");
            //String newData = "1"+data+"1";
            BaseEntity<String> entity =new BaseEntity<>();
            //entity.setToken(jsonObject.getString("token"));
            //entity.setToken2(jsonObject.getString("data"));
            entity.setMessageId(jsonObject.getString("messageId"));
            //entity.setMessageId("1403");
            //entity.setError(jsonObject.getString("error"));
            entity.setTimestamp(jsonObject.getString("timestamp"));
            entity.setMessage(jsonObject.getString("message"));
            entity.setData(data);
            entity.setAllData(jsonObject.toString());
            //Log.d("jinyangyang +","allData is "+entity.getAllData());
            /*L.i("newData",newData);
            String newJson = json.replace(data, newData);
            L.i("newJson",newJson);*/
            return (T) entity;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
