package com.botongsoft.rfid.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.ui.adapter.MjjgAdapter.SectionedSpanSizeLookup;
import com.botongsoft.rfid.ui.adapter.MjjgAdapter.MjjgEntityAdapter;
import com.botongsoft.rfid.ui.entity.HotelEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pc on 2017/6/27.
 */

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    protected View mRootView;
    private MjjgEntityAdapter mMjjgEntityAdapter;

    //写一个静态方法产生实例
    public static MyDialogFragment newInstance(int layoutId) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle bundle = new Bundle();//把所有需要传递的数据都放在Bundle中
        bundle.putInt("layoutId", layoutId);

        fragment.setArguments(bundle);//通过setArguments把Bundle传递过去
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Dialog dialog = getDialog();
        if (dialog != null) {//有些场景下是获取不到的
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置Dialog没有标题。需在setContentView之前设置，在之后设置会报错
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        }
        mRootView = inflater.inflate(getArguments().getInt("layoutId"), null);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    private void initDate() {
        HotelEntity entity =  new HotelEntity();
        entity.allTagsList = new ArrayList<>();
        HotelEntity.TagsEntity te  = entity.new TagsEntity();
        te.tagsName = "test";
        te.tagInfoList = new ArrayList<>();
        for(int i = 0 ;i<=100 ;i++){
            HotelEntity.TagsEntity.TagInfo tg =  te.new TagInfo();
            tg.tagName =i+"";
            te.tagInfoList.add(tg);
        }
        entity.allTagsList.add(te);

        mMjjgEntityAdapter = new MjjgEntityAdapter(getContext());
        GridLayoutManager manager = new GridLayoutManager(getContext(),8);
        //        http://blog.csdn.net/erjizi/article/details/49797967
        //        http://www.jianshu.com/p/675883c26ef2
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mMjjgEntityAdapter,manager));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mMjjgEntityAdapter);
        mMjjgEntityAdapter.setData(entity.allTagsList);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) { //此处可以设置Dialog的style等等
        super.onCreate(savedInstanceState);
        //        setCancelable(false);//无法直接点击外部取消dialog
        //        setStyle(DialogFragment.STYLE_NO_FRAME,0); //NO_FRAME就是dialog无边框，0指的是默认系统Theme
    }

    @Override
    public void onClick(View v) {  //点击事件
        switch (v.getId()) {
            //            case R.id.cancel:
            //                dismiss();
            //                break;
            //            case R.id.call:
            //                Toaster.showShort(getActivity(), "打电话");
            //                break;
            //            default:
            //                break;
        }
    }
}
