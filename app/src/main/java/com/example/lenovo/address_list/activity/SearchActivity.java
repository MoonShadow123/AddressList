package com.example.lenovo.address_list.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.adapter.SearchAdapter;
import com.example.lenovo.address_list.bean.Person;
import com.example.lenovo.address_list.fragment.Blank_Fragment;
import com.example.lenovo.address_list.util.EventUtil;
import com.example.lenovo.address_list.util.PermissionUtil;
import com.example.lenovo.address_list.util.SearchUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.address_list.util.PermissionUtil.dialog;

/**
 * @author : PengLiang
 * Time : 2019/9/2
 * Description : 查找内容和筛选内容
 */
public class SearchActivity extends AppCompatActivity {
    private DrawerLayout mDrawerlayout;
    private FloatingActionButton mSearchFb;
    private EditText mSearchEt;
    private Blank_Fragment blank_fragment;
    public List<Person> list;
    public List<Person> listData;
    public SearchAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private NavigationView navView;
    // 系统跳转识别码
    final int MSG = 20;
    final int TEL = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
        initViews();
        setViews();
        setEvents();
    }

    private void initData() {
        if (listData != null && listData.size() > 0) {
            listData.clear();
            listData = null;
        }
        list = new ArrayList<>();
        listData = new ArrayList<>();
        list = Person.getList();
        listData = Person.getList();
    }

    private void initViews() {
        navView = findViewById(R.id.nav_search_nav);
        mRecyclerView = findViewById(R.id.middle_frg_recycler);
        toolbar = findViewById(R.id.tb_search_tb);
        mDrawerlayout = findViewById(R.id.dl_search_dl);
        mSearchEt = findViewById(R.id.et_search_et);
        mSearchFb = findViewById(R.id.fb_search_fb);
        blank_fragment = new Blank_Fragment();
        mAdapter = new SearchAdapter(listData, this);
    }


    private void setViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        //设置标题栏为自定义的标题栏
        setSupportActionBar(toolbar);
        //得到标题栏的实例
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置导航按钮的图标
            actionBar.setHomeAsUpIndicator(R.drawable.title_head_69);
        }
    }

    private void setEvents() {
        mSearchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData = Person.getList();
                mAdapter.notifyDataSetChanged();
            }
        });

        //接收内容后查找
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String data = mSearchEt.getText().toString().trim();
                SearchUtil.search(data, list, listData, mAdapter);
            }
        });

        EventUtil.setNavViewEventAfter(navView, mDrawerlayout, SearchActivity.this);
        EventUtil.setSearchFbEvent(SearchActivity.this, mSearchFb, mSearchEt, list, listData, mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerlayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    // 申请权限时回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                // 电话权限的回调
                PermissionUtil.onPhoneRequest(SearchActivity.this, grantResults);
                break;
            case 2:
                // 短信权限的回调
                PermissionUtil.onMsgRequest(SearchActivity.this, grantResults);
                break;
            default:
                break;
        }
    }

    // 进入系统设置后检查权限是否已经获得的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEL) {
            // 检查电话权限是否获取
            PermissionUtil.checkPhonePermission(dialog, SearchActivity.this, TEL);
        }
        // 检查短信权限是否获取
        if (requestCode == MSG) {
            PermissionUtil.checkMsgPermission(SearchActivity.this);
        }
    }


}
