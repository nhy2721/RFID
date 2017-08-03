package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
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
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.db.DataBaseCreator;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.utils.LogUtils;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.botongsoft.rfid.ui.adapter.ScanCheckPlanErrorAdapter;
import com.botongsoft.rfid.ui.adapter.SpinnerAdapter;
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

/**
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
    private static final int BACK_SEARCH_SPINNER = 1;
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
        //        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDescDecoration());// 添加分割线。

        initDatas();
        scanCheckPlanErrorAdapter = new ScanCheckPlanErrorAdapter(this, mDataList);
        scanCheckPlanErrorAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(scanCheckPlanErrorAdapter);
        spinnerAdapter = new SpinnerAdapter(this, spinnerList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                LogUtils.e("onItemSelected",spinnerList.get(pos).mjgid+"");
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
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
    }

    private void initSpinner() {
        mBackHandler.post(initSpinner);
    }

    private Runnable initSpinner = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            //这里定义发送通知ui更新界面
            uiMessage = mUiHandler.obtainMessage();
            uiMessage.what = UI_SUCCESS;
            //在这里读取数据库增加list值，界面显示读取的标签信息
            searchDB();
            mUiHandler.sendMessage(uiMessage);
        }
    };

    private void searchDB() {
        DbUtils db = DataBaseCreator.create();
        try {
            List<DbModel> dbModels = db.findDbModelAll(Selector.from(CheckPlanDeatil.class)
                    .select("distinct zy,pdid,kfid,mjgid,mjjid ").where("pdid", "=", pdid));
            Log.d("spinner.size()", dbModels.size() + "");
            for (DbModel dbModel : dbModels) {
                SpinnerJo sj = new SpinnerJo();
                sj.zy = dbModel.getInt("zy");
                sj.pdid = dbModel.getInt("pdid");
                sj.kfid = dbModel.getInt("kfid");
                sj.mjjid = dbModel.getInt("mjjid");
                sj.mjgid = dbModel.getInt("mjgid");
                spinnerList.add(sj);
            }
            spinner.setAdapter(spinnerAdapter);
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

        }

        @Override
        public void onItemClick(int position, int listSize, ProgressBar pb) {

        }
    };
}
