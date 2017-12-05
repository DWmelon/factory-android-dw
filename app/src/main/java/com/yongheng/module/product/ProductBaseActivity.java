package com.yongheng.module.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yongheng.R;
import com.yongheng.application.BaseActivity;
import com.yongheng.application.MyClient;
import com.yongheng.module.egg.OnAddProductListener;
import com.yongheng.module.home.listener.OnUpdateDataFinishListener;
import com.yongheng.model.ProductModel;
import com.yongheng.network.ResultCode;
import com.yongheng.widgets.ProductModifyDialog;
import com.yongheng.widgets.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/4/8.
 */

public class ProductBaseActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,ProductModifyDialog.OnProductModifyDialogListener,OnAddProductListener {


    protected Toolbar mToolbar;
    
    private RecyclerView mRvProduce;
    protected ProductAdapter adapter;

    protected EditText mEtName;

    protected Long businessId;
    private String businessName;

    private ProductModel tempProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initIntent();
        initView();
        initData();
    }

    private void initIntent(){
        if (getIntent() == null || getIntent().getExtras() == null){
            finish();
        }
        Bundle bundle = getIntent().getExtras();
        businessId = bundle.getLong("businessId",-1);
        if (businessId == -1){
            finish();
        }
        businessName = bundle.getString("businessName","");
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbar.setTitle(businessName);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_product);
        mToolbar.setOnMenuItemClickListener(this);


        mRvProduce = (RecyclerView)findViewById(R.id.rv_product_list);
        mEtName = (EditText) findViewById(R.id.et_search);
    }

    private void initData(){

        mRvProduce.setLayoutManager(new LinearLayoutManager(this));
        mRvProduce.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL,
                getResources().getDimensionPixelOffset(R.dimen.margin_5),
                getResources().getColor(R.color.common_bg)));


        adapter = new ProductAdapter(this);
        mRvProduce.setAdapter(adapter);
        
    }


    /**
     * 添加按钮监听
     * @param menuItem
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_add:{
                ProductModifyDialog dialog = new ProductModifyDialog(this,null,0,"添加产品",this);
                dialog.show();
                return true;
            }
            default:
                return false;
        }
    }


    /**
     * 添加产品对话框对调
     * @param name
     * @param price
     * @param position
     */
    @Override
    public void onModifyFinish(String name, String price, int position) {
        if (name.isEmpty()||price.isEmpty()){
            return;
        }
        showProgress();

        tempProduct = new ProductModel();
        tempProduct.setProName(name);
        tempProduct.setPrice(price);
        tempProduct.setBusinessId(businessId);
        MyClient.getMyClient().getProductManager().requestAddProduct(tempProduct,0,0,this);

    }

    /**
     * 添加产品后台接口回调
     * @param code
     * @param msg
     * @param id
     * @param indexI
     * @param indexJ
     */
    @Override
    public void onUpdateProductFinish(int code, String msg, long id, int indexI, int indexJ) {
        if (code != ResultCode.SUCCESS_0){
            Toast.makeText(this,msg+":"+code,Toast.LENGTH_LONG).show();
            return;
        }

        tempProduct.setProductId(id);
        MyClient.getMyClient().getProductManager().addProduct(tempProduct);
        adapter.notifyDataSetChanged();
        Toast.makeText(this,"添加产品成功",Toast.LENGTH_LONG).show();

        hideProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyClient.getMyClient().getProductManager().release();
    }

}

