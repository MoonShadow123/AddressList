package com.example.lenovo.address_list.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.bean.CallInfo;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<CallInfo> infos;
    Context mContext;

    public MyAdapter(Context context, List<CallInfo> infos) {
        this.mContext = context;
        this.infos = infos;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_number, tv_date, tv_name, tv_type;
        private View view;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.contact_name);
            tv_number = itemView.findViewById(R.id.contact_nmuber);
            tv_date = itemView.findViewById(R.id.contact_date);
            tv_type = itemView.findViewById(R.id.contact_type);
            img = itemView.findViewById(R.id.contact_img);
            view = itemView.findViewById(R.id.contact_ll);
        }
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_contact_rcy, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.MyViewHolder holder, int i) {
        final CallInfo info = infos.get(i);
        //设置姓名，如果姓名存在联系人列表则显示姓名，否则显示陌生人
        if (info.name != null) {
            holder.tv_name.setText(info.name);
        } else {
            holder.tv_name.setText("陌生人");
        }

        holder.tv_number.setText(info.number);
        // 设置日期格式为年月日 只有1位M和d表示去除前面的0，比如2019/5/4这个就是一位M和d，如果是两位的话就显示2019/05/04
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
        String dateString = format.format(info.date);

        // 大写的HH表示24进制 小写的hh表示12进制  设置日期格式为小时和分钟
        SimpleDateFormat format_now = new SimpleDateFormat("HH:mm");
        String dateString_now = format_now.format(info.date);


        //设置系统时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d");
        //获取当前系统时间
        Date date = new Date(System.currentTimeMillis());
        //获取当前日期前一天的日期
        Date date_yesterday = getNextDay(date);
        String data_system = "";
        String str_data_yesterday = "";
        //当前日期字符串
        data_system = simpleDateFormat.format(date);
        //昨天日期的字符串
        str_data_yesterday = simpleDateFormat.format(date_yesterday);


        //对日期进行排序显示
        // 如果时间等于今天的话，则只显示小时和分钟，否则显示日期
        if (data_system.equals(dateString)) {
            //显示小时和分钟
            holder.tv_date.setText(dateString_now);
        } else if (str_data_yesterday.equals(dateString)) {
            //如果当前日期等于昨天，就显示昨天
            holder.tv_date.setText("昨天");
        } else {
            // 显示年月日
            holder.tv_date.setText(dateString);
        }


        // 设置默认图标
//        holder.img.setImageResource(R.drawable.qudian);
        // 设置默认显示的状态
//        holder.tv_type.setText("（来电）");

//                     类型
        String type = null;
        int textColor = 0;
        switch (info.type) {
            case CallLog.Calls.INCOMING_TYPE: // 来电，字体蓝色
                type = "（来电）";
                textColor = Color.BLUE;
                holder.img.setImageResource(R.drawable.laidian);
                break;
            case CallLog.Calls.OUTGOING_TYPE: // 去电，字体绿色
                type = "（去电）";
                textColor = Color.GREEN;
                holder.img.setImageResource(R.drawable.qudian);
                break;
            case CallLog.Calls.MISSED_TYPE:   // 未接，字体红色
                type = "（未接）";
                textColor = Color.RED;
                holder.img.setImageResource(R.drawable.weijie);
//                holder.tv_number.setTextColor(textColor);
                break;
        }
//        holder.tv_type.setText(type);
//        holder.tv_type.setTextColor(textColor);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // 如果没有读取通话记录的权限
//                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
////                    Toast.makeText(mContext, "请再点一下就会打电话，以后直接点就打了", Toast.LENGTH_SHORT).show();
//                    //申请读取通话记录的权限，5是请求码，只要是确定值就可以了
//                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 5);
//
//                }
                //获得了读取通话记录的权限
//                else {
                call(info.number);
//                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    // 打电话
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


//    // 两个日期比较大小
//    /**
//     * 比较两个日期的大小，日期格式为yyyy-MM-dd
//     *
//     * @param str1 the first date
//     * @param str2 the second date
//     * @return true <br/>false
//     */
//    public static boolean isDateOneBigger(String str1, String str2) {
//        boolean isBigger = false;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        Date dt1 = null;
//        Date dt2 = null;
//        try {
//            dt1 = sdf.parse(str1);
//            dt2 = sdf.parse(str2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (dt1.getTime() > dt2.getTime()) {
//            isBigger = true;
//        } else if (dt1.getTime() == dt2.getTime()) {
//            isBigger = false;
//        }
//        return isBigger;
//    }

    //获取当前日期前一天的日期
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

}
