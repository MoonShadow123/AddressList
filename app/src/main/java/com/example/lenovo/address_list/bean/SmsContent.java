package com.example.lenovo.address_list.bean;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;


public class SmsContent {
//    private Context mContext;
//    private Uri uri;
//    List<SmsInfo> infos;
//
//    public SmsContent(Context context, Uri uri) {
//        infos = new ArrayList<SmsInfo>();
//        this.mContext=context;
//        this.uri = uri;
//    }


    public List<SmsInfo> getSmsInfo(Context context,Uri uri) {
        List<SmsInfo> infos =new ArrayList<SmsInfo>();
        String[] projection = new String[]{"_id", "address", "person",
                "body", "date", "type"};
        ContentResolver resolver = context.getContentResolver();
        Cursor cusor = resolver.query(uri, projection, null, null,
                null);
        int nameColumn = cusor.getColumnIndex("person");
//        long dateColumn = cusor.getColumnIndex("date");
        int phoneNumberColumn = cusor.getColumnIndex("address");
        int smsbodyColumn = cusor.getColumnIndex("body");
        int typeColumn = cusor.getColumnIndex("type");

        if (cusor != null) {
            while (cusor.moveToNext()) {
                SmsInfo smsinfo = new SmsInfo();
                smsinfo.setName(cusor.getString(nameColumn));
                smsinfo.setDate(cusor.getLong(4));
                smsinfo.setPhoneNumber(cusor.getString(phoneNumberColumn));
                smsinfo.setSmsbody(cusor.getString(smsbodyColumn));
                smsinfo.setType(cusor.getString(typeColumn));
                infos.add(smsinfo);
            }
            cusor.close();
        }
        return infos;
    }
}
