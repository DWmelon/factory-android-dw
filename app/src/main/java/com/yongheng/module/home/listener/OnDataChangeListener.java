package com.yongheng.module.home.listener;

import com.yongheng.model.BusinessModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by melon on 2017/4/23.
 */

public interface OnDataChangeListener {

    public void onDataChange(ArrayList<BusinessModel> data);

    public void onSectionChange(ArrayList<String> section, HashMap<Integer, Integer> p2s, HashMap<Integer, Integer> s2p);
}
