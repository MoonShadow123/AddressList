package com.example.lenovo.address_list.util;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.lenovo.address_list.R;
import com.example.lenovo.address_list.adapter.SearchAdapter;
import com.example.lenovo.address_list.db.Person;

import java.util.List;

/**
 * @author : PengLiang
 * Time : 2019/9/2
 * Description : 查找工具类，根据选择的查找方式进行查找
 */
public class SearchUtil {
    // 设置查找方式
    public static void setSearchMode(final int hint, final EditText mSearchEt, final List<com.example.lenovo.address_list.db.Person> list, final List<Person> listData, final SearchAdapter mAdapter, Activity activity) {
        String stringHint = activity.getResources().getString(hint);
        mSearchEt.setHint(stringHint);
        String data = mSearchEt.getText().toString().trim();
        listData.clear();
        mAdapter.notifyDataSetChanged();
        // 根据点击的查找方式进行查找
        switch (hint) {
            case R.string.search_input_name:
                SearchUtil.search(data, list, listData, mAdapter); // 按姓名查找
                break;
            case R.string.search_input_phone_number:
                SearchUtil.search_phone(data, list, listData, mAdapter); // 手机号查找
                break;
            case R.string.search_input_group_number:
                SearchUtil.search_group(data, list, listData, mAdapter); // 按集团号查找
                break;
            case R.string.search_input_car_number:
                SearchUtil.search_car(data, list, listData, mAdapter); // 按车牌查找
                break;
            default:
                break;
        }


        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            // 输入文本后的查找
            @Override
            public void afterTextChanged(Editable s) {
                String data = mSearchEt.getText().toString().trim();
                switch (hint) {
                    case R.string.search_input_name:
                        SearchUtil.search(data, list, listData, mAdapter); // 按姓名查找
                        break;
                    case R.string.search_input_phone_number:
                        SearchUtil.search_phone(data, list, listData, mAdapter); // 手机号查找
                        break;
                    case R.string.search_input_group_number:
                        SearchUtil.search_group(data, list, listData, mAdapter); // 按集团号查找
                        break;
                    case R.string.search_input_car_number:
                        SearchUtil.search_car(data, list, listData, mAdapter); // 按车牌查找
                        break;
                    default:
                        break;
                }

            }
        });

    }


    // 按姓名查找
    public static void search(String data, List<com.example.lenovo.address_list.db.Person> list, List<com.example.lenovo.address_list.db.Person> listData, SearchAdapter mAdapter) {
        listData.clear();
        for (int i = 0; i < list.size(); i++) {
            Person person = list.get(i);
            String name = person.getName() + "";
            if (person.getName().contains(data) || name.contains(data)) {
                listData.add(person);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    // 按手机号查找
    public static void search_phone(String data, List<com.example.lenovo.address_list.db.Person> list, List<com.example.lenovo.address_list.db.Person> listData, SearchAdapter mAdapter) {
        listData.clear();
        for (int i = 0; i < list.size(); i++) {
            Person person = list.get(i);
            String phone_number = person.getPhoneNumber() + "";
            if (person.getPhoneNumber().contains(data) || phone_number.contains(data)) {
                listData.add(person);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    // 按车牌查找
    public static void search_car(String data, List<com.example.lenovo.address_list.db.Person> list, List<com.example.lenovo.address_list.db.Person> listData, SearchAdapter mAdapter) {
        listData.clear();
        for (int i = 0; i < list.size(); i++) {
            Person person = list.get(i);
            String car_number = person.getCarNumber() + "";
            if (person.getCarNumber().contains(data) || car_number.contains(data)) {
                listData.add(person);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //按集团号查找
    public static void search_group(String data, List<com.example.lenovo.address_list.db.Person> list, List<com.example.lenovo.address_list.db.Person> listData, SearchAdapter mAdapter) {
        listData.clear();
        for (int i = 0; i < list.size(); i++) {
            Person person = list.get(i);
            String group_number = person.getGroupNumber() + "";
            if (person.getGroupNumber().contains(data) || group_number.contains(data)) {
                listData.add(person);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


}
