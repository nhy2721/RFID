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
import com.botongsoft.rfid.bean.classity.Epc;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.db.MjgdaSearchDb;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.utils.ListUtils;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.ui.adapter.SelectAdapter;
import com.botongsoft.rfid.ui.widget.RecyclerViewDecoration.ListViewDescDecoration;

import java.util.ArrayList;
import java.util.List;

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
      List<Epc> epcList = (List<Epc>) DBDataUtils.getInfosHasOp(Epc.class,"archiveno","like","%"+q+"%");
        Mjjgda mjjgda = null;
        Mjj mjj = null;
        Kf kf = null;
        String kfname = "";
        String mjjname = "";
        String nLOrR = "";
        if(!ListUtils.isEmpty(epcList)){
            for (Epc ecp : epcList) {
                mjjgda = MjgdaSearchDb.getInfoHasOp(Mjjgda.class, "bm", "=", ecp.getBm() + "",
                        "jlid", "=", ecp.getJlid() + "", "status", "!=", "-1");// 只查不属于被删除的数据
                if (mjjgda != null) {
                    mjjgda.setTitle(ecp.getArchiveno());
                    mjjgda.setEpccode(String.valueOf(ecp.getEpccode()));
                    Mjjg mjjg = (Mjjg) DBDataUtils.getInfo(Mjjg.class, "id", mjjgda.getMjgid() + "");
                    if (mjjg != null) {
                        nLOrR = mjjg.getZy() == 1 ? "左" : "右";
                        mjj = (Mjj) DBDataUtils.getInfo(Mjj.class, "id", mjjg.getMjjid() + "");
                    }
                    if (mjj != null) {
                        mjjname = mjj.getMc() + "/";
                        kf = (Kf) DBDataUtils.getInfo(Kf.class, "id", mjj.getKfid() + "");
                    }

                    if (kf != null) {
                        kfname = kf.getMc() + "/";
                    }
                    String name = kfname + mjjname + nLOrR + "/" + mjjg.getZs() + "组" + mjjg.getCs() + "层";
                    //                        map.put("local", name);//界面显示存放位置
                    mjjgda.setScanInfo(name);
                    mList.add(mjjgda);
                }
            }
            if(ListUtils.isEmpty(mList)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast("档号关键字:"+q+"没查询到数据",1000);
                    }
                });
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isLoadAll", isLoadAll);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mList = null;
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
