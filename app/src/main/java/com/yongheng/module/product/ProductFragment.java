package com.yongheng.module.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.yongheng.MainActivity;
import com.yongheng.PageFragment;
import com.yongheng.R;
import com.yongheng.application.MyClient;

/**
 * Created by melon on 2017/6/23.
 */

public class ProductFragment extends PageFragment implements OnSearchProductListener,View.OnClickListener{

    private Toolbar mToolbar;

    private ImageView mIvSearch;
    private EditText mEtSearch;

    private ListView mLvContent;
    private ProductSearchAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);

        mIvSearch = (ImageView)view.findViewById(R.id.iv_search_confirm);
        mEtSearch = (EditText)view.findViewById(R.id.et_search);
        mLvContent = (ListView)view.findViewById(R.id.rv_home_list);

        adapter = new ProductSearchAdapter(getActivity());
        mLvContent.setAdapter(adapter);

        mIvSearch.setOnClickListener(this);

    }

    @Override
    public void onSearchFinish(boolean isSuccess) {
        ((MainActivity)getActivity()).hideProgress();
        if (isSuccess){
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).showProgress(R.string.progress_tip,false);
        String keyWord = mEtSearch.getText().toString();
        MyClient.getMyClient().getProductManager().searchProduct(keyWord,this);
    }
}
