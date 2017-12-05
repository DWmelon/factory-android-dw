package com.yongheng.module.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yongheng.R;
import com.yongheng.application.MyClient;
import com.yongheng.model.ProductJoinModel;

/**
 * Created by melon on 2017/6/24.
 */

public class ProductSearchAdapter extends BaseAdapter {

    private Context context;

    public ProductSearchAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return MyClient.getMyClient().getProductManager().getProductJoinModelList().size();
    }

    @Override
    public Object getItem(int i) {
        return MyClient.getMyClient().getProductManager().getProductJoinModelList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.adapter_product_search,viewGroup,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        performData(viewHolder,i);
        return view;
    }

    private void performData(ViewHolder viewHolder,int position){
        ProductJoinModel model = (ProductJoinModel) getItem(position);

        viewHolder.mTvBusinessName.setText(model.getBusinessName());
        viewHolder.mTvProductName.setText(model.getProName());
        viewHolder.mTvPrice.setText(model.getPrice());
    }

    public class ViewHolder{

        public TextView mTvProductName;
        public TextView mTvBusinessName;
        public TextView mTvPrice;


        public ViewHolder(View view){
            mTvBusinessName = (TextView)view.findViewById(R.id.tv_business_name);
            mTvProductName = (TextView)view.findViewById(R.id.tv_product_name);
            mTvPrice = (TextView)view.findViewById(R.id.tv_price);

        }

    }

}
