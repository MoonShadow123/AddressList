package com.example.lenovo.address_list.db;

import org.litepal.crud.DataSupport;

/**
 * @author : PengLiang
 * Time : 2019/9/4
 * Description : 数据库表映射的实体类
 */
public class Person extends DataSupport {
    private int id;
    private String name; // 姓名
    private String phoneNumber; // 手机号码
    private String groupNumber; // 集团号码
    private String officeNumber; // 办公号码
    private String carNumber; // 车牌号码
    private String department; // 部门
    private String other; // 其它

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getOther() {
        return other;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
