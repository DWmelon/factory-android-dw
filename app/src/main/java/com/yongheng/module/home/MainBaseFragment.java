package com.yongheng.module.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yongheng.MainActivity;
import com.yongheng.PageFragment;
import com.yongheng.R;
import com.yongheng.application.BaseActivity;
import com.yongheng.application.DataManager;
import com.yongheng.application.MyClient;
import com.yongheng.model.BusinessModel;
import com.yongheng.module.home.listener.OnLoadDataFinishListener;
import com.yongheng.widgets.AlphabetBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by melon on 2017/4/9.
 */

public class MainBaseFragment extends PageFragment implements AlphabetBar.OnSectionChangedListener,OnLoadDataFinishListener {

    private Toolbar mToolbar;

    private EditText mEtName;

    private ListView mLvList;
    protected BusinessAdapter adapter;

    private HashMap<String,BusinessModel> originMap = new HashMap<>();

    //字母联想相关
    private AlphabetBar mAlphabetBar;
    private TextView mTvLocationIndex;
    private boolean isCancel = false;
    private Handler mHandler = new Handler();




    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return LayoutInflater.from(getActivity()).inflate(R.layout.activity_main,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        initData();
        DataManager dataManager = MyClient.getMyClient().getDataManager();
        if (!dataManager.isLoadFinish()){
            dataManager.registerLoadDataFinishListener(this);
            ((BaseActivity)getActivity()).showProgress(R.string.progress_tip,false);
        }else{
            loadData();
        }

        initListener();
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_menu);
        mToolbar.setTitle("标题");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawerLayout();
            }
        });

        mEtName = (EditText) view.findViewById(R.id.et_search);
        mLvList = (ListView) view.findViewById(R.id.rv_home_list);

        mAlphabetBar = (AlphabetBar) view.findViewById(R.id.ab_listview_index);
        mTvLocationIndex = (TextView) view.findViewById(R.id.tv_location_index);

    }

    private void initData(){


        adapter = new BusinessAdapter(getActivity() , mAlphabetBar);
        mLvList.setAdapter(adapter);


        mAlphabetBar.setListView(mLvList);
        mAlphabetBar.setOnSectionChangedListener(this);

        if (mAlphabetBar != null) {
            mAlphabetBar.requestLayout();
        }

    }

    protected void loadData(){
        originMap = MyClient.getMyClient().getDataManager().getModelHashMap();
        adapter.setData(MyClient.getMyClient().getDataManager().getModelList());
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
                        if (!editable.toString().equals("")) {
                            filterBusiness(editable.toString());
                            mAlphabetBar.setVisibility(View.GONE);
                        } else {
                            adapter.setData(MyClient.getMyClient().getDataManager().getModelList());
                            mAlphabetBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void filterBusiness(String key){
        List<BusinessModel> modelList = new ArrayList<>();
        for (String name : originMap.keySet()){
            if (name.contains(key)){
                modelList.add(originMap.get(name));
            }
        }
        adapter.setData(modelList);
    }

    private void dismissIndexOverlay() {

        if (mTvLocationIndex.getVisibility() == View.VISIBLE) {
            Animation dismiss = AnimationUtils.loadAnimation(getActivity(), R.anim.index_dismiss);
            mTvLocationIndex.startAnimation(dismiss);
            dismiss.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mTvLocationIndex != null) {
                        mTvLocationIndex.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    }

    private Runnable mDismissRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isCancel) {
                return;
            }
            dismissIndexOverlay();
        }
    };

    @Override
    public void onSectionChanged(int section) {
        isCancel = false;
        if (mTvLocationIndex.getVisibility() != View.VISIBLE) {
            mTvLocationIndex.setVisibility(View.VISIBLE);
        }

        String[] strings = (String[]) adapter.getSections();

        if (strings != null && section < strings.length) {
            mTvLocationIndex.setText(strings[section]);
        }
    }

    @Override
    public void onSectionCancel() {
        isCancel = true;
        mHandler.postDelayed(mDismissRunnable, 1000);
    }

    @Override
    public void onLoadDataFinish() {
        if (getActivity() == null)return;
        ((BaseActivity)getActivity()).hideProgress();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
    }




}
