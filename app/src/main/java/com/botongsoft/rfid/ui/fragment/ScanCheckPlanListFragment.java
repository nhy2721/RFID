package com.botongsoft.rfid.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.ui.adapter.ScanCheckPlanListAdapter;

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
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置adapter
        Mjj mjj = new Mjj();
        mjj.setMc("2test");
        mjj.setKfid(422);
        mjj.setCs(5);
        mjj.setZs(5);
        mjj.setNoleft(0);
        mjj.setNoright(1);
        Mjj mjj1 = new Mjj();
        mjj1.setMc("1test");
        mjj1.setKfid(422);
        mjj1.setCs(5);
        mjj1.setZs(5);
        mjj1.setNoleft(0);
        mjj1.setNoright(0);
        mMjjList.add(mjj);
        mMjjList.add(mjj1);
        mListAdapter = new ScanCheckPlanListAdapter(getActivity(), mMjjList, spanCount,getActivity().getSupportFragmentManager() );
        mRecyclerView.setAdapter(mListAdapter);

        //设置Item增加、移除动画
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData(boolean isSavedNull) {

    }
}
