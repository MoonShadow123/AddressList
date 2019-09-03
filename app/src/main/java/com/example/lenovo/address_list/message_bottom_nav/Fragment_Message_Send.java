package com.example.lenovo.address_list.message_bottom_nav;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.bean.SmsContent;
import com.example.lenovo.address_list.bean.SmsInfo;

import java.util.List;

public class Fragment_Message_Send extends Fragment {
    private RecyclerView recyclerView;
    private Message_MyAdapter myAdapter;
    /**
     * 所有的短信
     */
    public static final String SMS_URI_ALL = "content://sms/";
    /**
     * 收件箱短信
     */
    public static final String SMS_URI_INBOX = "content://sms/inbox";
    /**
     * 发件箱短信
     */
    public static final String SMS_URI_SEND = "content://sms/sent";
    /**
     * 草稿箱短信
     */
    public static final String SMS_URI_DRAFT = "content://sms/draft";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerView = view.findViewById(R.id.frg_contact_rcy);
        Uri uri = Uri.parse(SMS_URI_SEND);
        List<SmsInfo> sms_list = new SmsContent().getSmsInfo(getContext(),uri);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new Message_MyAdapter(getContext(), sms_list, new Message_MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int postion) {
                Toast.makeText(getContext(), "你好"+postion, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(myAdapter);
        return view;

    }
}
