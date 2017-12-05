package com.yongheng.module.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongheng.R;
import com.yongheng.application.BaseActivity;
import com.yongheng.application.MyClient;
import com.yongheng.model.ProductModel;
import com.yongheng.module.home.listener.OnDeleteListener;
import com.yongheng.widgets.ProductModifyDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/4/9.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements ProductModifyDialog.OnProductModifyDialogListener,OnUpdateProductListener,OnDeleteListener {

    private Context context;
    private List<ProductModel> dataList = new ArrayList<>();

    private ProductModel tempProduct = new ProductModel();

    public ProductAdapter(Context context){
        this.context = context;
        dataList = MyClient.getMyClient().getProductManager().getProductModelList();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_product_list,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ProductModel model = dataList.get(position);
        holder.name.setText(model.getProName());
        holder.price.setText(model.getPrice());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductModifyDialog dialog = new ProductModifyDialog(context,model,position,null,ProductAdapter.this);
                dialog.show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((BaseActivity)context).showCommonAlert(R.string.dialog_title,R.string.product_delete_tip, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((BaseActivity)context).showProgress(R.string.progress_tip,false);
                        MyClient.getMyClient().getProductManager().deleteProduct(model.getProductId(),position,ProductAdapter.this);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((BaseActivity)context).hideProgress();
                    }
                });
                return true;
            }
        });

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void setData(List<ProductModel> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public void onModifyFinish(String name, String price, int position) {
        ProductModel model = dataList.get(position);
        boolean isModify = false;

        if (!model.getProName().equals(name)){
            isModify = true;
        }

        if (!model.getPrice().equals(price)){
            isModify = true;
        }


        if (isModify){
            tempProduct.setProductId(model.getProductId());
            tempProduct.setProName(name);
            tempProduct.setPrice(price);

            ((BaseActivity)context).showProgress(R.string.progress_tip,false);
            MyClient.getMyClient().getProductManager().updateProduct(tempProduct,position,ProductAdapter.this);
        }

    }

    @Override
    public void onUpdateProductFinish(int code, String msg, int position) {
        ((BaseActivity)context).hideProgress();
        if (code != 0){
            Toast.makeText(context,msg+":"+code,Toast.LENGTH_LONG).show();
            return;
        }

        ProductModel model = dataList.get(position);
        model.setProName(tempProduct.getProName());
        model.setPrice(tempProduct.getPrice());

        notifyDataSetChanged();

        Toast.makeText(context,"修改成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteFinish(int code, String msg, int position) {
        ((BaseActivity)context).hideProgress();
        if (code != 0){
            Toast.makeText(context,msg+":"+code,Toast.LENGTH_LONG).show();
            return;
        }

        dataList.remove(position);
        notifyDataSetChanged();
        Toast.makeText(context,"删除成功",Toast.LENGTH_LONG).show();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        ImageView edit;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            edit = (ImageView) itemView.findViewById(R.id.iv_edit);
        }
    }

}
