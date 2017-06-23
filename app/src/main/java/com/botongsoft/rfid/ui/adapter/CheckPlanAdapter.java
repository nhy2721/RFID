/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.botongsoft.rfid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.CheckPlan;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.botongsoft.rfid.R.id.imageView;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class CheckPlanAdapter extends SwipeMenuAdapter<CheckPlanAdapter.DefaultViewHolder> {
    private Context mContext;
    private static List<CheckPlan> list;

    private OnItemClickListener mOnItemClickListener;

    public CheckPlanAdapter(Context context, List<CheckPlan> list) {
        super();
        this.mContext = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkplan_menu, parent, false);
    }

    @Override
    public CheckPlanAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CheckPlanAdapter.DefaultViewHolder holder, int position) {
        holder.setData(list.get(position), position);
    }
    // 在指定位置添加一个新的Item
    //    public void addItem(Person person,int positionToAdd)
    //    {
    //        list.add(person);
    //        // 通知RecyclerView控件插入了某个Item
    //        notifyItemInserted(positionToAdd);
    //    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.checkplan_title_tv)
        TextView checkplan_title_tv;
        @BindView(R.id.checkplan_bz_tv)
        TextView checkplan_bz_tv;
        @BindView(R.id.checkplan_fw_tv)
        TextView checkplan_fw_tv;

        @BindView(R.id.news_summary_photo_iv)
        TextView news_summary_photo_iv;
        @BindView(R.id.checkplan_bz)
        TextView checkplan_bz;
        @BindView(R.id.checkplan_fw)
        TextView checkplan_fw;

        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //            绑定item点击事件
            itemView.setOnClickListener(this);

        }

        public void setData(CheckPlan mCheckPlan, int position) {
            this.news_summary_photo_iv.setText("计划编号");
            this.checkplan_title_tv.setText(String.valueOf(mCheckPlan.getPdid()));
            this.checkplan_bz.setText("备注");
            this.checkplan_bz_tv.setText(String.valueOf(mCheckPlan.getBz()));
            this.checkplan_fw.setText("盘点范围");
            this.checkplan_fw_tv.setText(String.valueOf(mCheckPlan.getFw()));
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                if(v.getId()== imageView){
                    mOnItemClickListener.onItemClick(getAdapterPosition(), list.size() - 1);
                }else{
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                }
                //                mOnItemClickListener.onItemClick(getLayoutPosition());
            }
        }
    }

}
