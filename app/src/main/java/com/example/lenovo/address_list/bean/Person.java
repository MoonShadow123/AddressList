package com.example.lenovo.address_list.bean;

import java.util.ArrayList;
import java.util.List;

public class Person {
    //姓名
    public String name;
    //手机号码
    public String phone_number;
    //集团号码
    public String group_number;
    //办公电话
    public String office_phone;
    //车牌号码
    public String car_number;
    //部门
    public String department;
    //其它
    public String other;

    public Person(String name, String phone_number, String group_number, String office_phone, String car_number, String department, String other) {
        this.name = name;
        this.phone_number = phone_number;
        this.group_number = group_number;
        this.office_phone = office_phone;
        this.car_number = car_number;
        this.department = department;
        this.other = other;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getGroup_number() {
        return group_number;
    }

    public String getOffice_phone() {
        return office_phone;
    }

    public String getCar_number() {
        return car_number;
    }

    public String getDepartment() {
        return department;
    }

    public String getOther() {
        return other;
    }


    public static List<Person> getList() {

        List<Person> list = new ArrayList<>();

        // 姓名
        String Name[] = {
                "易灿", "袁维坤", "马于军", "李云峰", "郑雄", "李勇", "李盖虎", "刘丹",
                "黄骊", "朱丹", "刘芳源", "李佳", "袁维坤", "袁维坤", "袁维坤", "袁维坤",
                "易灿", "袁维坤", "袁维坤", "袁维坤", "袁维坤", "袁维坤", "袁维坤", "袁维坤"
        };
        // 手机号码
        String Phone_number[] = {
                "13787111862", "13973123218", "13755072288", "13808467786", "13873182993", "13874819858", "13973197806", "18627585999",
                "13787220017", "13908451946", "13974846088", "15974283723", "", "", "", "",
                "13787111862", "13973123218", "13755072288", "", "", "", "", ""
        };
        // 集团号码
        String Group_number[] = {
                "6862", "6218", "6899", "6811", "6952", "7386", "9981", "8874",
                "6869", "6563", "6819", "", "", "", "", "",
                "6562", "6001", "5412", "", "", "", "", ""
        };
        //办公电话
        String Office_number[] = {
                "", "84028512", "84028508", "84028512", "84028509", "84028513", "84028514", "84028516",
                "", "84028512", "84028508", "", "", "", "", "",
                "", "84028512", "84028508", "", "", "", "", ""

        };
        //车牌号码
        String Car_number[] = {
                "湘A6591F", "湘B7591F", "湘C6991F", "湘F9591F", "湘D6791F", "湘G6521F", "湘G6595F", "湘W6592F",
                "湘A9591F", "", "", "", "", "", "", "",
                "湘A6591F", "", "", "", "", "", "", ""
        };
        // 部门
        String Department[] = {
                "院级领导", "院级领导", "院级领导", "党政办公室", "党政办公室", "党政办公室", "组织人事处", "组织人事处",
                "组织人事处", "宣传统战部", "宣传统战部", "宣传统战部", "院级领导", "院级领导", "院级领导", "院级领导",
                "院级领导", "院级领导", "院级领导", "院级领导", "院级领导", "院级领导", "院级领导", "院级领导"
        };
        //其它信息
        String Other[] = {
                "彭亮的老师", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", ""

        };

        Person person[] = new Person[Name.length];

        for (int i = 0; i < person.length; i++) {
            person[i] = new Person(Name[i], Phone_number[i], Group_number[i], Office_number[i], Car_number[i], Department[i], Other[i]);
            list.add(person[i]);
        }

        return list;
    }
}
