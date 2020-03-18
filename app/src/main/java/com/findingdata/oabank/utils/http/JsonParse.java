package com.findingdata.oabank.utils.http;

import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.ProjectNoteEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Loong on 2019/11/21.
 * Version: 1.0
 * Describe: JSON解析
 */
public class JsonParse {
    public static BaseEntity parse(String json){
        return new Gson().fromJson(json, BaseEntity.class);
    }

    public static <T> BaseEntity<T> parse( String json, Class<T> clazz){
        Type type = new ParameterizedTypeImpl(BaseEntity.class, new Class[]{clazz});
        return new Gson().fromJson(json, type);
    }
    public static <T> BaseEntity<T> parseList( String json,Type type){
        BaseEntity baseEntity=new BaseEntity();
        try {
            JSONObject jsonObject=new JSONObject(json);
            baseEntity.setStatus(jsonObject.getBoolean("Status"));
            baseEntity.setMessage(jsonObject.getString("Message"));
            Gson gson=new Gson();
            List<ProjectNoteEntity> list=gson.fromJson(jsonObject.getJSONArray("Result").toString(),type);
            baseEntity.setResult(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return baseEntity;
    }
}
