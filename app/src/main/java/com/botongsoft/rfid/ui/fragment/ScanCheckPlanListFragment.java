package com.botongsoft.rfid.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.botongsoft.rfid.R;


/**
 * Created by pc on 2017/6/26.
 */

public class ScanCheckPlanListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private int type;

    public static ScanCheckPlanListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
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
        type = getArguments().getInt("type");
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData(boolean isSavedNull) {

    }
}
