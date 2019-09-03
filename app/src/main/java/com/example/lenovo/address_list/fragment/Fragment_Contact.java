package com.example.lenovo.address_list.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.adapter.MyAdapter;
import com.example.lenovo.address_list.bean.CallInfo;
import com.example.lenovo.address_list.bean.CallInfoService;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Contact extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerView = view.findViewById(R.id.frg_contact_rcy);
        List<CallInfo> list = CallInfoService.getCallInfos(getContext());

        List<CallInfo> list_two = new ArrayList<>();
        //取出最后10个
        for (int i = list.size() - 1; i > list.size() - 8; i--) {
            CallInfo callInfo = list.get(i);
            list_two.add(callInfo);
        }

        myAdapter = new MyAdapter(getContext(), list_two);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);

        return view;
    }
}
