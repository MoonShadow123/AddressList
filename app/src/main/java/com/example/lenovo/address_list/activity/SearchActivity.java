package com.example.lenovo.address_list.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.example.lenovo.address_list.db.Person;

import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.adapter.SearchAdapter;
import com.example.lenovo.address_list.fragment.Blank_Fragment;
import com.example.lenovo.address_list.message_bottom_nav.Message_Bottom_NavActivity;
import com.example.lenovo.address_list.util.DialogUtil;
import com.example.lenovo.address_list.util.EventUtil;
import com.example.lenovo.address_list.util.HttpUtil;
import com.example.lenovo.address_list.util.ParseUtility;
import com.example.lenovo.address_list.util.PermissionUtil;
import com.example.lenovo.address_list.util.SearchUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    public List<com.example.lenovo.address_list.db.Person> list;
    public List<com.example.lenovo.address_list.db.Person> listData;
    public SearchAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private NavigationView navView;
    private static ProgressDialog progressDialog;
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
        // 如果数据库中有数据就加载数据库中的数据
        List<Person> list2 = DataSupport.findAll(Person.class);
        if (list2.size() > 0) {
            list.addAll(list2);
            listData.addAll(list2);
        }

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
                listData.addAll(list);
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

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //点击修改数据菜单
                    case R.id.nav_camera:
                        Toast.makeText(SearchActivity.this, "不好意思，本功能暂未开放", Toast.LENGTH_SHORT).show();
                        mDrawerlayout.closeDrawers();
                        return true;
                    //点击上传保存菜单
                    case R.id.nav_gallery:
                        Toast.makeText(SearchActivity.this, "不好意思，本功能暂未开放", Toast.LENGTH_SHORT).show();
                        mDrawerlayout.closeDrawers();
                        return true;
                    //点击同步更新菜单
                    case R.id.nav_slideshow:
                        syncUpdateAlertDialog();
                        mDrawerlayout.closeDrawers();
                        return true;
                    //点击系统设置菜单
                    case R.id.nav_manage:
                        Toast.makeText(SearchActivity.this, "不好意思，本功能暂未开放", Toast.LENGTH_SHORT).show();
                        mDrawerlayout.closeDrawers();
                        return true;
                    // 点击通话记录菜单
                    case R.id.nav_share:
                        // 如果没有读取通话记录的权限
                        if (ContextCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                            //申请读取通话记录的权限，1是请求码，只要是确定值就可以了
                            ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                        }
                        //获得了读取通话记录的权限
                        else {
                            // 跳转到通话记录界面
                            Intent intent = new Intent(SearchActivity.this, BottomNavigationActivity.class);
                            SearchActivity.this.startActivity(intent);
                        }
                        return true;
                    // 点击短信记录菜单
                    case R.id.nav_send:
                        // 如果没有读取通话记录的权限
                        if (ContextCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                            //申请读取通话记录的权限，1是请求码，只要是确定值就可以了
                            ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.READ_SMS}, 2);
                        }
                        //获得了读取通话记录的权限
                        else {
                            // 跳转到短信记录界面
                            Intent intent = new Intent(SearchActivity.this, Message_Bottom_NavActivity.class);
                            SearchActivity.this.startActivity(intent);
                        }
                        return true;
                }
                return false;
            }
        });

        // 滑动菜单的监听
//        EventUtil.setNavViewEventAfter(navView, mDrawerlayout, SearchActivity.this, mAdapter, listData);
        // 悬浮按钮的监听
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


    // 同步更新提示框
    public void syncUpdateAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
        builder.setTitle("更新数据");
        builder.setMessage("注意：此操作需要联网，请确保手机能够正常访问网络数据。" + "\n" + "系统将下载服务器上的最新数据，如果下载成功，则会清除本软件的历史数据后再写入最新数据，下载失败不会影响老数据的使用。" + "\n" + "不要重复点击此按钮，请耐心等待网络数据下载结束。");
        builder.setPositiveButton("开始下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = new ProgressDialog(SearchActivity.this);
                progressDialog.setTitle("正在更新数据");
                progressDialog.setMessage("更新中");
                progressDialog.setCancelable(false);
                progressDialog.show();
                List<com.example.lenovo.address_list.db.Person> list = DataSupport.findAll(com.example.lenovo.address_list.db.Person.class);

                if (list.size() > 0) {
                    listData.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    Toast.makeText(SearchActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    String address = "http://120.76.210.221/DzcmMailList/GetUser";
                    HttpUtil.sendOkHttpRequest(address, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(SearchActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();
                            boolean result = false;
                            result = ParseUtility.handleResponse(responseText);
                            if (result) {
                                if (progressDialog != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                        }
                                    });
                                    List<com.example.lenovo.address_list.db.Person> list2 = DataSupport.findAll(com.example.lenovo.address_list.db.Person.class);
                                    listData.addAll(list2);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mAdapter.notifyDataSetChanged();
                                            Toast.makeText(SearchActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }


                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(SearchActivity.this, "json数据处理失败", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });
                }


            }
        });
        builder.setNegativeButton("暂不下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


}
