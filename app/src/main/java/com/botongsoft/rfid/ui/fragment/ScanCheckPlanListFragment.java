package com.botongsoft.rfid.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.db.DataBaseCreator;
import com.botongsoft.rfid.ui.adapter.ScanCheckPlanListAdapter;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
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
        mListAdapter = new ScanCheckPlanListAdapter(getActivity(), mMjjList, spanCount, getActivity().getSupportFragmentManager());
        mRecyclerView.setAdapter(mListAdapter);

        //设置Item增加、移除动画
        //        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //        mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData(boolean isSavedNull) {
        searchDB();
    }

    private void searchDB() {
        mthread = new MyThread();
        new Thread(mthread).start();

    }

     class MyThread implements Runnable {
        private List<Mjj> mjjList = new ArrayList<Mjj>();

        public void run() {
            try {
                Log.e("SearchDBThreads--->", String.valueOf(Thread.currentThread().getName()));
                String[] srrArray = fw.split(",");
                Integer kfid = Integer.valueOf(srrArray[0]);
                if (kfid == 0) {
                    //库房id为0 查询所有库房的密集架
                    mjjList = (List) DBDataUtils.getInfos(Mjj.class);
                    for (Mjj mjj : mjjList) {
                        mjj.setShowLeft(true);
                        mjj.setShowRrigh(true);
                    }
                } else if (kfid != 0) {
                    Integer mjjid = Integer.valueOf(srrArray[1]);
                    if (mjjid == 0) {
                        //所有密集架
                        DbUtils db = DataBaseCreator.create();
                        try {
                            mjjList = db.findAll(Selector.from(Mjj.class).where("kfid", "=", kfid));
                            for (Mjj mjj : mjjList) {
                                mjj.setShowLeft(true);
                                mjj.setShowRrigh(true);
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else if (mjjid != 0) {
                        // "密集架面";
                        Integer m = Integer.valueOf(srrArray[2]);
                        if (m == 0) {
                            //"所有面";
                            DbUtils db = DataBaseCreator.create();
                            try {
                                mjjList = db.findAll(Selector.from(Mjj.class).where("kfid", "=", kfid).and("id", "=", mjjid));
                                for (Mjj mjj : mjjList) {
                                    mjj.setShowLeft(true);
                                    mjj.setShowRrigh(true);
                                }
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        } else if (m == 1) {
                            // "左面";
                            DbUtils db = DataBaseCreator.create();
                            try {
                                mjjList = db.findAll(Selector.from(Mjj.class).where("kfid", "=", kfid).and("id", "=", mjjid).and("noleft", "=", 0));
                                for (Mjj mjj : mjjList) {
                                    mjj.setShowLeft(true);
                                }
                            } catch (DbException e) {
                                e.printStackTrace();
                            }

                        } else if (m == 2) {
                            // "右面";
                            DbUtils db = DataBaseCreator.create();
                            try {
                                mjjList = db.findAll(Selector.from(Mjj.class).where("kfid", "=", kfid).and("id", "=", mjjid).and("noright", "=", 0));
                                for (Mjj mjj : mjjList) {
                                    mjj.setShowRrigh(true);
                                }
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                mMjjList.addAll(mjjList);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //            Message msg = new Message();
            //            Bundle b = new Bundle();// 存放数据
            //            b.putString("color", "我的");
            //            msg.setData(b);
            //
            //            myHandler.sendMessage(msg); // 向Handler发送消息，更新UI

        }
    }

}
