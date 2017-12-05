package com.yongheng.module.egg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yongheng.R;
import com.yongheng.application.BaseActivity;
import com.yongheng.application.MyClient;
import com.yongheng.model.BusinessModel;
import com.yongheng.model.CountryModel;
import com.yongheng.model.ProductModel;
import com.yongheng.network.ResultCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/5/8.
 */

public class AddActivity extends BaseActivity implements View.OnClickListener,OnAddBusinessListener,OnAddProductListener,OnDataFinishListener {

    private TextView mTvUpdate;

    private int count;

    private List<String> indexs = new ArrayList<>();
    private int index = 0;

    private List<CountryModel> countryModelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        initView();
        initData();
    }

    private void initView(){

    }

    private void initData(){

        findViewById(R.id.tv_update_data_business).setOnClickListener(this);
        findViewById(R.id.tv_update_data_product).setOnClickListener(this);
        findViewById(R.id.tv_update_data_xsl).setOnClickListener(this);
        findViewById(R.id.tv_save_data_xsl).setOnClickListener(this);
    }

    private void initBusinessData(){
        index = 0;
        List<BusinessModel> modelList = MyClient.getMyClient().getDataManager().getModelList();
        count = modelList.size();
        handleUpdateBusinessData();
    }

    private void initProductData(){
        index = 0;
        List<BusinessModel> modelList = MyClient.getMyClient().getDataManager().getModelList();
        for (BusinessModel model : modelList){
            count += model.getProductList().size();
        }

        for (int i = 0;i<modelList.size();i++){
            for (int j = 0;j<modelList.get(i).getProductList().size();j++){
                indexs.add(i+"-"+j);
            }
        }
        handleUpdateProductData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_update_data_business:{
                showProgress(R.string.progress_tip,false);
                initBusinessData();
                break;
            }
            case R.id.tv_update_data_product:{
                showProgress(R.string.progress_tip,false);
                initProductData();
                break;
            }
            case R.id.tv_update_data_xsl:{
                MyClient.getMyClient().getDataManager().getDataFromXls2();
                break;
            }
            case R.id.tv_save_data_xsl:{
                handleCountryData();
                break;
            }
        }
    }

    private void handleCountryData(){


        countryModelList = MyClient.getMyClient().getDataManager().getCountryModelList();
        if (countryModelList.isEmpty()){
            return;
        }

        index = 0;
        count = countryModelList.size();

        MyClient.getMyClient().getDataManager().updateDiffTime(countryModelList.get(index),this);
    }

    private void handleUpdateBusinessData(){

        BusinessModel model = MyClient.getMyClient().getDataManager().getModelList().get(index);
        MyClient.getMyClient().getBusinessManager().requestAddBusiness(model,index,this);
    }

    private void handleUpdateProductData(){

        int indexI = Integer.parseInt(indexs.get(index).split("-")[0]);
        int indexJ = Integer.parseInt(indexs.get(index).split("-")[1]);

        try{
            ProductModel model = MyClient.getMyClient().getDataManager().getModelList().get(indexI).getProductList().get(indexJ);
            model.setBusinessId(MyClient.getMyClient().getDataManager().getModelList().get(indexI).getBusinessId());
            MyClient.getMyClient().getProductManager().requestAddProduct(model,indexI,indexJ,this);
        }catch (Exception e){

        }

    }

    @Override
    public void onAddBusinessFinish(int code, String msg, long id, int index) {
        if (code == ResultCode.SUCCESS_0){
            MyClient.getMyClient().getDataManager().getModelList().get(index).setBusinessId(id);
        }

        count --;
        if (count <= 0){
            hideProgress();
            MyClient.getMyClient().getDataManager().saveAlarmDataToFile();
            Toast.makeText(this,"上传成功",Toast.LENGTH_LONG).show();
        }else{
            this.index++;
            handleUpdateBusinessData();
        }
    }

    @Override
    public void onUpdateProductFinish(int code, String msg, long id, int indexI, int indexJ) {
        if (code == ResultCode.SUCCESS_0){
            MyClient.getMyClient().getDataManager().getModelList().get(indexI).getProductList().get(indexJ).setProductId(id);
        }

        count --;
        if (count <= 0){
            hideProgress();
            MyClient.getMyClient().getDataManager().saveAlarmDataToFile();
            Toast.makeText(this,"上传成功",Toast.LENGTH_LONG).show();
        }else{
            index++;
            handleUpdateProductData();
        }
    }

    @Override
    public void onHandleDataFinish() {
        index ++;
        if (index == count){
            return;
        }

        MyClient.getMyClient().getDataManager().updateDiffTime(countryModelList.get(index),this);
    }
}
