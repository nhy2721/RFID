package com.botongsoft.rfid.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.botongsoft.rfid.R;

import butterknife.BindView;

/**
 * Created by pc on 2017/6/26.
 */

public class ScanCheckPlanDetailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int type;
    private int pdid;
    private String fw;

    public static ScanCheckPlanDetailFragment newInstance(int type, int pdid, String fw) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putInt("pdid", pdid);
        args.putString("fw", fw);
        ScanCheckPlanDetailFragment fragment = new ScanCheckPlanDetailFragment();
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
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);
    }

    @Override
    protected void initData(boolean isSavedNull) {

    }
}
