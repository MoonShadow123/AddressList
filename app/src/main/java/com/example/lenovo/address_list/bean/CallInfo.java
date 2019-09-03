package com.example.lenovo.address_list.bean;

public class CallInfo {

    public String number; // 号码
    public long date;     // 日期
    public int type;      // 类型：来电、去电、未接
    public String name;

    public CallInfo(String number, long date, int type,String name) {
        this.number = number;
        this.date = date;
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CallInfo{" +
                "number='" + number + '\'' +
                ", date=" + date +
                ", type=" + type +", name="+name+
                '}';
    }

}
