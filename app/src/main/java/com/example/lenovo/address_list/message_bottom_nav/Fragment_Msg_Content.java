package com.example.lenovo.address_list.message_bottom_nav;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.address_list.R;


public class Fragment_Msg_Content extends Fragment {
    private TextView mTv_body;
    private View view;

    // 得到一个带好参数的Fragment的实例
    public static Fragment_Msg_Content newInstance(String body) {
        Fragment_Msg_Content content = new Fragment_Msg_Content();
        Bundle bundle = new Bundle();
        bundle.putString("body", body);
        content.setArguments(bundle);
        return content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_msg_content, container, false);
        mTv_body = view.findViewById(R.id.frg_msg_content_body);
        // 设置显示为传为的参数
        mTv_body.setText(getArguments().getString("body"));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTv_body = view.findViewById(R.id.frg_msg_content_body);
        // 设置显示为传为的参数
        mTv_body.setText(getArguments().getString("body"));
    }
}
