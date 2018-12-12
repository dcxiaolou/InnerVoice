package com.android.dcxiaolou.innervoice.util;

/*
* 使用SharePreference将List存入本地
* */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ListDataSave {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("WrongConstant")
    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_APPEND);
        editor = preferences.edit();
    }

    /*
    * 保存list
    * */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (datalist == null || datalist.size() == 0)
            return ;
        //先转换成json数据，再保存
        Gson gson = new Gson();
        String strJson = gson.toJson(datalist);
        editor.putString(tag, strJson);
        editor.commit();
    }

    /*
    * 获取list
    * */
    public <T> List<T> getDataList(String tag) {
        List<T> datalist = new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (strJson == null) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {}.getType());
        return datalist;
    }

}
