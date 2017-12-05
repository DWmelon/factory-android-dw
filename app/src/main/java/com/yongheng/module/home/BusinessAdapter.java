package com.yongheng.module.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.yongheng.R;
import com.yongheng.application.BaseActivity;
import com.yongheng.application.MyClient;
import com.yongheng.model.BusinessModel;
import com.yongheng.module.home.listener.OnDeleteListener;
import com.yongheng.module.home.listener.OnUpdateBusinessListener;
import com.yongheng.module.product.ProductActivity;
import com.yongheng.module.product.ProductBaseActivity;
import com.yongheng.widgets.AlphabetBar;
import com.yongheng.widgets.BusinessModifyDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by melon on 2017/4/9.
 */

public class BusinessAdapter extends BaseAdapter implements View.OnClickListener,BusinessModifyDialog.OnModifyDialogListener, SectionIndexer, OnUpdateBusinessListener,OnDeleteListener {

    private Context context;
    private List<BusinessModel> businessModelList = new ArrayList<>();

    private ArrayList<String> mSection = new ArrayList<>();
    private String[] mSections = new String[]{};
    private HashMap<Integer,Integer> mSection2Position = new HashMap<>();
    private HashMap<Integer,Integer> mPosition2Section = new HashMap<>();

    private BusinessModel tempBusiness = new BusinessModel();

    public BusinessAdapter(Context context, AlphabetBar alphabetBar){
        this.context = context;

        setSectionInfo();
        alphabetBar.requestLayout();
    }

    public void setSectionInfo(){

        mPosition2Section.clear();
        mSection2Position.clear();
        mSection.clear();

        final ArrayList<BusinessModel> tempProvince = new ArrayList<>();
        for (BusinessModel item : businessModelList) {
            tempProvince.add(item);
//            mName2LocationInfoMap.put(item.getName(),item);
            if (!mSection.contains(item.getFirstChar())) {
                mSection2Position.put(mSection.size(), tempProvince.size());
                mPosition2Section.put(tempProvince.size(), mSection.size());
                mSection.add(item.getFirstChar());
            }
        }


        if(mSection != null){
            mSections = new String[mSection.size()];
            for(int i = 0;i < mSections.length;i++){
                mSections[i] = mSection.get(i);
            }
        }
    }

    @Override
    public int getCount() {
        return businessModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return businessModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_select_sch_listview_another,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.contentView = view;
            viewHolder.name = (TextView) view.findViewById(R.id.mSchNameTv);
            viewHolder.tip = (TextView) view.findViewById(R.id.tv_tip);
            viewHolder.edit = (ImageView) view.findViewById(R.id.iv_edit);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        performData(viewHolder,i);
        return view;
    }

    private void performData(ViewHolder holder, final int position){
        final BusinessModel model = businessModelList.get(position);
        holder.name.setText(model.getBusinessName());
        holder.tip.setText(context.getString(R.string.business_remark,model.getRemark()));

        holder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("businessId",model.getBusinessId());
                bundle.putString("businessName",model.getBusinessName());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.contentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((BaseActivity)context).showCommonAlert(R.string.dialog_title,R.string.business_delete_tip, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((BaseActivity)context).showProgress(R.string.progress_tip,false);
                        MyClient.getMyClient().getBusinessManager().requestDeleteBusiness(model.getBusinessId(),position,BusinessAdapter.this);
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
        holder.edit.setTag(position);
        holder.edit.setOnClickListener(this);
    }


    public void setData(List<BusinessModel> modelList){
        businessModelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_edit:{
                int position = (int)view.getTag();
                BusinessModifyDialog dialog = new BusinessModifyDialog(context,businessModelList.get(position),position,"修改备注",this);
                dialog.show();
                break;
            }

        }

    }

    @Override
    public void onModifyFinish(String name,String location,String remark,int position) {
        BusinessModel model = businessModelList.get(position);
        boolean isModify = false;

        if (!model.getBusinessName().equals(name)){
            isModify = true;
        }

        if (!model.getLocalName().equals(location)){
            isModify = true;
        }

        if (!model.getRemark().equals(remark)){
            isModify = true;
        }


        if (isModify){
            tempBusiness.setBusinessId(model.getBusinessId());
            tempBusiness.setBusinessName(name);
            tempBusiness.setLocalName(location);
            tempBusiness.setRemark(remark);
            ((BaseActivity)context).showProgress(R.string.progress_tip,false);
            MyClient.getMyClient().getBusinessManager().requestUpdateBusiness(tempBusiness,position,this);


        }

    }

    @Override
    public Object[] getSections() {
        return mSections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSection2Position.get(Integer.valueOf(sectionIndex));
    }

    @Override
    public int getSectionForPosition(int position) {
        return mPosition2Section.get(Integer.valueOf(position));
    }

    @Override
    public void onDeleteFinish(int code, String msg,int position) {
        if (context == null)return;
        ((BaseActivity)context).hideProgress();
        if (code != 0){
            Toast.makeText(context,code+":"+msg,Toast.LENGTH_LONG).show();
            return;
        }

        businessModelList.remove(position);
        MyClient.getMyClient().getBusinessManager().removeBusiness(position);

        setSectionInfo();
        notifyDataSetChanged();
        Toast.makeText(context,"删除成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateBusinessFinish(int code, String msg, int position) {
        ((BaseActivity)context).hideProgress();
        if (code != 0){
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            return;
        }

        BusinessModel model = businessModelList.get(position);

        model.setBusinessName(tempBusiness.getBusinessName());
        model.setLocalName(tempBusiness.getLocalName());
        model.setRemark(tempBusiness.getRemark());

        MyClient.getMyClient().getDataManager().saveAlarmDataToFile();
        notifyDataSetChanged();

        Toast.makeText(context,R.string.update_success,Toast.LENGTH_LONG).show();
    }

    public class ViewHolder{
        View contentView;
        TextView name;
        TextView tip;
        ImageView edit;
    }

    @Override
    public void notifyDataSetChanged() {
        setSectionInfo();
        super.notifyDataSetChanged();
    }
}
