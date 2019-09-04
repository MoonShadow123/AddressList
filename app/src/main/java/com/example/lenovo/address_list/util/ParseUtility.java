package com.example.lenovo.address_list.util;

import android.text.TextUtils;

import com.example.lenovo.address_list.bean.Gson_Person;
import com.example.lenovo.address_list.db.Person;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author : PengLiang
 * Time : 2019/9/4
 * Description : 解析服务器返回的数据
 */
public class ParseUtility {

    public static boolean handleResponse(String response) {

            if (!TextUtils.isEmpty(response)) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject personJsonObject = jsonArray.getJSONObject(i);
                        Person person = new Person();
                        person.setName(personJsonObject.getString("uname"));
                        person.setPhoneNumber(personJsonObject.getString("utel"));
                        person.setGroupNumber(personJsonObject.getString("ushorttel"));
                        person.setOfficeNumber(personJsonObject.getString("uteloffice"));
                        person.setCarNumber(personJsonObject.getString("ucar"));
                        person.setDepartment(personJsonObject.getString("upartment"));
                        person.setOther(personJsonObject.getString("uinfo"));
                        person.save();
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        return false;
    }
}
