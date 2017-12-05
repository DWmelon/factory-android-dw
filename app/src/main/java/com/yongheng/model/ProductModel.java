package com.yongheng.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by melon on 2017/4/8.
 */

public class ProductModel implements Serializable{

    private static final long serialVersionUID = 3076157269423228761L;

    private static final String KEY_PRODUCT_ID = "productId";
    private static final String KEY_PRODUCT_NAME = "productName";
    private static final String KEY_REAMRK = "remark";
    private static final String KEY_PRICE = "price";
    private static final String KEY_BUSINESS_ID = "businessId";


    private long productId;
    private String proName;
    private String price;
    private String remark;
    private long businessId;

    public void decode(JSONObject object){
        if (object == null){
            return;
        }

        productId = object.getLongValue(KEY_PRODUCT_ID);
        proName = object.getString(KEY_PRODUCT_NAME);
        price = object.getString(KEY_PRICE);
        remark = object.getString(KEY_REAMRK);
        businessId = object.getLongValue(KEY_BUSINESS_ID);

    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "productId=" + productId +
                ", proName='" + proName + '\'' +
                ", price='" + price + '\'' +
                ", remark='" + remark + '\'' +
                ", businessId=" + businessId +
                '}';
    }
}
