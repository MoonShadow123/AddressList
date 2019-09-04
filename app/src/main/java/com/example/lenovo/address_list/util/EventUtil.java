package com.example.lenovo.address_list.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.activity.BottomNavigationActivity;
import com.example.lenovo.address_list.adapter.SearchAdapter;
import com.example.lenovo.address_list.db.Person;
import com.example.lenovo.address_list.message_bottom_nav.Message_Bottom_NavActivity;

import java.util.List;

/**
 * @author : PengLiang
 * Time : 2019/9/3
 * Description : 组件监听后要处理的事情
 */
public class EventUtil {

    public static void setNavViewEventAfter(NavigationView navView, final DrawerLayout mDrawerlayout, final Activity activity, final SearchAdapter mAdapter, final List<com.example.lenovo.address_list.db.Person> listData) {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //点击修改数据菜单
                    case R.id.nav_camera:
                        Toast.makeText(activity, "不好意思，本功能暂未开放", Toast.LENGTH_SHORT).show();
                        mDrawerlayout.closeDrawers();
                        return true;
                    //点击上传保存菜单
                    case R.id.nav_gallery:
                        Toast.makeText(activity, "不好意思，本功能暂未开放", Toast.LENGTH_SHORT).show();
                        mDrawerlayout.closeDrawers();
                        return true;
                    //点击同步更新菜单
                    case R.id.nav_slideshow:
                        DialogUtil.syncUpdateAlertDialog(activity,mAdapter,listData);
                        mDrawerlayout.closeDrawers();
                        return true;
                    //点击系统设置菜单
                    case R.id.nav_manage:
                        Toast.makeText(activity, "不好意思，本功能暂未开放", Toast.LENGTH_SHORT).show();
                        mDrawerlayout.closeDrawers();
                        return true;
                    // 点击通话记录菜单
                    case R.id.nav_share:
                        // 如果没有读取通话记录的权限
                        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                            //申请读取通话记录的权限，1是请求码，只要是确定值就可以了
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                        }
                        //获得了读取通话记录的权限
                        else {
                            // 跳转到通话记录界面
                            Intent intent = new Intent(activity, BottomNavigationActivity.class);
                            activity.startActivity(intent);
                        }
                        return true;
                    // 点击短信记录菜单
                    case R.id.nav_send:
                        // 如果没有读取通话记录的权限
                        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                            //申请读取通话记录的权限，1是请求码，只要是确定值就可以了
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, 2);
                        }
                        //获得了读取通话记录的权限
                        else {
                            // 跳转到短信记录界面
                            Intent intent = new Intent(activity, Message_Bottom_NavActivity.class);
                            activity.startActivity(intent);
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public static void setSearchFbEvent(final Activity activity, FloatingActionButton mSearchFb, final EditText mSearchEt, final List<Person> list, final List<Person> listData, final SearchAdapter mAdapter) {
        mSearchFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                final String search[] = {"按姓名查找", "按手机号码查找", "按手机集团号查找", "按车牌找车主", "按部门查找", "我的圈子"};
                builder.setItems(search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hint;
                        switch (which) {
                            case 0:
                                hint = R.string.search_input_name;
                                SearchUtil.setSearchMode(hint, mSearchEt, list, listData, mAdapter, activity);
                                break;
                            case 1:
                                hint = R.string.search_input_phone_number;
                                SearchUtil.setSearchMode(hint, mSearchEt, list, listData, mAdapter, activity);
                                break;
                            case 2:
                                hint = R.string.search_input_group_number;
                                SearchUtil.setSearchMode(hint, mSearchEt, list, listData, mAdapter, activity);
                                break;
                            case 3:
                                hint = R.string.search_input_car_number;
                                SearchUtil.setSearchMode(hint, mSearchEt, list, listData, mAdapter, activity);
                                break;
                            case 4:
                                DialogUtil.DepartmentAlertDialog(activity, list, listData, mAdapter);
                                break;
                            //点击我的圈子
                            case 5:
                                DialogUtil.addMy(activity);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
    }

}
