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
import com.botongsoft.rfid.bean.classity.CheckPlanDeatil;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class ScanCheckPlanErrorAdapter extends SwipeMenuAdapter<ScanCheckPlanErrorAdapter.DefaultViewHolder> {
    private Context mContext;
    private static List<CheckPlanDeatil> list;

    private OnItemClickListener mOnItemClickListener;

    public ScanCheckPlanErrorAdapter(Context context, List<CheckPlanDeatil> list) {
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scanplandetail_menu, parent, false);
    }

    @Override
    public ScanCheckPlanErrorAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScanCheckPlanErrorAdapter.DefaultViewHolder holder, int position) {
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
        //        @BindView(R.id.tv_local)
        //        TextView tvLocal;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.imageViewStyle)
        ImageView imageViewStyle;
        @BindView(R.id.imageView)
        ImageView imageView;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //            绑定item点击事件
            itemView.setOnClickListener(this);
            //绑定image点击事件
            imageView.setOnClickListener(this);
        }

        public void setData(CheckPlanDeatil mCheckPlanDeatil, int position) {
            this.tvId.setText(String.valueOf(position + 1));
            this.tvTitle.setText(mCheckPlanDeatil.getBm() + "-" + mCheckPlanDeatil.getJlid());
            if (mCheckPlanDeatil.getType() == 2) {
                this.imageViewStyle.setImageResource(R.drawable.yuan_fales);
            } else if (mCheckPlanDeatil.getType() == 3) {
                this.imageViewStyle.setImageResource(R.drawable.yuan_waijie);
            } else if (mCheckPlanDeatil.getType() == 4) {
                this.imageViewStyle.setImageResource(R.drawable.yuan_fales);
            }
            //            if(map.getFlag()==1){
            //                this.tvTitle.setText(String.valueOf(map.getBm()+"-"+map.getJlid()+"  外借"));
            //            }else{
            //                this.tvTitle.setText(String.valueOf(map.getBm()+"-"+map.getJlid()));
            //            }
            //                        if (map.getColor() == 2) {
            //                            this.imageViewStyle.setImageResource(R.drawable.yuan_true);
            //                        } else if (map.getColor() == 0) {
            //                            this.imageViewStyle.setImageResource(R.drawable.yuan_default);
            //                        } else if (map.getColor() == 4) {
            //                            this.imageViewStyle.setImageResource(R.drawable.yuan_fales);
            //                        }else if (map.getColor() == 3) {
            //                            this.imageViewStyle.setImageResource(R.drawable.yuan_waijie);
            //                        }
            //            this.tvLocal.setText(String.valueOf(map.get("local")));
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                if (v.getId() == R.id.imageView) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), list.size() - 1);
                } else {
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                }
                //                mOnItemClickListener.onItemClick(getLayoutPosition());
            }
        }
    }

}
