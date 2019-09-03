package com.example.lenovo.address_list.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.lenovo.address_list.R;

/**
 * @author : PengLiang
 * Time : 2019/9/2
 * Description : 主活动，显示一个背景
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBgIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除状态栏
        removeStatusBar();
        initViews();
    }

    private void initViews() {
        mBgIv = findViewById(R.id.iv_main_bg);
        mBgIv.setOnClickListener(this);
    }

    private void removeStatusBar() {
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_main_bg:
                //跳转到SearchActivity
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
