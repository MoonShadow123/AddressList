package com.example.lenovo.address_list.message_bottom_nav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.bean.SmsInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Message_MyAdapter extends RecyclerView.Adapter<Message_MyAdapter.MyViewHolder> {
    List<SmsInfo> infos;
    Context mContext;
    private OnItemClickListener listener;

    public Message_MyAdapter(Context context, List<SmsInfo> infos,OnItemClickListener listener) {
        this.mContext = context;
        this.infos = infos;
        this.listener = listener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_body, tv_date, tv_name, tv_type;
        private View view;
        private ImageView img_head;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // 初始化组件
//            tv_number = itemView.findViewById(R.id.tv_number);
            tv_date = itemView.findViewById(R.id.msg_tv_data);
            tv_name = itemView.findViewById(R.id.msg_tv_name);
            tv_body = itemView.findViewById(R.id.msg_tv_body);
            img_head = itemView.findViewById(R.id.msg_img);
            view = itemView.findViewById(R.id.msg_ll);
//            tv_type = itemView.findViewById(R.id.tv_type);
//            view = itemView.findViewById(R.id.contact_ll);
        }
    }


    @NonNull
    @Override
    public Message_MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_message_contact_rcy, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Message_MyAdapter.MyViewHolder holder, final int i) {
//        final View view = holder.itemView;
        final SmsInfo info = infos.get(i);
        // 显示号码
        String name = "";
        name = info.getName();
        if (name == null) {
            holder.tv_name.setText(info.getPhoneNumber());

        } else {
            holder.tv_name.setText(info.getName());
        }

        // 设置内容
        holder.tv_body.setText(info.getSmsbody());


        // 日期
        // 转换日期格式，并显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
        String dateString = format.format(info.getDate());

        // 大写的HH表示24进制 小写的hh表示12进制  设置日期格式为小时和分钟
        SimpleDateFormat format_now = new SimpleDateFormat("HH:mm");
        String dateString_now = format_now.format(info.getDate());

        //设置系统时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d");
        //获取当前系统时间
        Date date = new Date(System.currentTimeMillis());
        //获取当前日期前一天的日期
        Date date_yesterday = getNextDay(date);
        String data_system = "";
        String str_data_yesterday = "";
        //当前日期的字符串
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

        // 对item的点击事件，通常使用接口进行回调
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listener.onClick(i);
            }
        });

        //设置默认头像图标
        holder.img_head.setImageResource(R.drawable.person);
        // 设置熟悉的号码的头像和名字
        String phone_number = info.getPhoneNumber();
        switch (phone_number) {
            //联通
            case "10010":
                holder.img_head.setImageResource(R.drawable.niantong);
                holder.tv_name.setText("中国联通");
                break;

            //邮政
            case "95580":
                holder.img_head.setImageResource(R.drawable.youzhen);
                holder.tv_name.setText("邮政储蓄银行");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    //获取当前日期前一天的日期
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    public interface OnItemClickListener{
        void onClick(int postion);
    }


}
