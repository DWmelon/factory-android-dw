package com.yongheng.model;

import com.alibaba.fastjson.JSONObject;
import com.yongheng.utils.CharUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/4/8.
 */

public class BusinessModel implements Serializable{

    private static final String KEY_BUSINESS_ID = "businessId";

    private static final String KEY_BUSINESS_NAME = "businessName";

    private static final String KEY_REMARK = "remark";

    private static final String KEY_LOCAL_NAME = "localName";

    private static final long serialVersionUID = -5793241866161457360L;
    private Long businessId;
    private String businessName;
    private String remark;
    private String localName;

    private String firstPy;
    private String firstChar;


    public void decode(JSONObject object){
        if (object == null){
            return;
        }

        businessId = object.getLongValue(KEY_BUSINESS_ID);
        businessName = object.getString(KEY_BUSINESS_NAME);
        remark = object.getString(KEY_REMARK);
        localName = object.getString(KEY_LOCAL_NAME);

        setFirstPy(CharUtil.cn2py(businessName));

    }

    private List<ProductModel> productList = new ArrayList<>();

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getFirstPy() {
        return firstPy;
    }

    public void setFirstPy(String firstPy) {
        this.firstPy = firstPy;
        if (firstPy != null && !firstPy.isEmpty()){
            setFirstChar(firstPy.substring(0,1).toUpperCase());
        }
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public List<ProductModel> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductModel> productList) {
        this.productList = productList;
    }


}
