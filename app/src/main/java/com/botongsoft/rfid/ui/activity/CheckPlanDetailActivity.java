package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.Constant;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.utils.LogUtils;
import com.botongsoft.rfid.ui.adapter.CheckPlanFragmentAdapter;
import com.botongsoft.rfid.ui.fragment.BaseFragment;
import com.botongsoft.rfid.ui.fragment.ScanCheckPlanDetailFragment;
import com.botongsoft.rfid.ui.fragment.ScanCheckPlanListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.botongsoft.rfid.R.id.toolbar;

/**
 * 盘点 盘点明细
 * Created by pc on 2017/6/12.
 */
public class CheckPlanDetailActivity extends BaseActivity {
    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    private int index;
    private String fw;
    private int pdid;
    private Activity mContext;
    private List<BaseFragment> fragments;
    CheckPlanFragmentAdapter checkPlanFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_checkplandetail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
    }

    private void initView() {
        fragments = new ArrayList<>();
        fragments.add(ScanCheckPlanDetailFragment.newInstance(Constant.TYPE_HOT_RANKING, pdid, fw));//扫描操作
        fragments.add(ScanCheckPlanListFragment.newInstance(Constant.TYPE_RETAINED_RANKING, pdid, fw));//显示盘点范围与格子
        checkPlanFragmentAdapter = new CheckPlanFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.removeAllViews();
        mViewPager.removeAllViewsInLayout();
        mViewPager.setAdapter(checkPlanFragmentAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setupWithViewPager(mViewPager);
        setPageChangeListener();
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
    }

    private void setPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtils.e("onPageScrollStateChanged", state + "");
                if (state == 0) {
                    checkPlanFragmentAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    protected void initEvents() {
        index = getIntent().getIntExtra("index", 0);
        setTitle(getIntent().getStringExtra("title"));
        fw = getIntent().getStringExtra("fw");
        pdid = getIntent().getIntExtra("pdid", 0);
        mToolbar.setSubtitle(pdid + "");//子标题
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止查询
        finish();
    }

    @Override
    public void onSuccess(BaseResponse baseResponse, int act) {

    }

    @Override
    public void onError(BusinessException e, int act) {

    }

}
