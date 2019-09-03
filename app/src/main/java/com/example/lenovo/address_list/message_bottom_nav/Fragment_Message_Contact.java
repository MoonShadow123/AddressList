package com.example.lenovo.address_list.message_bottom_nav;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.bean.SmsContent;
import com.example.lenovo.address_list.bean.SmsInfo;

import java.util.List;

public class Fragment_Message_Contact extends Fragment {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerView = view.findViewById(R.id.frg_contact_rcy);
        ;
        Uri uri = Uri.parse(SMS_URI_INBOX);
        final List<SmsInfo> sms_list = new SmsContent().getSmsInfo(getContext(), uri);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new Message_MyAdapter(getContext(), sms_list, new Message_MyAdapter.OnItemClickListener() {
            // 点击短信列表进入到短信内容界面
            @Override
            public void onClick(int postion) {
                // 这里调用了一个静态方法newInstance返回的是一个带了参数的Fragment对象，这里我们传入要设置的字符
                Fragment_Msg_Content content = Fragment_Msg_Content.newInstance(sms_list.get(postion).getSmsbody());
                // 切换fragment为上面得到的那个带参数的fragment,addToBackStack(null)为点击返回键时，返回的是另一个碎片，而不是直接退出Activity
                getFragmentManager().beginTransaction().replace(R.id.message_bottom__frame, content).addToBackStack(null).commitAllowingStateLoss();
            }
        });

        recyclerView.setAdapter(myAdapter);
        return view;
    }

    // 动态添加碎片
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.message_bottom__frame, fragment);
        transaction.commit();

    }
}
