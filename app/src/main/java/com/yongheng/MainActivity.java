package com.yongheng;

import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.yongheng.application.BaseActivity;
import com.yongheng.module.home.MainFragment;
import com.yongheng.module.home.leftview.HomeLeftContentAdapter;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvLeftContent;
    private HomeLeftContentAdapter adapter;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_drawer);
        TabFragment mTabFragment = (TabFragment) getSupportFragmentManager().findFragmentById(R.id.f_tab_fragment);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            //将侧边栏顶部延伸至status bar
            drawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
            drawerLayout.setClipToPadding(false);
        }

        mRvLeftContent = (RecyclerView) findViewById(R.id.left_drawer);
        mRvLeftContent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeLeftContentAdapter(this);
        mRvLeftContent.setAdapter(adapter);
    }

    public void openDrawerLayout(){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

}
