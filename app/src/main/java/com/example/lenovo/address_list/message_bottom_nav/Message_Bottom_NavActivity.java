package com.example.lenovo.address_list.message_bottom_nav;

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

public class Message_Bottom_NavActivity extends AppCompatActivity {
    private ActionBar actionBar;
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //切换到收信箱碎片
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    actionBar.setTitle("收信箱");
                    replaceFragment(new Fragment_Message_Contact());
                    return true;
                // 切换到全部短信碎片
                case R.id.navigation_dashboard:
                    actionBar.setTitle("全部短信");
                    replaceFragment(new Fragment_Message_All());
                    return true;
                // 切换到发信箱分析碎片
                case R.id.navigation_notifications:
                    replaceFragment(new Fragment_Message_Send());
                    actionBar.setTitle("发信箱");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new Fragment_Message_Contact());
        setContentView(R.layout.activity_message__bottom__nav);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
        actionBar.setTitle("收信箱");


    }


    // 动态添加碎片
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.message_bottom__frame, fragment);
        transaction.commit();
    }


}
