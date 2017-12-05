package com.yongheng.model;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by melon on 2017/6/24.
 */

public class ProductJoinModel extends ProductModel {

    private static final String KEY_BUSINESS_NAME = "businessName";

    private String businessName;

    public void decode(JSONObject object){
        if (object == null){
            return;
        }
        super.decode(object);

        businessName = object.getString(KEY_BUSINESS_NAME);
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
