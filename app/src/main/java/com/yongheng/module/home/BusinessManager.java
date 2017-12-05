package com.yongheng.module.home;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yongheng.application.MyClient;
import com.yongheng.model.BusinessModel;
import com.yongheng.module.egg.OnAddBusinessListener;
import com.yongheng.module.home.listener.OnDeleteListener;
import com.yongheng.module.home.listener.OnUpdateBusinessListener;
import com.yongheng.module.home.listener.OnUpdateDataFinishListener;
import com.yongheng.network.IRequest;
import com.yongheng.network.IRequestCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by melon on 2017/5/11.
 */

public class BusinessManager {

//    http://192.168.31.164:12344

    private static final String URL_BUSINESS_ADD = "http://119.23.222.106/yongheng/business/add";
    private static final String URL_BUSINESS_LIST = "http://119.23.222.106/yongheng/business/list";
    private static final String URL_BUSINESS_UPDATE = "http://119.23.222.106/yongheng/business/update";
    private static final String URL_BUSINESS_DELETE = "http://119.23.222.106/yongheng/business/delete";


    public void requestAddBusiness(final BusinessModel model, final int index, final OnAddBusinessListener listener){

        HashMap<String,String> map = new HashMap<>();

        map.put("businessName",model.getBusinessName());
        map.put("remark",model.getRemark());
        map.put("localName",model.getLocalName());

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_BUSINESS_ADD, map, false,new IRequestCallback() {

            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (listener == null){
                    return;
                }

                if (jsonObject == null){
                    listener.onAddBusinessFinish(-1,"",-1,-1);
                    return;
                }

                int code = jsonObject.getInteger("code");
                String msg = jsonObject.getString("msg");

                if (code != 0){
                    listener.onAddBusinessFinish(code,msg,-1,-1);
                }

                jsonObject = jsonObject.getJSONObject("data");
                Long id = jsonObject.getLongValue("id");

                listener.onAddBusinessFinish(code,msg,id,index);

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                listener.onAddBusinessFinish(code,"",-1,-1);
            }
        });

    }

    public void requestUpdateBusiness(final BusinessModel model, final int index, final OnUpdateBusinessListener listener){

        HashMap<String,String> map = new HashMap<>();


        map.put("businessId",String.valueOf(model.getBusinessId()));
        map.put("businessName",model.getBusinessName());
        map.put("remark",model.getRemark());
        map.put("localName",model.getLocalName());

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_BUSINESS_UPDATE, map, false,new IRequestCallback() {

            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (listener == null){
                    return;
                }

                if (jsonObject == null){
                    listener.onUpdateBusinessFinish(-1,"",-1);
                    return;
                }


                listener.onUpdateBusinessFinish(jsonObject.getIntValue("code"),jsonObject.getString("msg"),index);

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                listener.onUpdateBusinessFinish(code,"",-1);
            }
        });

    }

    public void requestBusinessList(List<Long> ids, final OnUpdateDataFinishListener listener){

        HashMap<String,String> map = new HashMap<>();
//        if (ids==null){
//            ids = new ArrayList<>();
//        }
//        map.put("businessIds", V2ArrayUtil.getJsonArrData(ids));
        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_BUSINESS_LIST, map, false,new IRequestCallback() {

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
                if (object != null){
                    JSONArray jsonArray = object.getJSONArray("businessModelList");
                    if (jsonArray != null){
                        List<BusinessModel> businessModels = new ArrayList<>();
                        for (int i = 0 ;i<jsonArray.size();i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            BusinessModel model = new BusinessModel();
                            model.decode(obj);
                            businessModels.add(model);
                        }
                        Collections.sort(businessModels, new Comparator<BusinessModel>() {
                            @Override
                            public int compare(BusinessModel businessModel, BusinessModel t1) {
                                return businessModel.getFirstPy().compareTo(t1.getFirstPy());
                            }
                        });
                        MyClient.getMyClient().getDataManager().setModelList(businessModels);
                    }
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

    public void requestDeleteBusiness(long businessId, final int position, final OnDeleteListener listener){

        HashMap<String,String> map = new HashMap<>();
        map.put("businessId",String.valueOf(businessId));

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }
        request.sendRequestForPostWithJson(URL_BUSINESS_DELETE, map, false,new IRequestCallback() {

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

    public void removeBusiness(int position){
        MyClient.getMyClient().getDataManager().deleteBusiness(position);
    }

    public void addBusiness(BusinessModel model){
        List<BusinessModel> modelList = MyClient.getMyClient().getDataManager().getModelList();
        modelList.add(model);
        Collections.sort(modelList, new Comparator<BusinessModel>() {
            @Override
            public int compare(BusinessModel businessModel, BusinessModel t1) {
                return businessModel.getFirstPy().compareTo(t1.getFirstPy());
            }
        });
        MyClient.getMyClient().getDataManager().setModelList(modelList);
    }

}
