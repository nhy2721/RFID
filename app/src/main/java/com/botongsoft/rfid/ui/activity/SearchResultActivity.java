package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.ui.Thread.SearchDBThread;
import com.botongsoft.rfid.ui.adapter.SelectAdapter;
import com.botongsoft.rfid.ui.widget.RecyclerViewDecoration.ListViewDescDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchResultActivity extends BaseActivity {
    //接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
    private static String q;
    private boolean isLoadAll;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.tv_select_num)
    TextView tv_select_num;


    SelectAdapter mAdapter;
    private Activity mContext;


    private ArrayList<Mjjgda> mList = new ArrayList<>();
    private SearchDBThread searchDBThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isLoadAll = savedInstanceState.getBoolean("isLoadAll");
        }
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;

    }

    @Override
    protected void initEvents() {
        q = getIntent().getStringExtra("q");
        setTitle(q);
        searchDb(q);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);// 布局管理器。
        mRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        //        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mRecyclerView.addItemDecoration(new ListViewDescDecoration());// 添加分割线。
        mAdapter = new SelectAdapter(this, mList,tv_select_num);
//        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @OnClick(R.id.btn_sure)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                Bundle bundle = new Bundle();
                mList= mAdapter.getSelectedItem();
                bundle.putSerializable("list",mList);
                this.setResult(RESULT_CANCELED,  this.getIntent().putExtras(bundle));
                this.finish();
                break;
        }
    }

    private void searchDb(String q) {
        searchDBThread = new SearchDBThread(mContext,q);
        searchDBThread.setList(mList);
        searchDBThread.start();
//        mList = searchDBThread.getmList();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isLoadAll", isLoadAll);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mList = null;
        if (searchDBThread != null) {
            searchDBThread.interrupt();//中断线程的方法
            searchDBThread = null;
        }
        finish();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle bundle = new Bundle();
                mList= mAdapter.getSelectedItem();
                bundle.putSerializable("list",mList);
                this.setResult(RESULT_CANCELED,  this.getIntent().putExtras(bundle));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 实例化 Bundle，设置需要传递的参数
            Bundle bundle = new Bundle();
//            mAdapter.updateDataSet(mAdapter.getSelectedItem());
//            mAdapter.notifyDataSetChanged();
            mList= mAdapter.getSelectedItem();
            bundle.putSerializable("list",mList);
            setResult(RESULT_CANCELED, this.getIntent().putExtras(bundle));
            this.finish();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    public void onSuccess(BaseResponse baseResponse, int act) {

    }

    @Override
    public void onError(BusinessException e, int act) {

    }
}
