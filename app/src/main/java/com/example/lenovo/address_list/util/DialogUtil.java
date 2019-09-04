package com.example.lenovo.address_list.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.adapter.SearchAdapter;
import com.example.lenovo.address_list.db.Person;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author : PengLiang
 * Time : 2019/9/3
 * Description : 对话框工具类，管理对话框
 */
public class DialogUtil {

    private static ProgressDialog progressDialog;

    // 同步更新提示框
    public static void syncUpdateAlertDialog(final Activity activity, final SearchAdapter mAdapter,  List<com.example.lenovo.address_list.db.Person> listData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("更新数据");
        builder.setMessage("注意：此操作需要联网，请确保手机能够正常访问网络数据。" + "\n" + "系统将下载服务器上的最新数据，如果下载成功，则会清除本软件的历史数据后再写入最新数据，下载失败不会影响老数据的使用。" + "\n" + "不要重复点击此按钮，请耐心等待网络数据下载结束。");
        builder.setPositiveButton("开始下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setTitle("正在更新数据");
                progressDialog.setMessage("更新中");
                progressDialog.show();
                String address = "http://120.76.210.221/DzcmMailList/GetUser";
                HttpUtil.sendOkHttpRequest(address, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        boolean result = false;
                        result = ParseUtility.handleResponse(responseText);
                        if (result) {
                            if (progressDialog != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                    }
                                });

                            }

                            Toast.makeText(activity, "加载成功", Toast.LENGTH_SHORT).show();
                        } else {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(activity, "json数据处理失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
            }
        });
        builder.setNegativeButton("暂不下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }




    // 我的圈子提示框（警告框）
    public static void addMy(final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("我的圈子");
        builder.setPositiveButton("新建朋友圈", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setTitle("请输入朋友圈名称");
                // 自定义布局，其实就是一个输入框
                View view = LayoutInflater.from(activity).inflate(R.layout.custom_alert_dialog, null);
                EditText editText = view.findViewById(R.id.custom_edit);
                builder1.setView(view);
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(activity, "朋友圈创建成功，请到联系人列表添加圈内朋友", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.show();
            }
        });
        builder.setNegativeButton("取消返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    // 显示部门选择的一个警告框
    public static void DepartmentAlertDialog(Activity activity, final List<Person> list, final List<Person> listData, final SearchAdapter mAdapter) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setTitle("请选择部门");
        final String department[] = {"院级领导", "党政办公室", "组织人事处", "宣传统战部"};
        builder1.setItems(department, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    //点击院级领导
                    case 0:
                        findDepartment(department[which], list, listData, mAdapter);
                        break;
                    //点击党政办公室
                    case 1:
                        findDepartment(department[which], list, listData, mAdapter);
                        break;
                    //点击组织人事处
                    case 2:
                        findDepartment(department[which], list, listData, mAdapter);
                        break;
                    //点击宣传统战部
                    case 3:
                        findDepartment(department[which], list, listData, mAdapter);
                        break;
                }
            }
        });
        builder1.show();
    }

    //按部门查找
    private static void findDepartment(String data, List<Person> list, List<Person> listData, SearchAdapter mAdapter) {
        listData.clear();
        for (int i = 0; i < list.size(); i++) {
           Person person = list.get(i);
            String department = person.getDepartment() + "";
            if (person.getDepartment().contains(data) || department.contains(data)) {
                listData.add(person);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
