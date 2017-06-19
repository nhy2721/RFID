package com.botongsoft.rfid.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.common.utils.UIUtils;
import com.botongsoft.rfid.ui.activity.DownFLoorActivity;
import com.botongsoft.rfid.ui.activity.UpFLoorActivity;

/**
 * Created by pc on 2017/6/9.
 */

public class CategoryAdapter extends RecyclerView.Adapter {
    private String[] mCategory;
    private Context mContext;
    private Intent intent;

    //    public CategoryAdapter() {
//        mCategory = UIUtils.getContext().getResources().getStringArray(R.array.book_category);
//    }
    public CategoryAdapter(Context mContext) {
        mCategory = UIUtils.getContext().getResources().getStringArray(R.array.book_category);
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处 inflate()第二个参数要设置成 null，不然 gridView 的 item 边距无效，全部挤在一起
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CategoryHolder) holder).iv_category_icon.setImageResource(getCategoryIcon(holder.getAdapterPosition()));
        ((CategoryHolder) holder).tv_ceil_name.setText(mCategory[holder.getAdapterPosition()]);
        ((CategoryHolder) holder).ll_ceil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Toast.makeText(UIUtils.getContext(), "上架", Toast.LENGTH_SHORT).show();
                        intent = new Intent(UIUtils.getContext(), UpFLoorActivity.class);
                        intent.putExtra("index", position);
                        intent.putExtra("title", mCategory[holder.getAdapterPosition()]);
                        UIUtils.startActivity(intent);
                        break;
                    case 1:
                        Toast.makeText(UIUtils.getContext(), "下架", Toast.LENGTH_SHORT).show();
                        intent = new Intent(UIUtils.getContext(), DownFLoorActivity.class);
                        intent.putExtra("index", position);
                        intent.putExtra("title", mCategory[holder.getAdapterPosition()]);
                        UIUtils.startActivity(intent);
                        break;


                }
//                Intent intent = new Intent(UIUtils.getContext(), CategoryDetailActivity.class);
//                intent.putExtra("index", position);
//                intent.putExtra("title", mCategory[holder.getAdapterPosition()]);
//                UIUtils.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory.length;
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private ImageView iv_category_icon;
        private LinearLayout ll_ceil;
        private TextView tv_ceil_name;

        public CategoryHolder(View itemView) {
            super(itemView);
            iv_category_icon = (ImageView) itemView.findViewById(R.id.iv_category_icon);
            ll_ceil = (LinearLayout) itemView.findViewById(R.id.ll_ceil);
            tv_ceil_name = (TextView) itemView.findViewById(R.id.tv_ceil_name);
        }
    }

    private static int getCategoryIcon(int index) {
        switch (index) {
            case 0:
                return R.mipmap.ic_category_literature;
            case 1:
                return R.mipmap.ic_category_popular;
            case 2:
                return R.mipmap.ic_category_culture;
            case 3:
                return R.mipmap.ic_category_life;
            case 4:
                return R.mipmap.ic_category_management;
            case 5:
                return R.mipmap.ic_category_technology;
            case 6:
                return R.mipmap.ic_category_country;
            case 7:
                return R.mipmap.ic_category_subject;
            case 8:
                return R.mipmap.ic_category_author;
            case 9:
                return R.mipmap.ic_category_publisher;
            case 10:
                return R.mipmap.ic_category_throng;
            case 11:
                return R.mipmap.ic_category_religion;
            case 12:
                return R.mipmap.ic_category_other;
            default:
                return R.mipmap.ic_category_literature;
        }
    }
}
