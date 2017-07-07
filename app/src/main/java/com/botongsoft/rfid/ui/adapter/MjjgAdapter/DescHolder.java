package com.botongsoft.rfid.ui.adapter.MjjgAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.botongsoft.rfid.R;


/**
 * Created by lyd10892 on 2016/8/23.
 */

public class DescHolder extends RecyclerView.ViewHolder {
    public TextView descView;
    public   RelativeLayout mRelativeLayout1;
    public DescHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        descView = (TextView) itemView.findViewById(R.id.tv_desc);
        mRelativeLayout1 = (RelativeLayout) itemView.findViewById(R.id.RelativeLayout);
    }
}
