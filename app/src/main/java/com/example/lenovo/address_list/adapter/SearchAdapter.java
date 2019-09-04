package com.example.lenovo.address_list.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.address_list.bean.Person;
import com.example.lenovo.address_list.R;

import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<com.example.lenovo.address_list.db.Person> list;
    private Context mContext;

    public SearchAdapter(List<com.example.lenovo.address_list.db.Person> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        View Linear1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.my_txt1);
            txt2 = itemView.findViewById(R.id.my_txt2);
            Linear1 = itemView.findViewById(R.id.myadapter_ll);
        }
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_myadapter, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int i) {
        final com.example.lenovo.address_list.db.Person person = list.get(i);
        holder.txt1.setText(person.getName());
        holder.txt2.setText(person.getDepartment());

        holder.Linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 个人信息的警告框
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(person.getName() + "的个人信息");
                final String message[] = {"姓  名：" + person.getName(), "手机号码：" + person.getPhoneNumber(), "集团号码：" + person.getGroupNumber(),
                        "办公电话：" + person.getOfficeNumber(), "车牌号码：" + person.getCarNumber(), "所属部门：" + person.getDepartment(), "其他信息：" + person.getOther()
                };
                builder.setItems(message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            //点击手机号码
                            case 1:
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                builder1.setTitle(message[1]);
                                builder1.setPositiveButton("打电话", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (message[1].equals("手机号码：")) {
                                            Toast.makeText(mContext, "空号，无法拨号", Toast.LENGTH_SHORT).show();
                                        } else {
                                            callPhone(person.getPhoneNumber());
                                        }

                                    }
                                });
                                builder1.setNegativeButton("发短信", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (message[1].equals("手机号码：")) {
                                            Toast.makeText(mContext, "空号，无法发送短信", Toast.LENGTH_SHORT).show();
                                        } else {
                                            sendSMS(person.getPhoneNumber());
                                        }

                                    }
                                });
                                builder1.show();
                                break;
                            // 点击集团号
                            case 2:
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
                                builder2.setTitle(message[2]);
                                builder2.setPositiveButton("打电话", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (message[2].equals("集团号码：")) {
                                            Toast.makeText(mContext, "空号，无法拨号", Toast.LENGTH_SHORT).show();
                                        } else {
                                            callPhone(person.getGroupNumber());
                                        }

                                    }
                                });
                                builder2.setNegativeButton("发短信", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (message[2].equals("集团号码：")) {
                                            Toast.makeText(mContext, "空号，无法发送短信", Toast.LENGTH_SHORT).show();
                                        } else {
                                            sendSMS(person.getGroupNumber());
                                        }

                                    }
                                });
                                builder2.show();
                                break;

                            // 点击办公电话
                            case 3:
                                AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
                                builder3.setTitle(message[3]);
                                builder3.setPositiveButton("打电话", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (message[3].equals("办公电话：")) {
                                            Toast.makeText(mContext, "空号，无法拨号", Toast.LENGTH_SHORT).show();
                                        } else {
                                            callPhone(person.getOfficeNumber());
                                        }
                                    }
                                });
                                builder3.show();
                                break;
                        }
                    }
                });

                builder.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    // 检查是否获得了打电话的权限
    public void checkCallPermission(String number) {
        // 如果没有获得打电话的权限
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请打电话的权限，1是请求码，只要是确定值就可以了
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        //获得了打电话的权限
        else {
            call(number);
        }
    }

    //打电话
    private void call(String number) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            // tel是协议
            intent.setData(Uri.parse("tel:" + number));
            mContext.startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }


    // 跳转到发短信界面，用户手动输入内容并发送
    public void sendSMS(String smsBody) {
        Uri smsToUri = Uri.parse("smsto:" + smsBody);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        //传值，可带一些参数去调用系统打开的界面
//        intent.putExtra("sms_body", "");
        mContext.startActivity(intent);
    }


}
