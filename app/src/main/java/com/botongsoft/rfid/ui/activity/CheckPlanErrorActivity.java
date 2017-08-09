package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.SpinnerJo;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatil;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatilDel;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.db.DataBaseCreator;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.utils.ConverJavaBean;
import com.botongsoft.rfid.common.utils.LogUtils;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.botongsoft.rfid.ui.adapter.ScanCheckPlanErrorAdapter;
import com.botongsoft.rfid.ui.adapter.SpinnerAdapter;
import com.botongsoft.rfid.ui.widget.RecyclerViewDecoration.ListViewDescDecoration;
import com.botongsoft.rfid.ui.widget.WrapContentLinearLayoutManager;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.botongsoft.rfid.R.id.toolbar;
import static com.botongsoft.rfid.common.db.DBDataUtils.getInfo;

/**盘点纠错页面
 * Created by pc on 2017/8/2.
 */

public class CheckPlanErrorActivity extends BaseActivity {


    private Activity mContext;
    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private int index;
    private String fw;
    private int pdid;
    private ScanCheckPlanErrorAdapter scanCheckPlanErrorAdapter;
    private SpinnerAdapter spinnerAdapter;
    private HandlerThread mCheckMsgThread;//Handler线程池
    //后台运行的handler
    private Handler mBackHandler;
    //与UI线程管理的handler
    private Handler mUiHandler;
    //传递后台运行消息队列
    Message backMsg;
    //传递UI前台显示消息队列
    Message uiMessage;
    private static final int UI_SUCCESS = 0;
    private static final int UI_SPINNER_SUCCESS = 1;
    private static final int BACK_SEARCH_SPINNER = 1;
    private static final int BACK_SEARCH_DB = 2;
    private List<CheckPlanDeatil> mDataList = new ArrayList<>();
    private List<SpinnerJo> spinnerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_checkerror);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;
        initUiHandler();

        LinearLayoutManager layout = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSwipeMenuRecyclerView.setLayoutManager(layout);// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDescDecoration());// 添加分割线。

        initDatas();
        scanCheckPlanErrorAdapter = new ScanCheckPlanErrorAdapter(this, mDataList);
        scanCheckPlanErrorAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(scanCheckPlanErrorAdapter);
        spinnerAdapter = new SpinnerAdapter(this, spinnerList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                LogUtils.e("onItemSelected", spinnerList.get(pos).mjgid + "");
                mDataList.clear();
                backMsg = mBackHandler.obtainMessage();
                Bundle mBundle = new Bundle();
                mBundle.putInt("pos", pos);
                backMsg.setData(mBundle);
                backMsg.what = BACK_SEARCH_DB;
                mBackHandler.sendMessage(backMsg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initDatas() {
        if (mCheckMsgThread == null || !mCheckMsgThread.isAlive()) {
            mCheckMsgThread = new HandlerThread("BackThread");// 创建一个BackHandlerThread对象，它是一个线程
            mCheckMsgThread.start();// 启动线程
            //创建后台线程
            initBackThread();
        }
        //通知后台初始化查询该盘点批次的错误位置集合显示在spinner控件中
        backMsg = mBackHandler.obtainMessage();
        backMsg.what = BACK_SEARCH_SPINNER;
        mBackHandler.sendMessage(backMsg);
    }

    private void initBackThread() {
        mBackHandler = new Handler(mCheckMsgThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.e("Handler BackThread--->", String.valueOf(Thread.currentThread().getName()));
                switch (msg.what) {
                    case BACK_SEARCH_SPINNER:
                        initSpinner();
                        break;
                    case BACK_SEARCH_DB:
                        int temp = msg.getData().getInt("pos");
                        searchDBForDatas(temp);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
    }

    private void searchDBForDatas(int temp) {
        mBackHandler.post(new Runnable() {
            @Override
            public void run() {
                List tempList = (List<CheckPlanDeatil>) DBDataUtils.getInfosHasOp(CheckPlanDeatil.class,
                        "zy", "=", String.valueOf(spinnerList.get(temp).zy),
                        "pdid", "=", String.valueOf(spinnerList.get(temp).pdid),
                        "kfid", "=", String.valueOf(spinnerList.get(temp).kfid),
                        "mjjid", "=", String.valueOf(spinnerList.get(temp).mjjid),
                        "mjgid", "=", String.valueOf(spinnerList.get(temp).mjgid));
                mDataList.addAll(tempList);
                uiMessage = mUiHandler.obtainMessage();
                uiMessage.what = UI_SUCCESS;
                mUiHandler.sendMessage(uiMessage);
            }
        });
    }

    private void initSpinner() {
        mBackHandler.post(initSpinner);
    }

    private Runnable initSpinner = new Runnable() {

        @Override
        public void run() {
            //在这里读取数据库增加list值，界面显示读取的标签信息
            searchDBForSpinner();
        }
    };


    private void searchDBForSpinner() {
        DbUtils db = DataBaseCreator.create();
        StringBuffer sb = new StringBuffer();
        int tempKfid = 0;
        int tempMjjid= 0;
        int tempMjgid= 0;
        String kfName = "";
        String mjjName = "";
        String mjgName = "";
        String zyName = "";
        try {
            List<DbModel> dbModels = db.findDbModelAll(Selector.from(CheckPlanDeatil.class)
                    .select("distinct zy,pdid,kfid,mjgid,mjjid ").where("pdid", "=", pdid).orderBy("mjgid"));
            Log.d("spinner.size()", dbModels.size() + "");
            for (DbModel dbModel : dbModels) {
                SpinnerJo sj = new SpinnerJo();
                sj.zy = dbModel.getInt("zy");
                sj.pdid = dbModel.getInt("pdid");
                sj.kfid = dbModel.getInt("kfid");
                sj.mjjid = dbModel.getInt("mjjid");
                sj.mjgid = dbModel.getInt("mjgid");

                if (tempKfid != sj.kfid) {
                    tempKfid = dbModel.getInt("kfid");
                    Kf kf = (Kf) getInfo(Kf.class, "id", String.valueOf(sj.kfid));
                    kfName = kf.getMc();
                }
                if (tempMjjid != sj.mjjid) {
                    tempMjjid = dbModel.getInt("mjjid");
                    Mjj mjj = (Mjj) getInfo(Mjj.class, "id", String.valueOf(sj.mjjid));
                    mjjName = mjj.getMc();
                }
                if (tempMjgid != sj.mjgid) {
                    tempMjgid = dbModel.getInt("mjgid");
                    Mjjg mjg = (Mjjg) getInfo(Mjjg.class, "id", String.valueOf(sj.mjgid));
                    mjgName = mjg.getMc();
                }
                if (sj.zy == 1) {
                    zyName = "左面";
                } else {
                    zyName = "右面";
                }
                sb.setLength(0);//清空sb
                sb.append(kfName).append("/").append(mjjName).append("/").append(mjgName).append("/").append(zyName);
                sj.title = sb.toString();
                spinnerList.add(sj);
            }
            mUiHandler.obtainMessage(UI_SPINNER_SUCCESS).sendToTarget();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initUiHandler() {
        mUiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UI_SUCCESS:
                        scanCheckPlanErrorAdapter.notifyDataSetChanged();
                        break;
                    case UI_SPINNER_SUCCESS:
                        spinner.setAdapter(spinnerAdapter);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
    }

    @Override
    protected void initEvents() {
        index = getIntent().getIntExtra("index", 0);
        setTitle(getIntent().getStringExtra("title"));
        fw = getIntent().getStringExtra("fw");
        pdid = getIntent().getIntExtra("pdid", 0);
        mToolbar.setSubtitle(String.valueOf(pdid));//子标题
    }

    @Override
    public void onSuccess(BaseResponse baseResponse, int act) {

    }

    @Override
    public void onError(BusinessException e, int act) {

    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
        }

        @Override
        public void onItemClick(int position, int listSize) {

            new AlertDialog.Builder(CheckPlanErrorActivity.this)
                    .setMessage("确定要忽略这条错误吗？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (position != -1) {
                                if (mDataList.get(position).getStatus() == 0) {
                                    //                                    LogUtils.e("delposition---->", String.valueOf(position));
                                    //                                    LogUtils.e("del lid---->", String.valueOf(mDataList.get(position).getLid()));
                                    //                                    LogUtils.e("del bm---->", String.valueOf(mDataList.get(position).getBm()));
                                    //                                    LogUtils.e("del Jlid---->", String.valueOf(mDataList.get(position).getJlid()));
                                    DBDataUtils.deleteInfo(CheckPlanDeatil.class, "lid", String.valueOf(mDataList.get(position).getLid()));
                                } else {
                                    CheckPlanDeatil checkPlanDeatil = mDataList.get(position);
                                    CheckPlanDeatilDel cd = ConverJavaBean.toAnotherObj(checkPlanDeatil, CheckPlanDeatilDel.class);
                                    DBDataUtils.save(cd);
                                    DBDataUtils.deleteInfo(CheckPlanDeatil.class, "lid", String.valueOf(mDataList.get(position).getLid()));
                                }
                                mDataList.remove(position);
                                scanCheckPlanErrorAdapter.notifyItemRemoved(position);
                                scanCheckPlanErrorAdapter.notifyDataSetChanged();
                                //                                mDataLists.remove(position);
                                //                                scanCheckPlanDetailAdapter.notifyItemRemoved(position);
                                //                                scanCheckPlanDetailAdapter.notifyItemRangeChanged(position, listSize);
                            }
                        }
                    })
                    .create().show();
        }

        @Override
        public void onItemClick(int position, int listSize, ProgressBar pb) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        if (mBackHandler != null) {
            mCheckMsgThread.quit();
        }
        mBackHandler.removeCallbacksAndMessages(null);
        finish();
    }
}
