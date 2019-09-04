package com.example.lenovo.address_list.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author : PengLiang
 * Time : 2019/9/3
 * Description : 发送网络请求
 */
public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
