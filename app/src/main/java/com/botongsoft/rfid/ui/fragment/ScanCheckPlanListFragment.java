package com.botongsoft.rfid.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.common.db.DataBaseCreator;
import com.botongsoft.rfid.common.db.SearchDb;
import com.botongsoft.rfid.ui.adapter.ScanCheckPlanListAdapter;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by pc on 2017/6/26.
 */

public class ScanCheckPlanListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private GridLayoutManager mLayoutManager;
    private ScanCheckPlanListAdapter mListAdapter;
    private List<Mjj> mMjjList = new ArrayList<Mjj>();
    private int type;
    private int pdid;
    private String fw;
    MyThread mthread;
    Handler myHandler;
    private String[] srrArray;
    private static final int MSG_SUBMIT = 0;

    public static ScanCheckPlanListFragment newInstance(int type, int pdid, String fw) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putInt("pdid", pdid);
        args.putString("fw", fw);
        ScanCheckPlanListFragment fragment = new ScanCheckPlanListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMjjList.clear();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_content, container, false);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
            pdid = getArguments().getInt("pdid");
            fw = getArguments().getString("fw");
            srrArray = fw.split(",");

        }
    }

    @Override
    protected void initEvents() {
        int spanCount = 3;
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        //        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        //        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        //            @Override
        //            public int getSpanSize(int position) {
        //                return mListAdapter.getItemColumnSpan(position);
        //            }
        //        });
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layout);// 布局管理器。
        //设置adapter
        mListAdapter = new ScanCheckPlanListAdapter(getActivity(), mMjjList, spanCount, getActivity().getSupportFragmentManager(), pdid);
        mRecyclerView.setAdapter(mListAdapter);

        //设置Item增加、移除动画
        //        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //        mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { //此处可以设置Dialog的style等等
        super.onCreate(savedInstanceState);
        //        setCancelable(false);//无法直接点击外部取消dialog
        //        setStyle(DialogFragment.STYLE_NO_FRAME,0); //NO_FRAME就是dialog无边框，0指的是默认系统Theme
        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_SUBMIT:
                        hideProgress();
                        mListAdapter.notifyDataSetChanged();
                        break;

                    default:
                        super.handleMessage(msg);//这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                        break;
                }
            }
        };

    }

    public void showProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void hideProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData(boolean isSavedNull) {
        showProgress();
        searchDB();
    }

    private void searchDB() {
        mthread = new MyThread();
        new Thread(mthread).start();

    }

    class MyThread implements Runnable {
        private List<Mjj> mjjList;

        public void run() {
            try {
                mjjList = (List<Mjj>) SearchDb.getMjjList(fw);
                DbUtils db = DataBaseCreator.create();
                //这条语句执行的意义为：根据pdid汇总统计扫描过的架子的面。有扫描过的给他个初始颜色 值为1.
                String sql = "select zy,mjjid,pdid,kfid from com_botongsoft_rfid_bean_classity_CheckError where pdid = " + pdid + " group by zy,mjjid,pdid,kfid";
                Cursor c = null; // 执行自定义sql
                try {
                    c = (Cursor) db.execQuery(sql);
                    while (c.moveToNext()) {
                        for (Mjj mjj : mjjList) {
                            // c.getColumnIndex 取到查询语句中的字段索引位置
                            int kfid_index = c.getColumnIndex("kfid");
                            int mjjid_index = c.getColumnIndex("mjjid");
                            int zy_index = c.getColumnIndex("zy");
                            int pdid_index = c.getColumnIndex("pdid");
                            //c.getInt(kfid) 根据索引位置的字段类型取出值
                            if (mjj.getKfid() == c.getInt(kfid_index) && mjj.getId() == c.getInt(mjjid_index) && c.getInt(pdid_index) == pdid) {
                                if (c.getInt(zy_index) == 1) {
                                    mjj.setLeftState(1);
                                }
                                if (c.getInt(zy_index) == 2) {
                                    mjj.setRightState(1);
                                }
                            }
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
                mMjjList.addAll(mjjList);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Message msg = myHandler.obtainMessage();
            msg.what = MSG_SUBMIT;//发送消息保存界面数据
            myHandler.sendMessage(msg);
            //            Message msg = new Message();
            //            Bundle b = new Bundle();// 存放数据
            //            b.putString("color", "我的");
            //            msg.setData(b);
            //
            //            myHandler.sendMessage(msg); // 向Handler发送消息，更新UI

        }
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mMjjList.clear();
            searchDB();
        }
    };
}
