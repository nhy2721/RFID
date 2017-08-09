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
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.JsonBean.CountJson;
import com.botongsoft.rfid.bean.JsonBean.KfJson;
import com.botongsoft.rfid.bean.JsonBean.MjjJson;
import com.botongsoft.rfid.bean.classity.CheckError;
import com.botongsoft.rfid.bean.classity.CheckPlan;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatil;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatilDel;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.classity.MjjgdaDelInfos;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.busines.FilesBusines;
import com.botongsoft.rfid.busines.MyBusinessInfo;
import com.botongsoft.rfid.common.constants.Constant;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.service.http.BusinessResolver;
import com.botongsoft.rfid.common.service.http.NetUtils;
import com.botongsoft.rfid.common.service.http.RequestTask;
import com.botongsoft.rfid.common.utils.JsonUtils;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.common.utils.UIUtils;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.botongsoft.rfid.ui.Thread.WriteCheckDetailDBThread;
import com.botongsoft.rfid.ui.Thread.WriteCheckErrorDBThread;
import com.botongsoft.rfid.ui.Thread.WriteCheckPlanDBThread;
import com.botongsoft.rfid.ui.Thread.WriteKfDBThread;
import com.botongsoft.rfid.ui.Thread.WriteMjgDBThread;
import com.botongsoft.rfid.ui.Thread.WriteMjgDaDBThread;
import com.botongsoft.rfid.ui.Thread.WriteMjgDaDelDBThread;
import com.botongsoft.rfid.ui.Thread.WriteMjjDBThread;
import com.botongsoft.rfid.ui.adapter.SyncAdapter;
import com.botongsoft.rfid.ui.widget.RecyclerViewDecoration.ListViewDescDecoration;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.botongsoft.rfid.R.id.appBarLayout;
import static com.botongsoft.rfid.R.id.toolbar;
import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETDA_SUCCESS;

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
    private static final int CONN_UNSUCCESS1 = 3;
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
    private static final int BackThread_GETMJJGDA = 1005;
    private static final int BackThread_PUTMJJGDA = 1006;
    private static final int BackThread_GETCHECKPLAN = 1007;
    private static final int BackThread_PUTCHECKERRORPLAN = 1008;
    private static final int BackThread_PUTCHECKDETAILPLAN = 1009;
    //传递后台运行消息队列
    Message backThreadmsg;
    Message uiMsg;
    private Thread networkThread;//网络操作相关的子线程
    private WriteKfDBThread wrKfDbThread;//数据库操作相关
    private WriteMjjDBThread wrMjjDbThread;//数据库操作相关
    private WriteMjgDBThread wrMjgDbThread;//数据库操作相关
    private WriteMjgDaDBThread writeMjgDaDBThread;//数据库操作相关
    private WriteMjgDaDelDBThread writeMjgDaDelDBThread;//数据库操作相关
    private WriteCheckPlanDBThread writeCheckPlanDBThread;
    private WriteCheckErrorDBThread writeCheckErrorDBThread;
    private WriteCheckDetailDBThread writeCheckDetailDBThread;
    static int temple = 0;
    static Long kfAnchor;
    static Long mjjAnchor;
    static Long mjjgAnchor;
    static Long mjjgdaAnchor;
    static Long checkPlanAnchor;
    private Long serverCheckDetailAnchor = 0L;
    private Long serverCheckErrorAnchor = 0L;
    private long mCheckDetailCount;//盘点错误记录本地提交服务器数量
    private long mCheckErrorCount;//盘点格子记录本地提交服务器数量
    static long mDaLocalCount;//档案本地提交服务器数量
    RequestTask task;
    List<Mjjgda> mjgdaLists;
    List<MjjgdaDelInfos> mjgdaDelLists;
    private boolean flag = false;
    private boolean isPause;

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
                            myBusinessInfos.add(new MyBusinessInfo(syncArry[i], 1000 + i, "" + 0, null));
                        }
                        backThreadmsg = mCheckMsgHandler.obtainMessage();
                        LogUtils.d("BackThread_DOWORK;");
                        backThreadmsg.what = BackThread_DOWORK;
                        mCheckMsgHandler.sendMessage(backThreadmsg);
                        //                        FilesBusines.getWorkState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext,kfAnchor,mjjAnchor,mjjgAnchor);
                        break;
                    case INIT_DOWORK:
                        FilesBusines.getWorkState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, kfAnchor, mjjAnchor, mjjgAnchor, mjjgdaAnchor, checkPlanAnchor);
                        break;
                    case CONN_UNSUCCESS:
                        new AlertDialog.Builder(mContext)
                                .setTitle("服务器无法访问")
                                .setMessage("情检查网络是否畅通")
                                .create().show();
                        break;
                    case CONN_UNSUCCESS1:
                        ToastUtils.showShort("网络中断");
                        //                        task.cancel(true);
                        //                        if (networkThread != null) {
                        //                            networkThread.interrupt();//中断线程的方法
                        //                            networkThread = null;
                        //                        }
                        break;
                    case Constant.BackThread_SUCCESS:
                        ToastUtils.showLong("通知界面保存完毕");
                        mSyncAdapter.notifyDataSetChanged();
                        break;
                    case BackThread_GETDA_SUCCESS:
                        ToastUtils.showLong("通知界面保存完毕");
                        //                        flikerProgressBar.setProgress(msg.arg1);
                        //                        if(msg.arg1 == 100){
                        //                            progressBar.finishLoad();
                        //                        }
                        temple = 0;
                        mSyncAdapter.notifyDataSetChanged();
                        ToastUtils.showShort("BackThread_GETDA_SUCCESS==》" + mDaLocalCount);
                        //                        if (mDaLocalCount > 0) {//服务器更新完数据后如果本地有数据需提交更新就发消息到后台线程去执行
                        //                            mCheckMsgHandler.obtainMessage(BackThread_PUTMJJGDA).sendToTarget();
                        //                        }
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
        myBusinessInfos.add(new MyBusinessInfo("库房", null, kfJson.getRes().getRecordCount(), kfJson));
        MjjJson mjjJson = (MjjJson) JsonUtils.analysisJsonFileByRaw(this, "mjj", MjjJson.class);
        myBusinessInfos.add(new MyBusinessInfo("密集架", null, mjjJson.getRes().getRecordCount(), mjjJson));
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

        }

        @Override
        public void onItemClick(int position, int listSize, ProgressBar pb) {
            Toast.makeText(mContext, "我是第" + position + "条点击同步。", Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0:
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
                case 1:
                    LogUtils.d("read MjjJson");
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    LogUtils.d("BackThread_GETMJJ;");
                    backThreadmsg.what = BackThread_GETMJJ;
                    mCheckMsgHandler.sendMessage(backThreadmsg);
                    break;
                case 2:
                    LogUtils.d("read MjjJson");
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    LogUtils.d("BackThread_GETMJJG;");
                    backThreadmsg.what = BackThread_GETMJJG;
                    mCheckMsgHandler.sendMessage(backThreadmsg);
                    break;
                case 3:
                    ToastUtils.showShort("我点了档案按钮temp" + temple + "/" + mDaLocalCount);
                    if (temple > 0) {//如果服务器有更新数据 先获取服务器的更新数据
                        backThreadmsg = mCheckMsgHandler.obtainMessage();
                        backThreadmsg.what = BackThread_GETMJJGDA;
                        mCheckMsgHandler.sendMessage(backThreadmsg);
                    }
                    if (mDaLocalCount > 0) {
                        //                        pb.setProgress(100);
                        mCheckMsgHandler.obtainMessage(BackThread_PUTMJJGDA).sendToTarget();
                    }

                    break;
                case 4:
                    LogUtils.d("read checkPlan");
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    LogUtils.d("BackThread_GETCHECKPLAN;");
                    backThreadmsg.what = BackThread_GETCHECKPLAN;
                    mCheckMsgHandler.sendMessage(backThreadmsg);
                    break;
                case 5:
                    LogUtils.d(" checkPlanError");
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    LogUtils.d("BackThread_PUTCHECKERRORPLAN;");
                    backThreadmsg.what = BackThread_PUTCHECKERRORPLAN;
                    mCheckMsgHandler.sendMessage(backThreadmsg);
                    break;
                case 6:
                    LogUtils.d(" checkPlanDetail");
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    LogUtils.d("BackThread_PUTCHECKDETAILPLAN;");
                    backThreadmsg.what = BackThread_PUTCHECKDETAILPLAN;
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
            if (act == BackThread_DOWORK) {//服务器返回更新数
                if (response.isSuccess()) {
                    try {
                        List<CountJson> countJsons = JSONObject.parseArray(response.res.rows, CountJson.class);
                        if (Integer.valueOf(countJsons.get(0).kf) > 0) {
                            myBusinessInfos.get(0).setListSize("服务器新数据：" + countJsons.get(0).kf + "条记录");
                        } else {
                            myBusinessInfos.get(0).setListSize("无更新内容");
                        }
                        if (Integer.valueOf(countJsons.get(0).mjj) > 0) {
                            myBusinessInfos.get(1).setListSize("服务器新数据：" + countJsons.get(0).mjj + "条记录");
                        } else {
                            myBusinessInfos.get(1).setListSize("无更新内容");
                        }
                        if (Integer.valueOf(countJsons.get(0).mjjg) > 0) {
                            myBusinessInfos.get(2).setListSize("服务器新数据：" + countJsons.get(0).mjjg + "条记录");
                        } else {
                            myBusinessInfos.get(2).setListSize("无更新内容");
                        }

                        if (Integer.valueOf(countJsons.get(0).mjgda) > 0 && mDaLocalCount > 0) {
                            temple = Integer.valueOf(countJsons.get(0).mjgda);
                            StringBuilder sb = new StringBuilder();
                            sb.append("本地有").append(mDaLocalCount).append("条数据需要提交").append("\n");
                            sb.append("服务器新数据：").append(countJsons.get(0).mjgda).append("条记录");
                            myBusinessInfos.get(3).setListSize(sb.toString());
                            //                            myBusinessInfos.get(3).setListSize("本地有" + mDaLocalCount + "条数据需要提交/服务器新数据：" + countJsons.get(0).mjgda + "条记录");
                        } else if (Integer.valueOf(countJsons.get(0).mjgda) > 0 && mDaLocalCount == 0) {
                            temple = Integer.valueOf(countJsons.get(0).mjgda);
                            myBusinessInfos.get(3).setListSize("服务器新数据：" + countJsons.get(0).mjgda + "条记录");
                        } else if (Integer.valueOf(countJsons.get(0).mjgda) == 0 && mDaLocalCount > 0) {
                            myBusinessInfos.get(3).setListSize("本地有" + mDaLocalCount + "条数据需要提交");
                        } else {
                            myBusinessInfos.get(3).setListSize("无更新内容");
                        }

                        if (Integer.valueOf(countJsons.get(0).checkplan) > 0) {
                            myBusinessInfos.get(4).setListSize("服务器新数据：" + countJsons.get(0).checkplan + "条记录");
                        } else {
                            myBusinessInfos.get(4).setListSize("无更新内容");
                        }

                        if (mCheckErrorCount > 0) {
                            myBusinessInfos.get(5).setListSize("本地有" + mCheckErrorCount + "条数据需要提交");
                        } else {
                            myBusinessInfos.get(5).setListSize("无更新内容");
                        }
                        if (mCheckDetailCount > 0) {
                            myBusinessInfos.get(6).setListSize("本地有" + mCheckDetailCount + "条数据需要提交");
                        } else {
                            myBusinessInfos.get(6).setListSize("无更新内容");
                        }
                        //                        if (Long.valueOf(countJsons.get(0).checkErrorNum) > 0) {
                        //                            serverCheckErrorAnchor = Long.valueOf(countJsons.get(0).checkErrorNum);
                        //                        }
                        //                        if (Long.valueOf(countJsons.get(0).checkDetailNum) > 0) {
                        //                            serverCheckDetailAnchor = Long.valueOf(countJsons.get(0).checkDetailNum);
                        //                        }

                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_GETKF) {
                if (response.isSuccess()) {
                    try {
                        List<Kf> kfJsonList = JSON.parseArray(response.res.rows, Kf.class);
                        if (kfJsonList != null && !kfJsonList.isEmpty()) {
                            wrKfDbThread = new WriteKfDBThread(mHandler, uiMsg);
                            wrKfDbThread.setList(kfJsonList);
                            wrKfDbThread.start();
                        }
                        //                        myBusinessInfos.get(0).setListSize("" + 0);
                        //                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else if (act == BackThread_GETMJJ) {
                if (response.isSuccess()) {
                    try {
                        List<Mjj> mjjJsonList = JSON.parseArray(response.res.rows, Mjj.class);
                        if (mjjJsonList != null && !mjjJsonList.isEmpty()) {
                            wrMjjDbThread = new WriteMjjDBThread(mHandler, uiMsg);
                            wrMjjDbThread.setList(mjjJsonList);
                            wrMjjDbThread.start();
                        }
                        //                        myBusinessInfos.get(1).setListSize("" + 0);
                        //                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_GETMJJG) {
                if (response.isSuccess()) {
                    try {
                        List<Mjjg> mjjgJsonList = JSON.parseArray(response.res.rows, Mjjg.class);
                        if (mjjgJsonList != null && !mjjgJsonList.isEmpty()) {
                            wrMjgDbThread = new WriteMjgDBThread(mHandler, uiMsg);
                            wrMjgDbThread.setList(mjjgJsonList);
                            wrMjgDbThread.start();
                        }
                        //                        myBusinessInfos.get(2).setListSize("" + 0);
                        //                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_GETMJJGDA) {
                if (response.isSuccess()) {
                    try {
                        List<Mjjgda> mjjgdaJsonList = JSON.parseArray(response.res.rows, Mjjgda.class);
                        if (mjjgdaJsonList != null && !mjjgdaJsonList.isEmpty()) {
                            writeMjgDaDBThread = new WriteMjgDaDBThread(mHandler, uiMsg, 0);
                            writeMjgDaDBThread.setList(mjjgdaJsonList);
                            writeMjgDaDBThread.start();
                        }
                        //                        myBusinessInfos.get(2).setListSize("" + 0);
                        //                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_PUTMJJGDA) {
                if (response.isSuccess()) {
                    try {
                        List<Mjjgda> mjjgdaJsonList = JSON.parseArray(response.res.rows, Mjjgda.class);
                        if (mjjgdaJsonList != null && !mjjgdaJsonList.isEmpty()) {
                            writeMjgDaDBThread = new WriteMjgDaDBThread(mHandler, uiMsg, 1);
                            writeMjgDaDBThread.setList(mjjgdaJsonList);
                            writeMjgDaDBThread.start();
                        }
                        List<Mjjgda> mjjgdaDelJsonList = JSON.parseArray(response.res.delrecords, Mjjgda.class);
                        if (mjjgdaDelJsonList != null && !mjjgdaDelJsonList.isEmpty()) {
                            Log.i("delList", mjjgdaDelJsonList.toString());
                            writeMjgDaDelDBThread = new WriteMjgDaDelDBThread(mHandler, uiMsg);
                            writeMjgDaDelDBThread.setList(mjjgdaDelJsonList);
                            writeMjgDaDelDBThread.start();
                        }
                        //                        myBusinessInfos.get(2).setListSize("" + 0);
                        //                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_GETCHECKPLAN) {
                if (response.isSuccess()) {
                    try {
                        List<CheckPlan> checkPlanJsonList = JSON.parseArray(response.res.rows, CheckPlan.class);
                        if (checkPlanJsonList != null && !checkPlanJsonList.isEmpty()) {
                            writeCheckPlanDBThread = new WriteCheckPlanDBThread(mHandler, uiMsg);
                            writeCheckPlanDBThread.setList(checkPlanJsonList);
                            writeCheckPlanDBThread.start();
                        }
                        //                        myBusinessInfos.get(2).setListSize("" + 0);
                        //                        mSyncAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_PUTCHECKERRORPLAN) {
                if (response.isSuccess()) {
                    try {
                        List<CheckError> checkErrorJsonList = JSON.parseArray(response.res.rows, CheckError.class);
                        if (checkErrorJsonList != null && !checkErrorJsonList.isEmpty()) {
                            writeCheckErrorDBThread = new WriteCheckErrorDBThread(mHandler, uiMsg);
                            writeCheckErrorDBThread.setList(checkErrorJsonList);
                            writeCheckErrorDBThread.start();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_PUTCHECKDETAILPLAN) {
                if (response.isSuccess()) {
                    try {
                        List<CheckPlanDeatil> checkDetailJsonList = JSON.parseArray(response.res.rows, CheckPlanDeatil.class);
                        if (checkDetailJsonList != null && !checkDetailJsonList.isEmpty()) {
                            writeCheckDetailDBThread = new WriteCheckDetailDBThread(mHandler, uiMsg);
                            writeCheckDetailDBThread.setList(checkDetailJsonList);
                            writeCheckDetailDBThread.start();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onError(BusinessException e, int act) {
        ToastUtils.showLong(act + "");
        ToastUtils.showLong(e.getMessage());
    }

    private void initBackThread() {
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.e("Handler BackThread--->", String.valueOf(Thread.currentThread().getName()));
                switch (msg.what) {
                    case BackThread_DOWORK:
                        uiMsg = mHandler.obtainMessage();
                        uiMsg.what = INIT_DOWORK;
                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        Kf kfInfo = (Kf) DBDataUtils.getInfoHasOp(Kf.class, "anchor", ">=", "0");
                        if (kfInfo == null) {
                            kfAnchor = 0L;
                        } else {
                            kfAnchor = Long.valueOf(kfInfo.getAnchor());
                        }
                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        Mjj mjjInfo = (Mjj) DBDataUtils.getInfoHasOp(Mjj.class, "anchor", ">=", "0");
                        if (mjjInfo == null) {
                            mjjAnchor = 0L;
                        } else {
                            mjjAnchor = Long.valueOf(mjjInfo.getAnchor());
                        }

                        //先将本地的版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        Mjjg mjjgInfo = (Mjjg) DBDataUtils.getInfoHasOp(Mjjg.class, "anchor", ">=", "0");
                        if (mjjgInfo == null) {
                            mjjgAnchor = 0L;
                        } else {
                            mjjgAnchor = Long.valueOf(mjjgInfo.getAnchor());
                        }
                        //先将本地的版本号发送给服务器，判断服务器时候有更新过 有更新要解决冲突
                        Mjjgda mjjgdaInfo = (Mjjgda) DBDataUtils.getInfoHasOp(Mjjgda.class, "anchor", ">=", "0");
                        if (mjjgdaInfo == null) {
                            mjjgdaAnchor = 0L;
                        } else {
                            mjjgdaAnchor = Long.valueOf(mjjgdaInfo.getAnchor());
                        }
                        try {
                            //已经同步过的数据下架了
                            int temp1 = (int) DBDataUtils.count(Mjjgda.class, "status", "=", "-1", "anchor", ">", "0");
                            //新保存的上架记录
                            int temp2 = (int) DBDataUtils.count(Mjjgda.class, "status", "=", "0", "anchor", "=", "0");
                            mDaLocalCount = temp1 + temp2;
                            if (mDaLocalCount > 0) {
                                uiMsg.arg1 = (int) mDaLocalCount;
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                        //盘点计划表版本号发送给服务器，服务器对比后返回大于这个版本号的数据进行更新本地库房表
                        CheckPlan mCheckPlan = (CheckPlan) DBDataUtils.getInfoHasOp(CheckPlan.class, "anchor", ">=", "0");
                        if (mCheckPlan == null) {
                            checkPlanAnchor = 0L;
                        } else {
                            checkPlanAnchor = Long.valueOf(mCheckPlan.getAnchor());
                        }
                        //盘点日志表
                        try {
                            int temp1 = (int) DBDataUtils.count(CheckError.class, "status", "=", "0", "anchor", ">=", "0");
                            mCheckErrorCount = Long.valueOf(temp1);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        //盘点纠错表
                        try {
                            int temp1 = (int) DBDataUtils.count(CheckPlanDeatil.class, "status", "=", "0", "anchor", ">=", "0");
                            mCheckDetailCount = Long.valueOf(temp1);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendMessage(uiMsg);
                        //                        mHandler.obtainMessage(INIT_DOWORK).sendToTarget();
                        break;
                    case BackThread_GETKF:
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, kfAnchor, BackThread_GETKF);
                        break;
                    case BackThread_GETMJJ:
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, mjjAnchor, BackThread_GETMJJ);
                        break;
                    case BackThread_GETMJJG:
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, mjjgAnchor, BackThread_GETMJJG);
                        break;
                    case BackThread_GETMJJGDA:
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, mjjgdaAnchor, BackThread_GETMJJGDA);
                        break;
                    case BackThread_PUTMJJGDA:
                        if (flag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        flag = true;
                        //已同步被下架
                        List<Mjjgda> tempList1 = (List<Mjjgda>) DBDataUtils.getInfosHasOp(Mjjgda.class, "status", "=", "-1", "anchor", ">", "0");
                        //新上架子
                        List<Mjjgda> tempList = (List<Mjjgda>) DBDataUtils.getInfosHasOp(Mjjgda.class, "status", "=", "0", "anchor", "=", "0");
                        //                        mjgdaDelLists = (List<MjjgdaDelInfos>) DBDataUtils.getInfos(MjjgdaDelInfos.class, "status", "=", "-1");
                        boolean st = NetUtils.isConnByHttp(Constant.DOMAINTEST);// 先判断对方服务器是否存在
                        if (st) {
                            if ((tempList != null && !tempList.isEmpty()) || (tempList1 != null && !tempList1.isEmpty())) {
//                                task = new RequestTask((BusinessResolver.BusinessCallback<BaseResponse>) mContext, mContext);
                                FilesBusines.putDa( mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, BackThread_PUTMJJGDA, tempList, tempList1);
                            }

                        }
//                        else {
//                            //                                    DialogLoad.closes();
//                            //                                    uiMsg = mHandler.obtainMessage();
//                            //                                    uiMsg.what = CONN_UNSUCCESS1;
//                            //                                    mHandler.sendMessage(uiMsg);
//                            if (task != null || task.getStatus() == AsyncTask.Status.RUNNING) {
//                                task.cancel(true);
//                                mjgdaLists.clear();
//                            }
//                            break;
//                        }

                        break;
                    case BackThread_GETCHECKPLAN:
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, checkPlanAnchor, BackThread_GETCHECKPLAN);
                        break;
                    case BackThread_PUTCHECKERRORPLAN:
                        List<CheckError> checkErrorList = (List<CheckError>) DBDataUtils.getInfosHasOp(CheckError.class, "status", "=", "0", "anchor", ">", "0");
                        FilesBusines.putCheckPlan(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, BackThread_PUTCHECKERRORPLAN, null, checkErrorList, null);
                        break;
                    case BackThread_PUTCHECKDETAILPLAN:
                        List<CheckPlanDeatil> checkDetailList = (List<CheckPlanDeatil>) DBDataUtils.getInfosHasOp(CheckPlanDeatil.class, "status", "=", "0", "anchor", ">", "0");
                        List<CheckPlanDeatilDel> checkDetailDelList = (List<CheckPlanDeatilDel>) DBDataUtils.getInfosHasOp(CheckPlanDeatilDel.class, "status", "=", "9", "anchor", ">", "0");
                        FilesBusines.putCheckPlan(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, BackThread_PUTCHECKDETAILPLAN, checkDetailList, null, checkDetailDelList);
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
            } else {
                uiMsg.what = CONN_UNSUCCESS;
            }
            mHandler.sendMessage(uiMsg);
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
        //        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
        //            task.cancel(true);
        //        }
        if (mjgdaLists != null && !mjgdaLists.isEmpty()) {
            mjgdaLists.clear();
        }
        temple = 0;
        mDaLocalCount = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止查询
        isOnScreen = false;
        temple = 0;
        mDaLocalCount = 0;
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
        if (writeMjgDaDBThread != null) {
            writeMjgDaDBThread.interrupt();//中断线程的方法
            writeMjgDaDBThread = null;
        }
        mCheckMsgHandler.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);
        //        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
        //            task.cancel(true);
        //        }
        finish();
    }

    /*
         * 重写返回键,模拟返回按下暂停键
         */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (flag) {
                    isPause = true;
                    flag = false;
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
