package com.yongheng.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yongheng.R;
import com.yongheng.application.BaseActivity;
import com.yongheng.application.MyClient;
import com.yongheng.model.BusinessModel;
import com.yongheng.module.egg.OnAddBusinessListener;
import com.yongheng.module.home.listener.OnUpdateDataFinishListener;
import com.yongheng.utils.CharUtil;
import com.yongheng.widgets.BusinessModifyDialog;

/**
 * Created by melon on 2017/5/18.
 */

public class MainFragment extends MainBaseFragment implements OnUpdateDataFinishListener,BusinessModifyDialog.OnModifyDialogListener,OnAddBusinessListener {

    private BusinessModel tempBusiness;

    private static MainFragment fragment;

    public static MainFragment newInstant(Bundle bundle){
        if (fragment == null){
            fragment = new MainFragment();
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                ((BaseActivity)getActivity()).showProgress(R.string.progress_tip,false);
                MyClient.getMyClient().getBusinessManager().requestBusinessList(null,this);
                return true;
            case R.id.action_add:{
                BusinessModifyDialog dialog = new BusinessModifyDialog(getActivity(),null,0,"添加客户",MainFragment.this);
                dialog.show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 更新客户信息成功后台接口回调
     * @param code
     * @param msg
     */
    @Override
    public void onUpdateFinish(int code, String msg) {
        if (getActivity() == null){
            return;
        }
        ((BaseActivity)getActivity()).hideProgress();
        if (code == 0){
            Toast.makeText(getActivity(),"更新数据成功",Toast.LENGTH_LONG).show();
            loadData();
            adapter.setData(MyClient.getMyClient().getDataManager().getModelList());
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(getActivity(),"更新数据失败:"+code,Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 添加客户对话窗回调
     * @param name
     * @param location
     * @param remark
     * @param position
     */
    @Override
    public void onModifyFinish(String name, String location, String remark, int position) {
        if (name.isEmpty()){
            return;
        }
        ((BaseActivity)getActivity()).showProgress();

        tempBusiness = new BusinessModel();
        tempBusiness.setBusinessName(name);
        tempBusiness.setLocalName(location);
        tempBusiness.setRemark(remark);
        MyClient.getMyClient().getBusinessManager().requestAddBusiness(tempBusiness,position,this);
    }

    /**
     * 添加客户成功后台接口回调
     * @param code
     * @param msg
     * @param id
     * @param index
     */
    @Override
    public void onAddBusinessFinish(int code, String msg, long id, int index) {
        if (getActivity() == null)return;

        tempBusiness.setBusinessId(id);
        tempBusiness.setFirstPy(CharUtil.cn2py(tempBusiness.getBusinessName()));

        MyClient.getMyClient().getBusinessManager().addBusiness(tempBusiness);

        adapter.setSectionInfo();
        adapter.notifyDataSetChanged();

        ((BaseActivity)getActivity()).hideProgress();
    }
}
