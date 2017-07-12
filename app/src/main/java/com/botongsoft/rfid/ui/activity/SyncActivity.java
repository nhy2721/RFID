package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.JsonBean.KfJson;
import com.botongsoft.rfid.bean.JsonBean.MjjJson;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.busines.MyBusinessInfo;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.utils.JsonUtils;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.botongsoft.rfid.ui.adapter.SyncAdapter;
import com.botongsoft.rfid.ui.widget.RecyclerViewDecoration.ListViewDescDecoration;
import com.lidroid.xutils.util.LogUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.botongsoft.rfid.R.id.appBarLayout;
import static com.botongsoft.rfid.R.id.toolbar;

/**
 * Created by pc on 2017/7/10.
 */

public class SyncActivity extends BaseActivity {
    private Activity mContext;
    @BindView(appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    SyncAdapter mSyncAdapter;
    List<MyBusinessInfo> myBusinessInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sync);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;

    }

    @Override
    protected void initEvents() {
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mSwipeMenuRecyclerView.setLayoutManager(layout);// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDescDecoration());// 添加分割线。
        //        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        mSyncAdapter = new SyncAdapter(this, getSyncListData());
        mSyncAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mSyncAdapter);
    }

    private List<MyBusinessInfo> getSyncListData() {
        //这里开线程查询数据库有多少的表是有数据更新的。然后将需要更新的对象加入myBusinessInfos集合
        myBusinessInfos = new ArrayList<MyBusinessInfo>();
        KfJson kfJson = (KfJson) JsonUtils.analysisJsonFileByRaw(this, "kf", KfJson.class);
        myBusinessInfos.add(new MyBusinessInfo("库房", "", Integer.valueOf(kfJson.getRes().getRecordCount()), null, kfJson));
        MjjJson mjjJson = (MjjJson) JsonUtils.analysisJsonFileByRaw(this, "mjj", MjjJson.class);
        myBusinessInfos.add(new MyBusinessInfo("密集架", "", Integer.valueOf(mjjJson.getRes().getRecordCount()), null, mjjJson));
//        String mjjgString  = FileUtils.readFileFromAsset(this,"mjjg.sql");
//        myBusinessInfos.add(new MyBusinessInfo("密集架格", "", Integer.valueOf(mjjJson.getRes().getRecordCount()), null, mjjJson));
        return myBusinessInfos;
    }

    public OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }

        @Override
        public void onItemClick(int position, int listSize) {
            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
            Object obj = myBusinessInfos.get(position).getObj();
            switch (obj.getClass().getName()) {
                case "com.botongsoft.rfid.bean.JsonBean.KfJson":
                    LogUtils.d("read KfJson");
                    KfJson kj = (com.botongsoft.rfid.bean.JsonBean.KfJson) obj;
                    for (KfJson.ResBean.RowsBean rowsBean : kj.getRes().getRows()) {
                        Kf kf = new Kf();
                        kf.setId(Integer.valueOf(rowsBean.getId()));
                        kf.setBz(rowsBean.getBz());
                        kf.setMc(rowsBean.getMc());
                        kf.setQzh(rowsBean.getQzh());
                        DBDataUtils.save(kf);
                    }
                    break;
                case "com.botongsoft.rfid.bean.JsonBean.MjjJson":
                    LogUtils.d("read MjjJson");
                    MjjJson mjjJson = (com.botongsoft.rfid.bean.JsonBean.MjjJson) obj;
//                    for (int i = mjjJson.getRes().getRows().size() - 1; i >= 0; i--) {
//
//                    }
                    for (MjjJson.ResBean.RowsBean rowsBean : mjjJson.getRes().getRows()) {
                        Mjj mjj = new Mjj();
                        mjj.setId(Integer.valueOf(rowsBean.getId()));
                        mjj.setNoleft(Integer.valueOf(rowsBean.getNoleft()));
                        mjj.setZlbq(rowsBean.getZlbq());
                        mjj.setZs(Integer.valueOf(rowsBean.getZs()));
                        mjj.setNoright(Integer.valueOf(rowsBean.getNoright()));
                        mjj.setMc(rowsBean.getMc());
                        mjj.setBz(rowsBean.getBz());
                        mjj.setKfid(Integer.valueOf(rowsBean.getKfid()));
                        mjj.setYlbq(rowsBean.getYlbq());
                        mjj.setCs(Integer.valueOf(rowsBean.getCs()));
                        DBDataUtils.save(mjj);
                    }
                    break;
                default:
                    break;
            }
            if (position != -1) {
                //                mDataList.remove(position);
                //                mDownFloorAdapter.notifyItemRemoved(position);
                //                mDownFloorAdapter.notifyItemRangeChanged(position, listSize);
            }
        }
    };


    @Override
    public void onSuccess(BaseResponse baseResponse, int act) {

    }

    @Override
    public void onError(BusinessException e, int act) {

    }
}
