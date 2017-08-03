package com.botongsoft.rfid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.SpinnerJo;

import java.util.List;

/**
 * Created by pc on 2017/8/2.
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<SpinnerJo> mList;
    private Context mContext;

    public SpinnerAdapter(Context pContext, List<SpinnerJo> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.spinner_item, null);
        if(convertView!=null) {
//            ImageView imageView = (ImageView)convertView.findViewById(R.id.image);
//            imageView.setImageResource(R.drawable.ic_launcher);
            TextView _TextView1=(TextView)convertView.findViewById(R.id.textView1);
//            TextView _TextView2=(TextView)convertView.findViewById(R.id.textView2);
            _TextView1.setText(mList.get(position).kfid+"/"+mList.get(position).mjjid+"/"
                    +mList.get(position).mjgid+"/"+mList.get(position).zy);

        }
        return convertView;
    }
}
