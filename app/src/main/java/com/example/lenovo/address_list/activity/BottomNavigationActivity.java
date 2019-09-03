package com.example.lenovo.address_list.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.fragment.Fragment_AllCalls;
import com.example.lenovo.address_list.fragment.Fragment_Contact;
import com.example.lenovo.address_list.fragment.Fragment_DataAnalysis;

public class BottomNavigationActivity extends AppCompatActivity {

    private ActionBar actionBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        // 导航按钮的点击事件
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    actionBar.setTitle("常联系");
                    replaceFragment(new Fragment_Contact());
                    return true;
                case R.id.navigation_dashboard:
                    actionBar.setTitle("全部通话");
                    replaceFragment(new Fragment_AllCalls());
                    return true;
                case R.id.navigation_notifications:
                    actionBar.setTitle("数据分析");
                    replaceFragment(new Fragment_DataAnalysis());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        // 将常联系的碎片显示出来
        replaceFragment(new Fragment_Contact());
        //初始化组件
        initViews();
    }

    private void initViews() {
        //标题栏对象
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar1);
        //设置标题栏为自定义的标题栏
        setSupportActionBar(toolbar);
        //得到标题栏的实例
        actionBar = getSupportActionBar();
        actionBar.setTitle("常联系");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    // 动态添加碎片
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_layout_frame, fragment);
        transaction.commit();
    }


}
