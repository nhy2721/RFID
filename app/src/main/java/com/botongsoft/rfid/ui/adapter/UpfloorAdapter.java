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
import android.widget.ImageView;
import android.widget.TextView;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class UpfloorAdapter extends SwipeMenuAdapter<UpfloorAdapter.DefaultViewHolder> {
    private Context mContext;
    private static List<Map> list;

    private OnItemClickListener mOnItemClickListener;

    public UpfloorAdapter(Context context, List<Map> list) {
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upfloor_menu, parent, false);
    }

    @Override
    public UpfloorAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UpfloorAdapter.DefaultViewHolder holder, int position) {
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
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.imageView)
        ImageView imageView;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //            itemView.setOnClickListener(this);
            //            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            //            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            //            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(this);
        }

        public void setData(Map map, int position) {
            this.tvId.setText(String.valueOf(position + 1));
            this.tvTitle.setText(String.valueOf(map.get("title")));
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition(), list.size() - 1);
                //                mOnItemClickListener.onItemClick(getLayoutPosition());
            }
        }
    }

}
