package com.botongsoft.rfid.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.ui.adapter.MjjgAdapter.MjjgEntityAdapter;
import com.botongsoft.rfid.ui.adapter.MjjgAdapter.SectionedSpanSizeLookup;
import com.botongsoft.rfid.ui.entity.MjjgEntity;

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
    private Mjj mjj;
    MyThread mthread;
    private MjjgEntity entity = new MjjgEntity();
    ;

    //写一个静态方法产生实例
    public static MyDialogFragment newInstance(int layoutId, Mjj mjj, String value) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle bundle = new Bundle();//把所有需要传递的数据都放在Bundle中
        bundle.putInt("layoutId", layoutId);
        bundle.putSerializable("mjj", mjj);
        bundle.putString("value", value);
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
        this.mjj = (Mjj) getArguments().getSerializable("mjj");
        String value = getArguments().getString("value");
        if (value.equals("left")) {
            searchDB(1);
        } else {
            searchDB(2);
        }
        mMjjgEntityAdapter = new MjjgEntityAdapter(getContext());
        GridLayoutManager manager = new GridLayoutManager(getContext(), mjj.getZs());
        //        http://blog.csdn.net/erjizi/article/details/49797967
        //        http://www.jianshu.com/p/675883c26ef2
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mMjjgEntityAdapter, manager,mjj.getZs()));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mMjjgEntityAdapter);


    }

    private void searchDB(int i) {

        if (i == 1) {//查左边
            mthread = new MyThread(1);
            new Thread(mthread).start();
        } else {//查右边
            mthread = new MyThread(2);
            new Thread(mthread).start();
        }
    }

    class MyThread implements Runnable {
        int i;

        public MyThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            if (i == 1) {
                Log.e("i = 1 --->", String.valueOf(i));
                Log.e("i = 1 Threads--->", String.valueOf(Thread.currentThread().getName()));
                entity.allTagsList = new ArrayList<>();
                MjjgEntity.TagsEntity te = entity.new TagsEntity();
                te.tagsName = mjj.getMc()+" 左面";
                te.tagInfoList = new ArrayList<>();
                for (int i = 1; i <= mjj.getCs()*mjj.getZs(); i++) {
                    MjjgEntity.TagsEntity.TagInfo tg = te.new TagInfo();
                    tg.tagName = i + "";
                    te.tagInfoList.add(tg);
                }
                entity.allTagsList.add(te);
            } else {
                Log.e("i = 2 --->", String.valueOf(i));
                Log.e("i = 2 BThreads--->", String.valueOf(Thread.currentThread().getName()));
                entity.allTagsList = new ArrayList<>();
                MjjgEntity.TagsEntity te = entity.new TagsEntity();
                te.tagsName = mjj.getMc() +" 右面";;
                te.tagInfoList = new ArrayList<>();
//                for (int i = 1; i <= mjj.getCs()*mjj.getZs(); i++) {
//                    HotelEntity.TagsEntity.TagInfo tg = te.new TagInfo();
//                    tg.tagName = i + "";
//                    te.tagInfoList.add(tg);
//                }
                for (int i2 = 0; i2 < mjj.getCs(); i2++) {
                    for (int j = 0; j < mjj.getZs(); j++) {
                        MjjgEntity.TagsEntity.TagInfo tg = te.new TagInfo();
                        tg.tagName = "第"+(i2 + 1)+"层第"+ (j + 1)+"组";
                        te.tagInfoList.add(tg);
                    }

                }
                entity.allTagsList.add(te);
            }
            mMjjgEntityAdapter.setData(entity.allTagsList);
        }
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
