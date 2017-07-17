package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.JsonBean.CountJson;
import com.botongsoft.rfid.bean.JsonBean.KfJson;
import com.botongsoft.rfid.bean.JsonBean.MjjJson;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.busines.FilesBusines;
import com.botongsoft.rfid.busines.MyBusinessInfo;
import com.botongsoft.rfid.common.constants.Constant;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.service.http.BusinessResolver;
import com.botongsoft.rfid.common.service.http.NetUtils;
import com.botongsoft.rfid.common.utils.JsonUtils;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.common.utils.UIUtils;
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
    List<MyBusinessInfo> myBusinessInfos = new ArrayList<MyBusinessInfo>();
    private String[] syncArry;
    private static final int CONN_SUCCESS = 0;
    private static final int CONN_UNSUCCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sync);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;
        initDatas();
    }

    private void initDatas() {

        new Thread(networkTask).start();
    }

    @Override
    protected void initEvents() {
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mSwipeMenuRecyclerView.setLayoutManager(layout);// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDescDecoration());// 添加分割线。
        //        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        mSyncAdapter = new SyncAdapter(this, myBusinessInfos);
        mSyncAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mSyncAdapter);
    }

    private List<MyBusinessInfo> getSyncListData() {
        //这里开线程查询数据库有多少的表是有数据更新的。然后将需要更新的对象加入myBusinessInfos集合
        KfJson kfJson = (KfJson) JsonUtils.analysisJsonFileByRaw(this, "kf", KfJson.class);
        myBusinessInfos.add(new MyBusinessInfo("库房", null, Integer.valueOf(kfJson.getRes().getRecordCount()), kfJson));
        MjjJson mjjJson = (MjjJson) JsonUtils.analysisJsonFileByRaw(this, "mjj", MjjJson.class);
        myBusinessInfos.add(new MyBusinessInfo("密集架", null, Integer.valueOf(mjjJson.getRes().getRecordCount()), mjjJson));
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
            switch (myBusinessInfos.get(position).getName()) {
                case "库房":
                    LogUtils.d("read KfJson");
//                    KfJson kj = (com.botongsoft.rfid.bean.JsonBean.KfJson) obj;
//                    for (KfJson.ResBean.RowsBean rowsBean : kj.getRes().getRows()) {
//                        Kf kf = new Kf();
//                        kf.setId(Integer.valueOf(rowsBean.getId()));
//                        kf.setBz(rowsBean.getBz());
//                        kf.setMc(rowsBean.getMc());
//                        kf.setQzh(rowsBean.getQzh());
//                        DBDataUtils.save(kf);
//                    }
                    break;
                case "密集架":
                    LogUtils.d("read MjjJson");
//                    MjjJson mjjJson = (com.botongsoft.rfid.bean.JsonBean.MjjJson) obj;
//                    //                    for (int i = mjjJson.getRes().getRows().size() - 1; i >= 0; i--) {
//                    //
//                    //                    }
//                    //根据下载的json文件保存数据库 （要根据下载的数据状态 增 删 改 来增加数据库）
//                    for (MjjJson.ResBean.RowsBean rowsBean : mjjJson.getRes().getRows()) {
//                        Mjj mjj = new Mjj();
//                        mjj.setId(Integer.valueOf(rowsBean.getId()));
//                        mjj.setNoleft(Integer.valueOf(rowsBean.getNoleft()));
//                        mjj.setZlbq(rowsBean.getZlbq());
//                        mjj.setZs(Integer.valueOf(rowsBean.getZs()));
//                        mjj.setNoright(Integer.valueOf(rowsBean.getNoright()));
//                        mjj.setMc(rowsBean.getMc());
//                        mjj.setBz(rowsBean.getBz());
//                        mjj.setKfid(Integer.valueOf(rowsBean.getKfid()));
//                        mjj.setYlbq(rowsBean.getYlbq());
//                        mjj.setCs(Integer.valueOf(rowsBean.getCs()));
//                        DBDataUtils.save(mjj);
//                    }
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
    public void onSuccess(BaseResponse response, int act) {
        //        if (baseResponse.isSuccess() && !TextUtils.isEmpty(baseResponse.res.rows)) {
        //            int recordCount = baseResponse.res.recordCount;
        //            List<NewsInfo> newsinfo = JSONObject.parseArray(baseResponse.res.rows,
        //                    NewsInfo.class);
        //            if (current_pagesize == 1) {
        //                beanList.clear();
        //            }
        //            beanList.addAll(newsinfo);
        //            newsAdapter.setCurList(beanList);
        //        }
        if (response != null) {
            if (act == Constant.ACT_GET_WORDS) {//服务器返回更新数
                if (response.isSuccess()) {
                    try {
                        List<CountJson> countJsons =   JSONObject.parseArray(response.res.rows,CountJson.class);
                        myBusinessInfos.get(0).setListSize(Integer.valueOf(countJsons.get(0).kf));
                        myBusinessInfos.get(1).setListSize(Integer.valueOf(countJsons.get(0).mjj));
                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == Constant.ACT_GET_KF) {

            }
        }
    }

    @Override
    public void onError(BusinessException e, int act) {
        ToastUtils.showLong(e.getMessage());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case CONN_SUCCESS:
                    syncArry = UIUtils.getContext().getResources().getStringArray(R.array.sync_array);
                    for (int i = 0; i < syncArry.length; i++) {
                        myBusinessInfos.add(new MyBusinessInfo(syncArry[i], 1000 + i, 0, null));
                        //                    switch (syncArry[i]) {
                        //                        case "库房":
                        //                            FilesBusines.getWorkState(this, 3,Constant.ACT_TYPE_NEWIMAGE, this);
                        //                            break;
                        //                        case "密集架":
                        //                            break;
                        //                        case "密集格":
                        //                            break;
                        //                        case "档案":
                        //                            break;
                        //                        case "盘点表":
                        //                            break;
                        //                        case "盘点记录":
                        //                            break;
                        //                        case "盘点纠错":
                        //                            break;
                        //                        default:
                        //                            break;
                        //                    }
                        //                    mContext.runOnUiThread(new Runnable() {
                        //                        @Override
                        //                        public void run() {
                        //
                        //                        }
                        //                    });
                    }
                    FilesBusines.getWorkState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext);
                    break;
                case CONN_UNSUCCESS:
                    new AlertDialog.Builder(mContext)
                            .setTitle("服务器无法访问")
                            .setMessage("情检查网络是否畅通")
                            .create().show();
                    break;
                default:
                    super.handleMessage(msg);//这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                    break;
            }
        }

    };

    /**
     * 网络操作相关的子线程
     */

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            Message msg = handler.obtainMessage();
            Bundle data = new Bundle();

            boolean st = NetUtils.isConnByHttp(Constant.DOMAINTEST);// 先判断对方服务器是否存在
            if (st) {
                msg.what = CONN_SUCCESS;
                handler.sendMessage(msg);
            } else {
                msg.what = CONN_UNSUCCESS;
                handler.sendMessage(msg);
            }

        }
    };
}
