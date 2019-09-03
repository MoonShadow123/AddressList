package com.example.lenovo.address_list.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.lenovo.address_list.activity.BottomNavigationActivity;
import com.example.lenovo.address_list.message_bottom_nav.Message_Bottom_NavActivity;

/**
 * @author : PengLiang
 * Time : 2019/9/3
 * Description : 动态权限的管理，跳转到手机的权限设置
 */
public class PermissionUtil {

    public static AlertDialog dialog;
    // 系统跳转识别码
    private static final int MSG = 20;
    private static final int TEL = 10;

    // 提示用户去应用设置界面手动开启权限
    public static void showDialogTipUserGoToAppSettting(String name, final int TEMP, final Activity activity) {

        // 跳转到应用设置界面
//                        finish();
        dialog = new AlertDialog.Builder(activity)
                .setTitle(name + "权限不可用")
                .setMessage("请在-应用设置-权限-中，允许“传媒通讯录”使用" + name + "权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting(TEMP, activity);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private static void goToAppSetting(int i, Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, i);
    }


    // 检查电话权限是否获取
    public static void checkPhonePermission(AlertDialog dialog, Activity activity, final int TEL) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 提示用户应该去应用设置界面手动开启权限
                PermissionUtil.showDialogTipUserGoToAppSettting("电话", TEL, activity);
            } else {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(activity, "权限获取成功", Toast.LENGTH_SHORT).show();
                // 跳转到通话记录界面
                Intent intent = new Intent(activity, BottomNavigationActivity.class);
                activity.startActivity(intent);
            }
        }
    }

    // 检查短信权限是否获取
    public static void checkMsgPermission(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 提示用户应该去应用设置界面手动开启权限
                PermissionUtil.showDialogTipUserGoToAppSettting("短信", MSG, activity);
            } else {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(activity, "权限获取成功", Toast.LENGTH_SHORT).show();
                // 跳转到通话记录界面
                Intent intent = new Intent(activity, Message_Bottom_NavActivity.class);
                activity.startActivity(intent);
            }
        }
    }

    // 电话权限的回调
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void onPhoneRequest(Activity activity, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 跳转到通话记录界面
            Intent intent = new Intent(activity, BottomNavigationActivity.class);
            activity.startActivity(intent);
            // 如果没有打电话的权限
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //申请读取通话记录的权限，5是请求码，只要是确定值就可以了
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 5);
            }
        } else {
            // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
            boolean b = activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG);
            if (!b) {
                // 用户还是想用我的 APP 的
                // 提示用户去应用设置界面手动开启权限
                PermissionUtil.showDialogTipUserGoToAppSettting("电话", TEL, activity);
            }
        }

    }

    // 短信权限的回调
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void onMsgRequest(Activity activity, int[] grantResults) {
        // 如果已经获得了短信权限
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 跳转到短信记录界面
            Intent intent = new Intent(activity, Message_Bottom_NavActivity.class);
            activity.startActivity(intent);
        } else {
            // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
            boolean b = activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS);
            if (!b) {
                // 用户还是想用我的 APP 的
                // 提示用户去应用设置界面手动开启权限
                PermissionUtil.showDialogTipUserGoToAppSettting("短信", MSG, activity);
            }

        }
    }
}
