package com.yongheng.module.product;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import com.yongheng.application.MyClient;
import com.yongheng.model.ProductModel;
import com.yongheng.module.home.listener.OnUpdateDataFinishListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/5/18.
 */
public class ProductActivity extends ProductBaseActivity implements OnUpdateDataFinishListener {

    private List<ProductModel> modelOriginList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
        requestData();
    }

    private void requestData(){
        showProgress();
        MyClient.getMyClient().getProductManager().requestProductListByBusinessId(businessId,this);
    }


    @Override
    public void onUpdateFinish(int code, String msg) {
        hideProgress();
        if (code == 0){
            if (adapter != null){
                modelOriginList = MyClient.getMyClient().getProductManager().getProductModelList();
                adapter.setData(MyClient.getMyClient().getProductManager().getProductModelList());
            }
        }else{
            Toast.makeText(this,"获取产品信息失败:"+code,Toast.LENGTH_LONG).show();
        }
    }


    private void initListener(){

        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")){
                    filterBusiness(editable.toString());
                }else {
                    adapter.setData(modelOriginList);
                }
            }
        });

    }



    private void filterBusiness(String key){
        List<ProductModel> modelList = new ArrayList<>();
        for (int i = 0;i<modelOriginList.size();i++){
            ProductModel mo = modelOriginList.get(i);
            if (mo.getProName().contains(key)){
                modelList.add(mo);
            }
        }
        adapter.setData(modelList);
    }



}
