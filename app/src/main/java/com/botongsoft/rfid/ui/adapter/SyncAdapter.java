package com.botongsoft.rfid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.busines.MyBusinessInfo;
import com.botongsoft.rfid.common.utils.ListUtils;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.botongsoft.rfid.ui.activity.SyncActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pc on 2017/7/11.
 */

public class SyncAdapter extends SwipeMenuAdapter<SyncAdapter.DefaultViewHolder> {
    private Context mContext;
    private static List<MyBusinessInfo> myBusinessInfo;

    private OnItemClickListener mOnItemClickListener;

    public SyncAdapter(SyncActivity syncActivity, List<MyBusinessInfo> syncListData) {
        super();
        this.mContext = syncActivity;
        this.myBusinessInfo = syncListData;
    }

    public void setCurList(List<MyBusinessInfo> list) {
        if (!ListUtils.isEmpty(list)) {
            myBusinessInfo.clear();
            myBusinessInfo.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sync_info, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(myBusinessInfo.get(position), position);
    }

    @Override
    public int getItemCount() {
        return myBusinessInfo == null ? 0 : myBusinessInfo.size();
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.bt_action)
        Button bt_action;
        @BindView(R.id.tv_oleNsize)
        TextView tv_oleNsize;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.pb)
        ProgressBar pb;

        public OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bt_action.setOnClickListener(this);
        }

        public void setData(MyBusinessInfo myBusinessInfo, int position) {
            this.tv_name.setText(myBusinessInfo.getName());
            this.tv_oleNsize.setText(myBusinessInfo.getListSize() + "条记录需要更新");
            if (myBusinessInfo.getListSize() > 0) {
                this.tv_status.setText("未更新");
            }
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition(), myBusinessInfo.size() - 1);
                //                mOnItemClickListener.onItemClick(getLayoutPosition());
            }
        }
    }
}
