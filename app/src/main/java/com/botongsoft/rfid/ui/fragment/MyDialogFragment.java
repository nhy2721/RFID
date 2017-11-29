package com.botongsoft.rfid.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.CheckError;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.common.Constant;
import com.botongsoft.rfid.common.db.DataBaseCreator;
import com.botongsoft.rfid.ui.adapter.MjjgAdapter.MjjgEntityAdapter;
import com.botongsoft.rfid.ui.adapter.MjjgAdapter.SectionedSpanSizeLookup;
import com.botongsoft.rfid.ui.entity.MjjgEntity;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pc on 2017/6/27.
 */

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final int MSG_SUBMIT = 0;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    protected View mRootView;
    private MjjgEntityAdapter mMjjgEntityAdapter;
    private Mjj mjj;
    MyThread mthread;
    Handler myHandler;
    private MjjgEntity entity = new MjjgEntity();
    private int pdid;
    MjjgEntity.TagsEntity.TagInfo tg = null;

    //写一个静态方法产生实例
    public static MyDialogFragment newInstance(int layoutId, Mjj mjj, String value, int pdid) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle bundle = new Bundle();//把所有需要传递的数据都放在Bundle中
        bundle.putInt("layoutId", layoutId);
        bundle.putSerializable("mjj", mjj);
        bundle.putString("value", value);
        bundle.putInt("pdid", pdid);
        fragment.setArguments(bundle);//通过setArguments把Bundle传递过去
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.95), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Dialog dialog = getDialog();
        if (dialog != null) {//有些场景下是获取不到的
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置Dialog没有标题。需在setContentView之前设置，在之后设置会报错
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
            //            dialog.getWindow().setDimAmount(0);//去掉遮罩层
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
        pdid = getArguments().getInt("pdid");
        if (value.equals("left")) {
            searchDB(Constant.VALUE_LEFT);
        } else {
            searchDB(Constant.VALUE_RIGHT);
        }
        mMjjgEntityAdapter = new MjjgEntityAdapter(getContext());
        int zs = mjj.getZs();
        GridLayoutManager manager = new GridLayoutManager(getContext(), zs);
        //        http://blog.csdn.net/erjizi/article/details/49797967
        //        http://www.jianshu.com/p/675883c26ef2
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mMjjgEntityAdapter, manager, mjj.getZs()));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mMjjgEntityAdapter);


    }

    private void searchDB(int i) {

        if (i == Constant.VALUE_LEFT) {//查左边
            mthread = new MyThread(Constant.VALUE_LEFT);
            new Thread(mthread).start();
        } else {//查右边
            mthread = new MyThread(Constant.VALUE_RIGHT);
            new Thread(mthread).start();
        }
    }

    class MyThread implements Runnable {
        int lOrR;

        public MyThread(int i) {
            this.lOrR = i;
        }

        @Override
        public void run() {
            entity.allTagsList = new ArrayList<>();
            MjjgEntity.TagsEntity te = entity.new TagsEntity();
            te.tagInfoList = new ArrayList<>();
            if (lOrR == Constant.VALUE_LEFT) {
                te.tagsName = mjj.getMc() + " 左面";
            } else {
                te.tagsName = mjj.getMc() + " 右面";
            }
            //根据密集架左右 id 查询出密集格显示到页面
            int csLen = mjj.getCs();
            int zsLen = mjj.getZs();
            for (int i2 = 0; i2 < csLen; i2++) {
                for (int j = 0; j < zsLen; j++) {
                    tg = te.new TagInfo();
                    // tg.setTagName("第" + (i2 + 1) + "层第" + (j + 1) + "组");
                    Mjjg mjjg = searchDBByMjjg(mjj.getId(), lOrR, i2 + 1, j + 1);
                    if (mjjg != null) {
                        tg.setTagName(mjjg.getMc());
                        tg.setTagId(mjjg.getId());
                        //这里需要查询已经扫描过checkerror的记录表来改变格子的颜色
                        CheckError ce = searchDBByCheckError(mjjg.getId(), pdid, mjj.getKfid(), mjj.getId(), lOrR);
                        if (ce != null) {
                            tg.setUpdateColor("old");
                        }
                    }
                    te.tagInfoList.add(tg);
                }

            }
            entity.allTagsList.add(te);

            Message msg = myHandler.obtainMessage();
            msg.what = MSG_SUBMIT;//发送消息保存界面数据
            myHandler.sendMessage(msg);

        }

        private CheckError searchDBByCheckError(int mjjgid, int pdid, int kfid, int mjjid, int i) {
            CheckError ce = null;
            DbUtils db = DataBaseCreator.create();
            try {
                ce = db.findFirst(Selector.from(CheckError.class).where("mjgid", "=", mjjgid).and("pdid", "=", pdid).and("kfid", "=", kfid).and("mjjid", "=", mjjid).and("zy", "=", i));
            } catch (DbException e) {
                e.printStackTrace();
            }
            return ce;
        }

        private Mjjg searchDBByMjjg(int mjjId, int value, int cs, int zs) {
            Mjjg mjjg = null;
            DbUtils db = DataBaseCreator.create();
            try {
                mjjg = db.findFirst(Selector.from(Mjjg.class).where("mjjid", "=", mjjId).and("zy", "=", value).and("cs", "=", cs).and("zs", "=", zs));
            } catch (DbException e) {
                e.printStackTrace();
            }
            return mjjg;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { //此处可以设置Dialog的style等等
        super.onCreate(savedInstanceState);
        //        setCancelable(false);//无法直接点击外部取消dialog
        //        setStyle(DialogFragment.STYLE_NO_FRAME,0); //NO_FRAME就是dialog无边框，0指的是默认系统Theme
        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_SUBMIT:
                        mMjjgEntityAdapter.setData(entity.allTagsList);
                        break;

                    default:
                        super.handleMessage(msg);//这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                        break;
                }
            }
        };

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
