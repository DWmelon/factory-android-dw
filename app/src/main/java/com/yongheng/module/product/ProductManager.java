package com.yongheng.module.product;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yongheng.application.MyClient;
import com.yongheng.model.ProductJoinModel;
import com.yongheng.model.ProductJoinModelList;
import com.yongheng.module.home.listener.OnUpdateDataFinishListener;
import com.yongheng.model.ProductModel;
import com.yongheng.module.egg.OnAddProductListener;
import com.yongheng.module.home.listener.OnDeleteListener;
import com.yongheng.network.IRequest;
import com.yongheng.network.IRequestCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by melon on 2017/5/11.
 */

public class ProductManager {

    private static final String URL_PRODUCT_ADD = "http://119.23.222.106/yongheng/product/add";
    private static final String URL_PRODUCT_UPDATE = "http://119.23.222.106/yongheng/product/update";
    private static final String URL_PRODUCT_LIST = "http://119.23.222.106/yongheng/product/list";
    private static final String URL_PRODUCT_DELETE = "http://119.23.222.106/yongheng/product/delete";
    private static final String URL_PRODUCT_SEARCH = "http://119.23.222.106/yongheng/product/search";

    private List<ProductModel> productModelList = new ArrayList<>();

    private ProductJoinModelList productJoinModelList = new ProductJoinModelList();

    public void requestAddProduct(final ProductModel model, final int indexI, final int indexJ, final OnAddProductListener listener){

        HashMap<String,String> map = new HashMap<>();

//        String str = V2ArrayUtil.getJsonArrData(businessModels);

        map.put("productName",model.getProName());
        map.put("remark",model.getRemark());
        map.put("price",model.getPrice());
        map.put("businessId",String.valueOf(model.getBusinessId()));


        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_PRODUCT_ADD, map, false,new IRequestCallback() {

            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (listener == null){
                    return;
                }

                if (jsonObject == null){
                    listener.onUpdateProductFinish(-1,"",-1,-1,-1);
                }

                JSONObject object = jsonObject.getJSONObject("data");


                listener.onUpdateProductFinish(jsonObject.getIntValue("code"),jsonObject.getString("msg"),object.getLongValue("id"),indexI,indexJ);

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                listener.onUpdateProductFinish(code,"",-1,-1,-1);
            }
        });

    }

    public void updateProduct(ProductModel model, final int position, final OnUpdateProductListener listener){

        Map<String,String> map = new HashMap<>();
        map.put("productId",String.valueOf(model.getProductId()));
        map.put("productName",model.getProName());
        map.put("price",model.getPrice());
        map.put("remark",model.getRemark());

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_PRODUCT_UPDATE, map, false,new IRequestCallback() {

            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (listener == null){
                    return;
                }

                if (jsonObject == null){
                    listener.onUpdateProductFinish(-1,"修改失败",position);
                    return;
                }

                listener.onUpdateProductFinish(jsonObject.getIntValue("code"),jsonObject.getString("msg"),position);

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                listener.onUpdateProductFinish(code,"修改失败",position);
            }
        });

    }

    public void requestProductListByBusinessId(Long id, final OnUpdateDataFinishListener listener){

        HashMap<String,String> map = new HashMap<>();
        map.put("businessId",String.valueOf(id));

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_PRODUCT_LIST, map, false,new IRequestCallback() {

            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (listener == null){
                    return;
                }

                if (jsonObject == null){
                    listener.onUpdateFinish(-1,"");
                    return;
                }

                JSONObject object = jsonObject.getJSONObject("data");
                if (object == null){
                    listener.onUpdateFinish(-1,"");
                    return;
                }

                JSONArray jsonArray = object.getJSONArray("productModelList");
                if (jsonArray == null){
                    listener.onUpdateFinish(-1,"");
                    return;
                }

                productModelList.clear();
                for (int i = 0;i<jsonArray.size();i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    ProductModel model = new ProductModel();
                    model.decode(obj);
                    productModelList.add(model);
                }

                if (!productModelList.isEmpty()){
                    Collections.sort(productModelList, new Comparator<ProductModel>() {
                        @Override
                        public int compare(ProductModel model, ProductModel t1) {
                            return model.getProName().compareTo(t1.getProName());
                        }
                    });
                }

                listener.onUpdateFinish(jsonObject.getIntValue("code"),jsonObject.getString("msg"));

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                listener.onUpdateFinish(code,"");
            }
        });


    }

    public void deleteProduct(Long id, final int position, final OnDeleteListener listener){

        HashMap<String,String> map = new HashMap<>();
        map.put("productId",String.valueOf(id));

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_PRODUCT_DELETE, map, false,new IRequestCallback() {

            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (listener == null){
                    return;
                }

                if (jsonObject == null){
                    listener.onDeleteFinish(-1,"",position);
                    return;
                }

                listener.onDeleteFinish(jsonObject.getIntValue("code"),jsonObject.getString("msg"),position);

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                listener.onDeleteFinish(code,"",position);
            }
        });


    }

    public void searchProduct(String keyWord, final OnSearchProductListener listener){
        if (TextUtils.isEmpty(keyWord)){
            return;
        }

        if (productJoinModelList!=null){
            productJoinModelList.getProductJoinModelList().clear();
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("keyWord",keyWord);

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_PRODUCT_SEARCH, map, false,new IRequestCallback() {

            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (listener == null){
                    return;
                }

                if (jsonObject == null){
                    listener.onSearchFinish(false);
                    return;
                }

                if (productJoinModelList == null){
                    productJoinModelList = new ProductJoinModelList();
                }

                productJoinModelList.decode(jsonObject);

                listener.onSearchFinish(productJoinModelList.getCode() == 0);

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                listener.onSearchFinish(false);
            }
        });


    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    public void addProduct(ProductModel model){
        productModelList.add(model);
        Collections.sort(productModelList, new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel model, ProductModel t1) {
                return model.getProName().compareTo(t1.getProName());
            }
        });
    }

    public List<ProductJoinModel> getProductJoinModelList() {
        return productJoinModelList.getProductJoinModelList();
    }

    public void release(){
        productModelList.clear();
    }

}
