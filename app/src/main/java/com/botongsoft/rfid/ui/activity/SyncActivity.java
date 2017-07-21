package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.JsonBean.CountJson;
import com.botongsoft.rfid.bean.JsonBean.KfJson;
import com.botongsoft.rfid.bean.JsonBean.MjjJson;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.busines.FilesBusines;
import com.botongsoft.rfid.busines.MyBusinessInfo;
import com.botongsoft.rfid.common.constants.Constant;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.service.http.BusinessResolver;
import com.botongsoft.rfid.common.service.http.NetUtils;
import com.botongsoft.rfid.common.utils.JsonUtils;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.common.utils.UIUtils;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.botongsoft.rfid.ui.Handler.WriteKfDBThread;
import com.botongsoft.rfid.ui.Handler.WriteMjgDBThread;
import com.botongsoft.rfid.ui.Handler.WriteMjjDBThread;
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
    private static final int INIT_DOWORK = 2;
    private static final int PUT_WROK_KF = 1002;
    List<Kf> kfList;
    private boolean isOnScreen;//是否在屏幕上
    private HandlerThread mCheckMsgThread;//Handler线程池
    //后台运行的handler
    private Handler mCheckMsgHandler;
    //与UI线程管理的handler
    private Handler mHandler;
    private static final int BackThread_DOWORK = 9999;
    private static final int BackThread_GETKF = 1000;
    private static final int BackThread_PUTKF = 1001;
    private static final int BackThread_GETMJJ = 1003;
    private static final int BackThread_GETMJJG = 1004;
    //传递后台运行消息队列
    Message backThreadmsg;
    Message uiMsg;
    private Thread networkThread;//网络操作相关的子线程
    private WriteKfDBThread wrKfDbThread;//数据库操作相关
    private WriteMjjDBThread wrMjjDbThread;//数据库操作相关
    private WriteMjgDBThread wrMjgDbThread;//数据库操作相关
    static int kfAnchor;
    static int mjjAnchor;
    static int mjjgAnchor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sync);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;
        initUiHandler();
        initDatas();
    }

    private void initUiHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                switch (msg.what) {
                    case CONN_SUCCESS:
                        syncArry = UIUtils.getContext().getResources().getStringArray(R.array.sync_array);
                        for (int i = 0; i < syncArry.length; i++) {
                            myBusinessInfos.add(new MyBusinessInfo(syncArry[i], 1000 + i, 0, null));
                        }
                        backThreadmsg = mCheckMsgHandler.obtainMessage();
                        LogUtils.d("BackThread_DOWORK;");
                        backThreadmsg.what = BackThread_DOWORK;
                        mCheckMsgHandler.sendMessage(backThreadmsg);
                        //                        FilesBusines.getWorkState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext,kfAnchor,mjjAnchor,mjjgAnchor);
                        break;
                    case INIT_DOWORK:
                        FilesBusines.getWorkState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, kfAnchor, mjjAnchor, mjjgAnchor);
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
    }

    private void initDatas() {
        networkThread = new Thread(networkTask);
        networkThread.start();
        if (mCheckMsgThread == null || !mCheckMsgThread.isAlive()) {
            mCheckMsgThread = new HandlerThread("BackThread");// 创建一个BackHandlerThread对象，它是一个线程
            mCheckMsgThread.start();// 启动线程
            initBackThread();
        }
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
            Toast.makeText(mContext, "我是第" + position + "条点击同步。", Toast.LENGTH_SHORT).show();
            switch (myBusinessInfos.get(position).getName()) {
                case "库房":
                    LogUtils.d("read KfJson " + myBusinessInfos.get(position).getListSize());
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    LogUtils.d("BackThread_GETKF;");
                    backThreadmsg.what = BackThread_GETKF;
                    mCheckMsgHandler.sendMessage(backThreadmsg);

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
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    LogUtils.d("BackThread_GETMJJ;");
                    backThreadmsg.what = BackThread_GETMJJ;
                    mCheckMsgHandler.sendMessage(backThreadmsg);
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
                case "密集格":
                    LogUtils.d("read MjjJson");
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    LogUtils.d("BackThread_GETMJJG;");
                    backThreadmsg.what = BackThread_GETMJJG;
                    mCheckMsgHandler.sendMessage(backThreadmsg);
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
                        List<CountJson> countJsons = JSONObject.parseArray(response.res.rows, CountJson.class);
                        myBusinessInfos.get(0).setListSize(Integer.valueOf(countJsons.get(0).kf));
                        myBusinessInfos.get(1).setListSize(Integer.valueOf(countJsons.get(0).mjj));
                        myBusinessInfos.get(2).setListSize(Integer.valueOf(countJsons.get(0).mjjg));
                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == Constant.ACT_GET_KF) {
                if (response.isSuccess()) {
                    try {
                        List<Kf> kfJsonList = JSON.parseArray(response.res.rows, Kf.class);
                        if (kfJsonList != null && !kfJsonList.isEmpty()) {
                            wrKfDbThread = new WriteKfDBThread();
                            wrKfDbThread.setList(kfJsonList);
                            wrKfDbThread.start();
                        }
                        myBusinessInfos.get(0).setListSize(0);
                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else if (act == Constant.ACT_GET_MJJ) {
                if (response.isSuccess()) {
                    try {
                        List<Mjj> mjjJsonList = JSON.parseArray(response.res.rows, Mjj.class);
                        if (mjjJsonList != null && !mjjJsonList.isEmpty()) {
                            wrMjjDbThread = new WriteMjjDBThread();
                            wrMjjDbThread.setList(mjjJsonList);
                            wrMjjDbThread.start();
                        }
                        myBusinessInfos.get(1).setListSize(0);
                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == Constant.ACT_GET_MJJG) {
                if (response.isSuccess()) {
                    try {
                        List<Mjjg> mjjgJsonList = JSON.parseArray(response.res.rows, Mjjg.class);
                        if (mjjgJsonList != null && !mjjgJsonList.isEmpty()) {
                            wrMjgDbThread = new WriteMjgDBThread();
                            wrMjgDbThread.setList(mjjgJsonList);
                            wrMjgDbThread.start();
                        }
                        myBusinessInfos.get(2).setListSize(0);
                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    @Override
    public void onError(BusinessException e, int act) {
        ToastUtils.showLong(e.getMessage());
    }

    private void initBackThread() {
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.e("Handler BackThread--->", String.valueOf(Thread.currentThread().getName()));
                switch (msg.what) {
                    case BackThread_DOWORK:
                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        Kf kfInfo = (Kf) DBDataUtils.getInfoHasOp(Kf.class, "anchor", ">=", "0");
                        if (kfInfo == null) {
                            kfAnchor = 0;
                        } else {
                            kfAnchor = Integer.valueOf(kfInfo.getAnchor());
                        }
                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        Mjj mjjInfo = (Mjj) DBDataUtils.getInfoHasOp(Mjj.class, "anchor", ">=", "0");
                        if (mjjInfo == null) {
                            mjjAnchor = 0;
                        } else {
                            mjjAnchor = Integer.valueOf(mjjInfo.getAnchor());
                        }

                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        Mjjg mjjgInfo = (Mjjg) DBDataUtils.getInfoHasOp(Mjjg.class, "anchor", ">=", "0");
                        if (mjjgInfo == null) {
                            mjjgAnchor = 0;
                        } else {
                            mjjgAnchor = Integer.valueOf(mjjgInfo.getAnchor());
                        }
                        uiMsg = mHandler.obtainMessage();
                        uiMsg.what = INIT_DOWORK;
                        mHandler.sendMessage(uiMsg);
                        break;
                    case BackThread_GETKF:

                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        //                        Kf kfInfo = (Kf) DBDataUtils.getInfoHasOp(Kf.class, "anchor", ">=", "0");
                        //                        if (kfInfo == null) {
                        //                            kfAnchor = 0;
                        //                        } else {
                        //                            kfAnchor = Integer.valueOf(kfInfo.getAnchor());
                        //                        }
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, kfAnchor, BackThread_GETKF);
                        break;
                    case BackThread_GETMJJ:

                        //                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        //                        Mjj mjjInfo = (Mjj) DBDataUtils.getInfoHasOp(Mjj.class, "anchor", ">=", "0");
                        //                        if (mjjInfo == null) {
                        //                            mjjAnchor = 0;
                        //                        } else {
                        //                            mjjAnchor = Integer.valueOf(mjjInfo.getAnchor());
                        //                        }
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, mjjAnchor, BackThread_GETMJJ);
                        break;
                    case BackThread_GETMJJG:
                        //                        int mjjgAnchor;
                        //                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        //                        Mjjg mjjgInfo = (Mjjg) DBDataUtils.getInfoHasOp(Mjjg.class, "anchor", ">=", "0");
                        //                        if (mjjgInfo == null) {
                        //                            mjjgAnchor = 0;
                        //                        } else {
                        //                            mjjgAnchor = Integer.valueOf(mjjgInfo.getAnchor());
                        //                        }
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, mjjgAnchor, BackThread_GETMJJG);
                        break;
                    case BackThread_PUTKF:
                        //                        kfList = (List<Kf>) DBDataUtils.getInfos(Kf.class, "status", "<", "9");
                        //                        if (kfList.size() > 0) {
                        //                            for (Kf kf : kfList) {
                        //                                boolean st = NetUtils.isConnByHttp(Constant.DOMAINTEST);// 先判断对方服务器是否存在
                        //                                if (st) {
                        //                                    FilesBusines.putKfState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, JSON.toJSON(kfList));
                        //                                } else {
                        //                                    msg.what = CONN_UNSUCCESS;
                        //                                    handler.sendMessage(msg);
                        //                                }
                        //                            }
                        //                        }
                        break;
                    default:
                        super.handleMessage(msg);//这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                        break;
                }
            }
        };
    }

    /**
     * 网络操作相关的子线程
     */

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            uiMsg = mHandler.obtainMessage();
            Bundle data = new Bundle();

            boolean st = NetUtils.isConnByHttp(Constant.DOMAINTEST);// 先判断对方服务器是否存在
            if (st) {
                uiMsg.what = CONN_SUCCESS;
                mHandler.sendMessage(uiMsg);
            } else {
                uiMsg.what = CONN_UNSUCCESS;
                mHandler.sendMessage(uiMsg);
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isOnScreen = true;
        //        if(isOnScreen && isRun) {
        if (isOnScreen) {
            //开启新进程
            if (mCheckMsgThread == null) {
                mCheckMsgThread = new HandlerThread("BackThread");// 创建一个BackHandlerThread对象，它是一个线程
                mCheckMsgThread.start();// 启动线程
                //创建后台线程
                initBackThread();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止查询
        isOnScreen = false;
        mCheckMsgThread.quit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止查询
        isOnScreen = false;

        //释放资源
        if (mCheckMsgHandler != null) {
            mCheckMsgThread.quit();
        }
        mCheckMsgHandler.removeCallbacksAndMessages(null);

        if (networkThread != null) {
            networkThread.interrupt();//中断线程的方法
            networkThread = null;
        }
        if (wrKfDbThread != null) {
            wrKfDbThread.interrupt();//中断线程的方法
            wrKfDbThread = null;
        }
        if (wrMjjDbThread != null) {
            wrMjjDbThread.interrupt();//中断线程的方法
            wrMjjDbThread = null;
        }
        if (wrMjgDbThread != null) {
            wrMjgDbThread.interrupt();//中断线程的方法
            wrMjgDbThread = null;
        }
        finish();
    }


}
