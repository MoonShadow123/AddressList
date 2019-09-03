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

public class Fragment_AllCalls extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allcalls, container, false);
        recyclerView = view.findViewById(R.id.frag_allcalls_rcy);
        List<CallInfo> list = CallInfoService.getCallInfos(getContext());

        List<CallInfo> list_replace = new ArrayList<>();
        //改变原来的顺序
        for (int i = list.size()-1; i>0; i--) {
            CallInfo callInfo = list.get(i);
            list_replace.add(callInfo);
        }


        myAdapter = new MyAdapter(getContext(), list_replace);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
        return view;
    }
}
