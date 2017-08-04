package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.JsonBean.CountJson;
import com.botongsoft.rfid.bean.classity.CheckError;
import com.botongsoft.rfid.bean.classity.CheckPlan;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatil;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatilDel;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.busines.FilesBusines;
import com.botongsoft.rfid.common.constants.Constant;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.service.http.BusinessResolver;
import com.botongsoft.rfid.common.service.http.NetUtils;
import com.botongsoft.rfid.common.service.http.RequestTask;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.ui.Thread.WriteCheckDetailDBThread;
import com.botongsoft.rfid.ui.Thread.WriteCheckErrorDBThread;
import com.botongsoft.rfid.ui.Thread.WriteCheckPlanDBThread;
import com.botongsoft.rfid.ui.Thread.WriteKfDBThread;
import com.botongsoft.rfid.ui.Thread.WriteMjgDBThread;
import com.botongsoft.rfid.ui.Thread.WriteMjgDaDBThread;
import com.botongsoft.rfid.ui.Thread.WriteMjgDaDelDBThread;
import com.botongsoft.rfid.ui.Thread.WriteMjjDBThread;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.botongsoft.rfid.R.id.appBarLayout;
import static com.botongsoft.rfid.R.id.toolbar;

/**
 * Created by pc on 2017/7/10.
 */

public class SyncbakActivity extends BaseActivity {
    private Activity mContext;
    @BindView(appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_name1)
    TextView tv_name1;
    @BindView(R.id.bt_action1)
    Button bt_action1;
    @BindView(R.id.tv_oleNsize1)
    TextView tv_oleNsize1;
    @BindView(R.id.tv_status1)
    TextView tv_status1;
    @BindView(R.id.pb1)
    ProgressBar pb1;

    @BindView(R.id.tv_name2)
    TextView tv_name2;
    @BindView(R.id.bt_action2)
    Button bt_action2;
    @BindView(R.id.tv_oleNsize2)
    TextView tv_oleNsize2;
    @BindView(R.id.tv_status2)
    TextView tv_status2;
    @BindView(R.id.pb2)
    ProgressBar pb2;

    @BindView(R.id.tv_name3)
    TextView tv_name3;
    @BindView(R.id.bt_action3)
    Button bt_action3;
    @BindView(R.id.tv_oleNsize3)
    TextView tv_oleNsize3;
    @BindView(R.id.tv_status3)
    TextView tv_status3;
    @BindView(R.id.pb3)
    ProgressBar pb3;
    @BindView(R.id.tv_name4)
    TextView tv_name4;
    @BindView(R.id.bt_action4)
    Button bt_action4;
    @BindView(R.id.tv_oleNsize4)
    TextView tv_oleNsize4;
    @BindView(R.id.tv_status4)
    TextView tv_status4;
    @BindView(R.id.pb4)
    ProgressBar pb4;
    @BindView(R.id.tv_name5)
    TextView tv_name5;
    @BindView(R.id.bt_action5)
    Button bt_action5;
    @BindView(R.id.tv_oleNsize5)
    TextView tv_oleNsize5;
    @BindView(R.id.tv_status5)
    TextView tv_status5;
    @BindView(R.id.pb5)
    ProgressBar pb5;
    @BindView(R.id.tv_name6)
    TextView tv_name6;
    @BindView(R.id.bt_action6)
    Button bt_action6;
    @BindView(R.id.tv_oleNsize6)
    TextView tv_oleNsize6;
    @BindView(R.id.tv_status6)
    TextView tv_status6;
    @BindView(R.id.pb6)
    ProgressBar pb6;
    @BindView(R.id.tv_name7)
    TextView tv_name7;
    @BindView(R.id.bt_action7)
    Button bt_action7;
    @BindView(R.id.tv_oleNsize7)
    TextView tv_oleNsize7;
    @BindView(R.id.tv_status7)
    TextView tv_status7;
    @BindView(R.id.pb7)
    ProgressBar pb7;


    private static final int CONN_SUCCESS = 0;
    private static final int CONN_UNSUCCESS = 1;
    private static final int CONN_UNSUCCESS1 = 3;
    private static final int INIT_DOWORK = 2;
    private static final int PUT_WROK_KF = 1002;
    private boolean isOnLine = true;//是否在线
    private boolean isOnScreen;//是否在屏幕上
    private HandlerThread mCheckMsgThread;//Handler线程池
    //后台运行的handler
    private Handler mCheckMsgHandler;
    //与UI线程管理的handler
    private Handler mHandler;
    private static final int BackThread_DOWORK = 9999;
    private static final int BackThread_GETKF = 1000;
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
    private boolean getKfFlag = false;
    private boolean getMjjFlag = false;
    private boolean getMjgflag = false;
    private boolean getDaFLag = false;
    private boolean putDaFLag = false;
    private boolean getCheckPlanFLag = false;
    private boolean putCheckDetailFLag = false;
    private boolean putCheckErrorFLag = false;
    private boolean isPause;
    List<Mjjgda> getMjjgdaJsonList;
    List<Mjjgda> putMjjgdaJsonList;
    List<Mjjg> mjjgJsonList;
    List<Mjj> mjjJsonList;
    List<Kf> kfJsonList;
    List<CheckPlan> checkPlanJsonList;
    List<CheckError> checkErrorJsonList;
    List<CheckPlanDeatil> checkDetailJsonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sync_bak);
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
                        backThreadmsg = mCheckMsgHandler.obtainMessage();
                        LogUtils.d("BackThread_DOWORK;");
                        backThreadmsg.what = BackThread_DOWORK;
                        mCheckMsgHandler.sendMessage(backThreadmsg);
                        break;
                    case INIT_DOWORK:
                        FilesBusines.getWorkState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, kfAnchor, mjjAnchor, mjjgAnchor, mjjgdaAnchor, checkPlanAnchor);
                        break;
                    case CONN_UNSUCCESS:
                        new AlertDialog.Builder(mContext)
                                .setTitle("服务器无法访问")
                                .setMessage("情检查网络是否畅通")
                                .create().show();
                        bt_action1.setEnabled(false);
                        bt_action2.setEnabled(false);
                        bt_action3.setEnabled(false);
                        bt_action4.setEnabled(false);
                        bt_action5.setEnabled(false);
                        bt_action6.setEnabled(false);
                        bt_action7.setEnabled(false);
                        isOnLine = false;
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

                        break;
                    case Constant.BackThread_GETCHECKDETAIL_SUCCESS_PB:
                        int checkdetail = data.getInt("checkdetail");
                        LogUtils.d(checkdetail + "");
                        pb7.setMax(checkDetailJsonList.size());
                        pb7.setProgress(checkdetail);
                        tv_status7.setText("正在写入数据库");
                        tv_status7.setTextColor(Color.RED);
                        if (checkdetail == checkDetailJsonList.size()) {
                            tv_oleNsize7.setText("更新完成");
                            tv_oleNsize7.setTextColor(Color.GREEN);
                            tv_status7.setText("");
                            putCheckDetailFLag = false;
                        }
                        break;
                    case Constant.BackThread_GETCHECKERROR_SUCCESS_PB:
                        int checkerror = data.getInt("checkerror");
                        LogUtils.d(checkerror + "");
                        pb6.setMax(checkErrorJsonList.size());
                        pb6.setProgress(checkerror);
                        tv_status6.setText("正在写入数据库");
                        tv_status6.setTextColor(Color.RED);
                        if (checkerror == checkErrorJsonList.size()) {
                            tv_oleNsize6.setText("更新完成");
                            tv_oleNsize6.setTextColor(Color.GREEN);
                            tv_status6.setText("");
                            putCheckErrorFLag = false;
                        }
                        break;
                    case Constant.BackThread_GETCHECKPLAN_SUCCESS_PB:
                        int checkplan = data.getInt("checkplan");
                        LogUtils.d(checkplan + "");
                        pb5.setMax(checkPlanJsonList.size());
                        pb5.setProgress(checkplan);
                        tv_status5.setText("正在写入数据库");
                        tv_status5.setTextColor(Color.RED);
                        if (checkplan == checkPlanJsonList.size()) {
                            tv_oleNsize5.setText("更新完成");
                            tv_oleNsize5.setTextColor(Color.GREEN);
                            tv_status5.setText("");
                            getCheckPlanFLag = false;
                        }
                        break;
                    case Constant.BackThread_GETDA_SUCCESS_PB:
                        int da = data.getInt("da");
                        LogUtils.d(da + "");
                        LogUtils.d(getMjjgdaJsonList.size() + "");
                        pb4.setMax(getMjjgdaJsonList.size());
                        pb4.setProgress(da);
                        tv_status4.setText("正在写入数据库");
                        tv_status4.setTextColor(Color.RED);
                        if (da == getMjjgdaJsonList.size()) {
                            tv_oleNsize4.setText("更新完成");
                            tv_oleNsize4.setTextColor(Color.GREEN);
                            tv_status4.setText("");
                            if (mDaLocalCount > 0) {
                                mCheckMsgHandler.obtainMessage(BackThread_PUTMJJGDA).sendToTarget();
                            }
                            getDaFLag = false;
                        }
                        temple = 0;

                        break;
                    case Constant.BackThread_PUTDA_SUCCESS_PB:
                        int putda = data.getInt("da");
                        LogUtils.d(putda + "");
                        pb4.setMax(putMjjgdaJsonList.size());
                        pb4.setProgress(putda);
                        tv_status4.setText("正在写入数据库");
                        tv_status4.setTextColor(Color.RED);
                        if (putda == putMjjgdaJsonList.size()) {
                            tv_oleNsize4.setText("更新完成");
                            tv_oleNsize4.setTextColor(Color.GREEN);
                            tv_status4.setText("");
                            putDaFLag = false;
                        }

                        break;
                    case Constant.BackThread_GETMJG_SUCCESS_PB:
                        int mjjg = data.getInt("mjg");
                        LogUtils.d(mjjg + "");
                        pb3.setMax(mjjgJsonList.size());
                        pb3.setProgress(mjjg);
                        tv_status3.setText("正在写入数据库");
                        tv_status3.setTextColor(Color.RED);
                        if (mjjg == mjjgJsonList.size()) {
                            tv_oleNsize3.setText("更新完成");
                            tv_oleNsize3.setTextColor(Color.GREEN);
                            tv_status3.setText("");
                            getMjgflag = false;
                        }
                        break;
                    case Constant.BackThread_GETMJJ_SUCCESS_PB:
                        int mjj = data.getInt("mjj");
                        LogUtils.d(mjj + "");
                        pb2.setMax(mjjJsonList.size());
                        pb2.setProgress(mjj);
                        tv_status2.setText("正在写入数据库");
                        tv_status2.setTextColor(Color.RED);
                        if (mjj == mjjJsonList.size()) {
                            tv_oleNsize2.setText("更新完成");
                            tv_oleNsize2.setTextColor(Color.GREEN);
                            tv_status2.setText("");
                            getMjjFlag = false;
                        }
                        break;
                    case Constant.BackThread_GETKF_SUCCESS_PB:
                        int kf = data.getInt("kf");
                        LogUtils.d(kf + "");
                        pb1.setMax(kfJsonList.size());
                        pb1.setProgress(kf);
                        tv_status1.setText("正在写入数据库");
                        tv_status1.setTextColor(Color.RED);
                        if (kf == kfJsonList.size()) {
                            tv_oleNsize1.setText("更新完成");
                            tv_oleNsize1.setTextColor(Color.GREEN);
                            tv_status1.setText("");
                            getKfFlag = false;
                        }
                        break;
                    default:
                        super.handleMessage(msg);
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
        tv_name1.setText("库房");
        tv_name2.setText("密集架");
        tv_name3.setText("密集格");
        tv_name4.setText("档案");
        tv_name5.setText("盘点计划");
        tv_name6.setText("盘点记录");
        tv_name7.setText("盘点纠错");
    }

    @OnClick({R.id.bt_action1, R.id.bt_action2, R.id.bt_action3, R.id.bt_action4, R.id.bt_action5, R.id.bt_action6, R.id.bt_action7})
    public void click(Button button) {
        switch (button.getId()) {
            case R.id.bt_action1:
                bt_action1.setEnabled(false);
                backThreadmsg = mCheckMsgHandler.obtainMessage();
                LogUtils.d("BackThread_GETKF;");
                backThreadmsg.what = BackThread_GETKF;
                mCheckMsgHandler.sendMessage(backThreadmsg);
                break;
            case R.id.bt_action2:
                button.setEnabled(false);
                backThreadmsg = mCheckMsgHandler.obtainMessage();
                LogUtils.d("BackThread_GETMJJ;");
                backThreadmsg.what = BackThread_GETMJJ;
                mCheckMsgHandler.sendMessage(backThreadmsg);
                break;
            case R.id.bt_action3:
                button.setEnabled(false);
                backThreadmsg = mCheckMsgHandler.obtainMessage();
                LogUtils.d("BackThread_GETMJJG;");
                backThreadmsg.what = BackThread_GETMJJG;
                mCheckMsgHandler.sendMessage(backThreadmsg);
                break;
            case R.id.bt_action4:
                button.setEnabled(false);
                if (temple > 0) {
                    //如果服务器有更新数据 先获取服务器的更新数据,然后在通知线程完毕后去查找本地是否有更新数据再上传到服务器
                    backThreadmsg = mCheckMsgHandler.obtainMessage();
                    backThreadmsg.what = BackThread_GETMJJGDA;
                    mCheckMsgHandler.sendMessage(backThreadmsg);
                } else if (mDaLocalCount > 0 && temple <= 0) {
                    //服务器没有更新，本地有更新
                    mCheckMsgHandler.obtainMessage(BackThread_PUTMJJGDA).sendToTarget();
                }
                break;
            case R.id.bt_action5:
                button.setEnabled(false);
                backThreadmsg = mCheckMsgHandler.obtainMessage();
                LogUtils.d("BackThread_GETCHECKPLAN;");
                backThreadmsg.what = BackThread_GETCHECKPLAN;
                mCheckMsgHandler.sendMessage(backThreadmsg);
                break;
            case R.id.bt_action6:
                button.setEnabled(false);
                backThreadmsg = mCheckMsgHandler.obtainMessage();
                LogUtils.d("BackThread_PUTCHECKERRORPLAN;");
                backThreadmsg.what = BackThread_PUTCHECKERRORPLAN;
                mCheckMsgHandler.sendMessage(backThreadmsg);
                break;
            case R.id.bt_action7:
                button.setEnabled(false);
                backThreadmsg = mCheckMsgHandler.obtainMessage();
                LogUtils.d("BackThread_PUTCHECKDETAILPLAN;");
                backThreadmsg.what = BackThread_PUTCHECKDETAILPLAN;
                mCheckMsgHandler.sendMessage(backThreadmsg);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(BaseResponse response, int act) {
        if (response != null) {
            if (act == BackThread_DOWORK) {//服务器返回更新数
                if (response.isSuccess()) {
                    try {
                        List<CountJson> countJsons = JSONObject.parseArray(response.res.rows, CountJson.class);
                        if (Integer.valueOf(countJsons.get(0).kf) > 0) {
                            tv_oleNsize1.setText("服务器新数据：" + countJsons.get(0).kf + "条记录");
                        } else {
                            tv_oleNsize1.setText("无更新内容");
                        }
                        if (Integer.valueOf(countJsons.get(0).mjj) > 0) {
                            tv_oleNsize2.setText("服务器新数据：" + countJsons.get(0).mjj + "条记录");
                        } else {
                            tv_oleNsize2.setText("无更新内容");
                        }
                        if (Integer.valueOf(countJsons.get(0).mjjg) > 0) {
                            tv_oleNsize3.setText("服务器新数据：" + countJsons.get(0).mjjg + "条记录");
                        } else {
                            tv_oleNsize3.setText("无更新内容");
                        }

                        if (Integer.valueOf(countJsons.get(0).mjgda) > 0 && mDaLocalCount > 0) {
                            temple = Integer.valueOf(countJsons.get(0).mjgda);
                            StringBuilder sb = new StringBuilder();
                            sb.append("本地有").append(mDaLocalCount).append("条数据需要提交").append("\n");
                            sb.append("服务器新数据：").append(countJsons.get(0).mjgda).append("条记录");
                            tv_oleNsize4.setText(sb.toString());
                            //                            myBusinessInfos.get(3).setListSize("本地有" + mDaLocalCount + "条数据需要提交/服务器新数据：" + countJsons.get(0).mjgda + "条记录");
                        } else if (Integer.valueOf(countJsons.get(0).mjgda) > 0 && mDaLocalCount == 0) {
                            temple = Integer.valueOf(countJsons.get(0).mjgda);
                            tv_oleNsize4.setText("服务器新数据：" + countJsons.get(0).mjgda + "条记录");
                        } else if (Integer.valueOf(countJsons.get(0).mjgda) == 0 && mDaLocalCount > 0) {
                            tv_oleNsize4.setText("本地有" + mDaLocalCount + "条数据需要提交");
                        } else {
                            tv_oleNsize4.setText("无更新内容");
                        }

                        if (Integer.valueOf(countJsons.get(0).checkplan) > 0) {
                            tv_oleNsize5.setText("服务器新数据：" + countJsons.get(0).checkplan + "条记录");
                        } else {
                            tv_oleNsize5.setText("无更新内容");
                        }

                        if (mCheckErrorCount > 0) {
                            tv_oleNsize6.setText("本地有" + mCheckErrorCount + "条数据需要提交");
                        } else {
                            tv_oleNsize6.setText("无更新内容");
                        }
                        if (mCheckDetailCount > 0) {
                            tv_oleNsize7.setText("本地有" + mCheckDetailCount + "条数据需要提交");
                        } else {
                            tv_oleNsize7.setText("无更新内容");
                        }
                        //                        if (Long.valueOf(countJsons.get(0).checkErrorNum) > 0) {
                        //                            serverCheckErrorAnchor = Long.valueOf(countJsons.get(0).checkErrorNum);
                        //                        }
                        //                        if (Long.valueOf(countJsons.get(0).checkDetailNum) > 0) {
                        //                            serverCheckDetailAnchor = Long.valueOf(countJsons.get(0).checkDetailNum);
                        //                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_GETKF) {
                if (response.isSuccess()) {
                    try {
                        kfJsonList = JSON.parseArray(response.res.rows, Kf.class);
                        if (kfJsonList != null && !kfJsonList.isEmpty()) {
                            wrKfDbThread = new WriteKfDBThread(mHandler, uiMsg);
                            wrKfDbThread.setList(kfJsonList);
                            wrKfDbThread.start();
                        } else {
                            getKfFlag = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else if (act == BackThread_GETMJJ) {
                if (response.isSuccess()) {
                    try {
                        mjjJsonList = JSON.parseArray(response.res.rows, Mjj.class);
                        if (mjjJsonList != null && !mjjJsonList.isEmpty()) {
                            wrMjjDbThread = new WriteMjjDBThread(mHandler, uiMsg);
                            wrMjjDbThread.setList(mjjJsonList);
                            wrMjjDbThread.start();
                        } else {
                            getMjjFlag = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_GETMJJG) {
                if (response.isSuccess()) {
                    try {
                        mjjgJsonList = JSON.parseArray(response.res.rows, Mjjg.class);
                        if (mjjgJsonList != null && !mjjgJsonList.isEmpty()) {
                            wrMjgDbThread = new WriteMjgDBThread(mHandler, uiMsg);
                            wrMjgDbThread.setList(mjjgJsonList);
                            wrMjgDbThread.start();
                        } else {
                            getMjgflag = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_GETMJJGDA) {
                if (response.isSuccess()) {
                    try {
                        getMjjgdaJsonList = JSON.parseArray(response.res.rows, Mjjgda.class);
                        if (getMjjgdaJsonList != null && !getMjjgdaJsonList.isEmpty()) {
                            writeMjgDaDBThread = new WriteMjgDaDBThread(mHandler, uiMsg, 0);
                            writeMjgDaDBThread.setList(getMjjgdaJsonList);
                            writeMjgDaDBThread.start();
                        } else {
                            getDaFLag = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //                    flag = false;
                }
            } else if (act == BackThread_PUTMJJGDA) {
                if (response.isSuccess()) {
                    try {
                        putMjjgdaJsonList = JSON.parseArray(response.res.rows, Mjjgda.class);
                        if (putMjjgdaJsonList != null && !putMjjgdaJsonList.isEmpty()) {
                            writeMjgDaDBThread = new WriteMjgDaDBThread(mHandler, uiMsg, 1);
                            writeMjgDaDBThread.setList(putMjjgdaJsonList);
                            writeMjgDaDBThread.start();
                        } else {
                            putDaFLag = false;
                        }
                        List<Mjjgda> delMjjgdaJsonList = JSON.parseArray(response.res.delrecords, Mjjgda.class);
                        if (delMjjgdaJsonList != null && !delMjjgdaJsonList.isEmpty()) {
                            Log.i("delList", delMjjgdaJsonList.toString());
                            writeMjgDaDelDBThread = new WriteMjgDaDelDBThread(mHandler, uiMsg);
                            writeMjgDaDelDBThread.setList(delMjjgdaJsonList);
                            writeMjgDaDelDBThread.start();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //                    flag = false;
                }
            } else if (act == BackThread_GETCHECKPLAN) {
                if (response.isSuccess()) {
                    try {
                        checkPlanJsonList = JSON.parseArray(response.res.rows, CheckPlan.class);
                        if (checkPlanJsonList != null && !checkPlanJsonList.isEmpty()) {
                            writeCheckPlanDBThread = new WriteCheckPlanDBThread(mHandler, uiMsg);
                            writeCheckPlanDBThread.setList(checkPlanJsonList);
                            writeCheckPlanDBThread.start();
                        } else {
                            getCheckPlanFLag = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_PUTCHECKERRORPLAN) {
                if (response.isSuccess()) {
                    try {
                        checkErrorJsonList = JSON.parseArray(response.res.rows, CheckError.class);
                        if (checkErrorJsonList != null && !checkErrorJsonList.isEmpty()) {
                            writeCheckErrorDBThread = new WriteCheckErrorDBThread(mHandler, uiMsg);
                            writeCheckErrorDBThread.setList(checkErrorJsonList);
                            writeCheckErrorDBThread.start();
                        } else {
                            putCheckErrorFLag = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (act == BackThread_PUTCHECKDETAILPLAN) {
                if (response.isSuccess()) {
                    try {
                        checkDetailJsonList = JSON.parseArray(response.res.rows, CheckPlanDeatil.class);
                        if (checkDetailJsonList != null && !checkDetailJsonList.isEmpty()) {
                            writeCheckDetailDBThread = new WriteCheckDetailDBThread(mHandler, uiMsg);
                            writeCheckDetailDBThread.setList(checkDetailJsonList);
                            writeCheckDetailDBThread.start();
                        } else {
                            putCheckDetailFLag = false;
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
                            //已经同步过的数据下架了(版本号大于0 状态为删除状态-1)
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
                        if (getKfFlag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        getKfFlag = true;
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, kfAnchor, BackThread_GETKF);
                        break;
                    case BackThread_GETMJJ:
                        if (getMjjFlag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        getMjjFlag = true;
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, mjjAnchor, BackThread_GETMJJ);
                        break;
                    case BackThread_GETMJJG:
                        if (getMjgflag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        getMjgflag = true;
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, mjjgAnchor, BackThread_GETMJJG);
                        break;
                    case BackThread_GETMJJGDA:
                        if (getDaFLag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        getDaFLag = true;
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, mjjgdaAnchor, BackThread_GETMJJGDA);
                        break;
                    case BackThread_PUTMJJGDA:
                        if (putDaFLag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        putDaFLag = true;
                        //已同步被下架
                        List<Mjjgda> tempList1 = (List<Mjjgda>) DBDataUtils.getInfosHasOp(Mjjgda.class, "status", "=", "-1", "anchor", ">", "0");
                        //新上架子
                        List<Mjjgda> tempList = (List<Mjjgda>) DBDataUtils.getInfosHasOp(Mjjgda.class, "status", "=", "0", "anchor", "=", "0");
                        boolean st = NetUtils.isConnByHttp(Constant.DOMAINTEST);// 先判断对方服务器是否存在
                        if (st) {
                            if ((tempList != null && !tempList.isEmpty()) || (tempList1 != null && !tempList1.isEmpty())) {
                                task = new RequestTask((BusinessResolver.BusinessCallback<BaseResponse>) mContext, mContext);
                                FilesBusines.putDa(task, mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, BackThread_PUTMJJGDA, tempList, tempList1);
                            }

                        } else {
                            if (task != null || task.getStatus() == AsyncTask.Status.RUNNING) {
                                task.cancel(true);

                            }
                            break;
                        }

                        break;
                    case BackThread_GETCHECKPLAN:
                        if (getCheckPlanFLag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        getCheckPlanFLag = true;
                        FilesBusines.getState(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, checkPlanAnchor, BackThread_GETCHECKPLAN);
                        break;
                    case BackThread_PUTCHECKERRORPLAN:  //上传盘点过的格子记录
                        if (putCheckErrorFLag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        putCheckErrorFLag = true;
                        List<CheckError> checkErrorList = (List<CheckError>) DBDataUtils.getInfosHasOp(CheckError.class, "status", "=", "0", "anchor", ">", "0");
                        FilesBusines.putCheckPlan(mContext, (BusinessResolver.BusinessCallback<BaseResponse>) mContext, BackThread_PUTCHECKERRORPLAN, null, checkErrorList, null);
                        break;
                    case BackThread_PUTCHECKDETAILPLAN:  //上传盘点纠错记录
                        if (putCheckDetailFLag) {
                            return;
                        }
                        isPause = false; // 防止多次点击下载,造成多个下载 flag = true;
                        putCheckDetailFLag = true;
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
        LogUtils.d("onPause");
        //停止查询
        isOnScreen = false;
        mCheckMsgThread.quit();
        //        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
        //            task.cancel(true);
        //        }

        temple = 0;
        mDaLocalCount = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
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
        //        if (flag) {
        //            isPause = true;
        //            flag = false;
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
                LogUtils.d("KEYCODE_BACK");
                if (getKfFlag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (getMjjFlag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (getMjgflag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (getDaFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (putDaFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (getCheckPlanFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (putCheckDetailFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (putCheckErrorFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LogUtils.d("onBackPressed");
            finishAfterTransition();
        } else {
            LogUtils.d("super onBackPressed");
            super.onBackPressed();
        }
    }

    @Override
    protected int getMenuID() {
        return R.menu.menu_book_detail;
    }

    MenuItem menuItem;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                LogUtils.d("我按了返回键盘");
                if (getKfFlag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (getMjjFlag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (getMjgflag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (getDaFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (putDaFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (getCheckPlanFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (putCheckDetailFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (putCheckErrorFLag == true) {
                    ToastUtils.showShort("正在更新数据库，请勿返回");
                    return false;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    super.onBackPressed();
                }
                return true;
            case R.id.action_Sync:
                if (isOnLine) {//网络 状态正常
//                    showAnimate(item); //这里开始动画 太丑了
                    item.setEnabled(false);
                    bt_action1.setEnabled(false);
                    mCheckMsgHandler.obtainMessage(BackThread_GETKF).sendToTarget();
                    bt_action2.setEnabled(false);
                    mCheckMsgHandler.obtainMessage(BackThread_GETMJJ).sendToTarget();
                    bt_action3.setEnabled(false);
                    mCheckMsgHandler.obtainMessage(BackThread_GETMJJG).sendToTarget();
                    bt_action4.setEnabled(false);
                    if (temple > 0) {
                        //如果服务器有更新数据 先获取服务器的更新数据,然后在通知线程完毕后去查找本地是否有更新数据再上传到服务器
                        backThreadmsg = mCheckMsgHandler.obtainMessage();
                        backThreadmsg.what = BackThread_GETMJJGDA;
                        mCheckMsgHandler.sendMessage(backThreadmsg);
                    } else if (mDaLocalCount > 0 && temple <= 0) {
                        //服务器没有更新，本地有更新
                        mCheckMsgHandler.obtainMessage(BackThread_PUTMJJGDA).sendToTarget();
                    }
                    bt_action5.setEnabled(false);
                    mCheckMsgHandler.obtainMessage(BackThread_GETCHECKPLAN).sendToTarget();
                    bt_action6.setEnabled(false);
                    mCheckMsgHandler.obtainMessage(BackThread_PUTCHECKERRORPLAN).sendToTarget();
                    bt_action7.setEnabled(false);
                    mCheckMsgHandler.obtainMessage(BackThread_PUTCHECKDETAILPLAN).sendToTarget();

//                    hideAnimate();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 关闭动画
     */
    private void hideAnimate() {
        if (menuItem != null) {
            View view = menuItem.getActionView();
            if (view != null) {
                view.clearAnimation();
                menuItem.setActionView(null);
            }
        }
    }

    /**
     * item做动画
     *
     * @param item
     */
    private void showAnimate(MenuItem item) {
        hideAnimate();
        menuItem = item;
        //这里使用一个ImageView设置成MenuItem的ActionView，这样我们就可以使用这个ImageView显示旋转动画了
        ImageView qrView = (ImageView) getLayoutInflater().inflate(R.layout.action_view, null);
        qrView.setImageResource(R.drawable.sync);
        menuItem.setActionView(qrView);
        //显示动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        qrView.startAnimation(animation);
    }
}
