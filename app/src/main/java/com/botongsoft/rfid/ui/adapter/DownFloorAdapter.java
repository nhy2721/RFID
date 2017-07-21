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
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class DownFloorAdapter extends SwipeMenuAdapter<DownFloorAdapter.DefaultViewHolder> {
    private Context mContext;
    private static List<Mjjgda> list;

    private OnItemClickListener mOnItemClickListener;

    public DownFloorAdapter(Context context, List<Mjjgda> list) {
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_downfloor_menu, parent, false);
    }

    @Override
    public DownFloorAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DownFloorAdapter.DefaultViewHolder holder, int position) {
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
        @BindView(R.id.tv_local)
        TextView tvLocal;
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
            //绑定item点击事件
            //            itemView.setOnClickListener(this);
            //绑定image点击事件
            imageView.setOnClickListener(this);
        }

        public void setData(Mjjgda mjjgda, int position) {
            this.tvId.setText(String.valueOf(position + 1));
//            this.tvTitle.setText(String.valueOf(map.get("title")));
//            this.tvLocal.setText(String.valueOf(map.get("local")));
            this.tvTitle.setText(mjjgda.getTitle());
            this.tvLocal.setText(mjjgda.getScanInfo());
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
