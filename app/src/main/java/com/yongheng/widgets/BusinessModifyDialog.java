package com.yongheng.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongheng.R;
import com.yongheng.model.BusinessModel;


/**
 * Created by melon on 2017/4/19.
 */

public class BusinessModifyDialog extends Dialog implements View.OnClickListener {

    private OnModifyDialogListener listener;
    private View contentView;
    private Context context;

    private TextView mTvTitle;

    private EditText mEtName;
    private EditText mEtLocation;
    private EditText mEtRemark;

    private TextView mTvOk;
    private TextView mTvCancel;

    private String title;
    private int position;

    private BusinessModel model;
    public BusinessModifyDialog(@NonNull Context context,BusinessModel model, int position, String title, OnModifyDialogListener listener) {
        super(context);
        this.position = position;
        this.context = context;
        this.listener = listener;
        this.title = title;
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        int width = getContext().getResources().getDimensionPixelSize(R.dimen.modify_dialog_width);
        int height = WindowManager.LayoutParams.WRAP_CONTENT;//getContext().getResources().getDimensionPixelSize(R.dimen.pay_dialog_h);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.y = -60;
        lp.height = height;
        lp.width = width;

        getWindow().setAttributes(lp);

        getWindow().getDecorView().setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_modify_business_dialog, null);
        ViewGroup.LayoutParams param = new LinearLayout.LayoutParams(width, height);
        setContentView(contentView, param);
        mTvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
        mEtName = (EditText) contentView.findViewById(R.id.et_modify_name);
        mEtLocation = (EditText) contentView.findViewById(R.id.et_modify_location);
        mEtRemark = (EditText) contentView.findViewById(R.id.et_modify_remark);
        mTvOk = (TextView) contentView.findViewById(R.id.tv_add);
        mTvCancel = (TextView) contentView.findViewById(R.id.tv_cancel);
//        if (type == TYPE_ACCESS_SCH){
//            ((TextView)contentView.findViewById(R.id.tv_pay_dialog_tip1)).setText();
//        }
        if (title != null){
            mTvTitle.setText(title);
        }

        if (model != null){
            mEtName.setHint(model.getBusinessName());
            mEtName.setText(model.getBusinessName());
            mEtLocation.setHint(model.getLocalName());
            mEtLocation.setText(model.getLocalName());
            mEtRemark.setHint(model.getRemark());
            mEtRemark.setText(model.getRemark());
        }


        mTvOk.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add:{
                if (listener!=null){
                    listener.onModifyFinish(
                            mEtName.getText().toString(),
                            mEtLocation.getText().toString(),
                            mEtRemark.getText().toString(),
                            position);
                }
            }
            case R.id.tv_cancel:{
                dismiss();
                break;
            }
        }
    }

    public static interface OnModifyDialogListener{

        void onModifyFinish(String name,String location,String remark,int position);

    }


}
