package com.yongheng.module.home.leftview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongheng.R;
import com.yongheng.module.egg.AddActivity;

/**
 * Created by melon on 2017/5/8.
 */

public class HomeLeftContentAdapter extends RecyclerView.Adapter<HomeLeftContentAdapter.ViewHolder> {

    private final static int TYPE_HEADER = 0;
    private final static int TYPE_ITEM = 1;

    private Context mContext;

    public HomeLeftContentAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER){
            view = LayoutInflater.from(mContext).inflate(R.layout.header_left_drawer,parent,false);
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_left_drawer,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0){
            holder.mTvContext.setText(mContext.getString(R.string.app_name));
            holder.mIvIcon.setImageResource(R.mipmap.ic_launcher);
        } else if (position == 1){
            holder.mTvContext.setText("备份数据");
            holder.mIvIcon.setImageResource(R.drawable.icon_feedback);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AddActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_HEADER;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTvContext;
        public ImageView mIvIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            mTvContext = (TextView)itemView.findViewById(R.id.tv_left_drawer_tip);
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_left_drawer_icon);

        }
    }

}
