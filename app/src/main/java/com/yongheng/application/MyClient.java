package com.yongheng.application;

import android.content.Context;
import android.text.TextUtils;

import com.yongheng.module.home.BusinessManager;
import com.yongheng.module.product.ProductManager;
import com.yongheng.network.HttpRequestManager;
import com.yongheng.network.IInterface;
import com.yongheng.network.IRequest;
import com.yongheng.network.ResponseErrorManager;
import com.yongheng.storage.StorageManager;

import java.util.HashMap;

/**
 * Created by melon on 2017/1/3.
 */

public class MyClient {

    public static final String SERVICE_HTTP_REQUEST = "httpRequest";

    private static MyClient myClient;

    private Context context;

    private HashMap<String, IInterface> mService = new HashMap<String, IInterface>();

    public static synchronized MyClient getMyClient(){
        if (myClient == null){
            myClient = new MyClient();
        }
        return myClient;
    }

    public void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("IpinClient#init,the param context is null,please check again");
        }
        this.context = context;

        StorageManager.getInstance().init(context);

        getMyClient().getDataManager().init(context);


        IRequest request = new HttpRequestManager(context);
        mService.put(MyClient.SERVICE_HTTP_REQUEST, request);
    }

    public IInterface getService(String serviceName) {

        if (TextUtils.isEmpty(serviceName)) {
            return null;
        }

        return mService.get(serviceName);

    }
    private DataManager dataManager;
    private ResponseErrorManager responseErrorManager;
    private BusinessManager businessManager;
    private ProductManager productManager;


    public synchronized DataManager getDataManager(){
        if (dataManager == null){
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public synchronized ResponseErrorManager getResponseErrorManager (){
        if (responseErrorManager == null){
            responseErrorManager = new ResponseErrorManager();
        }
        return responseErrorManager;
    }

    public synchronized BusinessManager getBusinessManager (){
        if (businessManager == null){
            businessManager = new BusinessManager();
        }
        return businessManager;
    }

    public synchronized ProductManager getProductManager (){
        if (productManager == null){
            productManager = new ProductManager();
        }
        return productManager;
    }

}
