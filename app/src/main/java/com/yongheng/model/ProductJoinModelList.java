package com.yongheng.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/6/24.
 */

public class ProductJoinModelList extends BaseModel {

    private static final String KEY_PRODUCT_LIST = "productList";

    private List<ProductJoinModel> productJoinModelList = new ArrayList<>();

    public void decode(JSONObject object){
        super.decode(object);
        if (object == null){
            return;
        }
        JSONObject data = object.getJSONObject(KEY_DATA);
        if (data == null){
            return;
        }

        JSONArray array = data.getJSONArray(KEY_PRODUCT_LIST);
        if (array == null){
            return;
        }

        productJoinModelList.clear();
        for (int i = 0;i<array.size();i++){
            JSONObject obj = array.getJSONObject(i);
            ProductJoinModel model = new ProductJoinModel();
            model.decode(obj);
            productJoinModelList.add(model);
        }

    }

    public List<ProductJoinModel> getProductJoinModelList() {
        return productJoinModelList;
    }

    public void setProductJoinModelList(List<ProductJoinModel> productJoinModelList) {
        this.productJoinModelList = productJoinModelList;
    }
}
