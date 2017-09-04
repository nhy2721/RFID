package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.Receiver.KeyReceiver;
import com.botongsoft.rfid.bean.classity.Epc;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.Constant;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.db.MjgdaSearchDb;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.utils.SoundUtil;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.botongsoft.rfid.ui.adapter.UpGuidanceAdapter;
import com.botongsoft.rfid.ui.fragment.SettingDialogFragment;
import com.botongsoft.rfid.ui.widget.RecyclerViewDecoration.ListViewDescDecoration;
import com.handheld.UHFLonger.UHFLongerManager;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.botongsoft.rfid.R.id.appBarLayout;
import static com.botongsoft.rfid.R.id.toolbar;

/**
 * 上架引导
 * Created by pc on 2017/6/12.
 */
public class UpGuidanceActivity extends BaseActivity {
    private static final int UI_SUCCESS = 0;

    @BindView(appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(toolbar)
    Toolbar mToolbar;
    //    @BindView(R.id.swipe_layout)
    //    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    //    @BindView(R.id.tx_layout)
    //    TextInputLayout mTextInputLayout;
    //    @BindView(R.id.input_tx)
    //    TextInputEditText mTextInputEditText;
    @BindView(R.id.st_saoma)
    Switch mSwitch;
    //    @BindView(R.id.tv_info)
    //    TextView mTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private int index;
    private String editString;
    private List<Map> mDataList;
    private UpGuidanceAdapter mUpGuidanceAdapter;
    private int size = 50;
    private static int size1 = 1;
    private Activity mContext;
    private HandlerThread mCheckMsgThread;//Handler线程池
    //后台运行的handler
    private Handler mCheckMsgHandler;
    //与UI线程管理的handler
    private Handler mHandler;
    private boolean isOnScreen;//是否在屏幕上
    private boolean isRun;//是否在RFID读取
    private static final int MSG_UPDATE_INFO = 1;
    //传递后台运行消息队列
    Message msg;
    //传递UI前台显示消息队列
    Message mHandlerMessage;
    Bundle mBundle;
    Thread thread;
    private static UHFLongerManager manager;
    private KeyReceiver keyReceiver;
    private boolean runFlag = true;
    private boolean startFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_upguidance);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;
        SoundUtil.initSoundPool(this);//
        try {
            manager = BaseApplication.application.getmanager();
            SharedPreferences sp = getSharedPreferences("power", 0);
            //            int value = ShareManager.getInt(this, "power");
            int value = sp.getInt("value", 0);
            if (value == 0) {
                value = 30;
            }
            manager.setOutPower((short) value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        initUiHandler();
        //        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener); //滑动布局的滑动监听
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mSwipeMenuRecyclerView.setLayoutManager(layout);// 布局管理器。
        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layout.setReverseLayout(true);//列表翻转
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        //        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDescDecoration());// 添加分割线。

//        mTextInputEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // 输入前的监听
//                //                Log.e("输入前确认执行该方法", "开始输入");
//                mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // 输入的内容变化的监听
//                //               Log.e("输入过程中执行该方法", "文字变化");
//                if (mCheckMsgHandler != null) {
//                    mCheckMsgHandler.removeCallbacks(delayRun);
//                }
//                mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                // 输入后的监听
//                //                Log.e("输入结束执行该方法", "输入结束");
//                Log.e("Handler textChanged--->", String.valueOf(Thread.currentThread().getName()));
//                if (mTextInputEditText.length() != 0) {
//                    if (mCheckMsgHandler != null) {
//                        mCheckMsgHandler.removeCallbacks(delayRun);
//                    }
//                    //延迟800ms，如果不再输入字符，则执行该线程的run方法 模拟扫描输入
//                    msg = mCheckMsgHandler.obtainMessage();
//                    msg.what = MSG_UPDATE_INFO;
//                    mCheckMsgHandler.sendMessageDelayed(msg, Constant.delayRun);
//                }
//            }
//        });
        // 添加滚动监听。
        //        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        mUpGuidanceAdapter = new UpGuidanceAdapter(this, mDataList);
        mUpGuidanceAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mUpGuidanceAdapter);
    }

    @Override
    protected void initEvents() {
        index = getIntent().getIntExtra("index", 0);
        setTitle(getIntent().getStringExtra("title"));
        mDataList = new ArrayList<>();
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 开启switch，设置提示信息
                    startFlag = true;
                } else {
                    // 关闭swtich，设置提示信息
                    startFlag = false;
                }
            }
        });
        mProgressBar.setVisibility(View.GONE);

    }

    private void initUiHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UI_SUCCESS:
//                        mTextInputEditText.setText("");
                        smoothMoveToPosition(mSwipeMenuRecyclerView, mDataList.size() + 1);
                        mUpGuidanceAdapter.notifyDataSetChanged();
                        break;
                    default:
                        super.handleMessage(msg);//这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                        break;
                }
            }
        };
    }

    private void initBackThread() {
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.e("Handler BackThread--->", String.valueOf(Thread.currentThread().getName()));
                switch (msg.what) {
                    case MSG_UPDATE_INFO:
                        checkForUpdate();//
                        break;
                    default:
                        super.handleMessage(msg);//这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                        break;
                }
            }
        };
    }


    /**
     * 延迟线程，看是否还有下一个字符输入
     */

    private void checkForUpdate() {
        mCheckMsgHandler.post(delayRun);
    }

    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            Log.e("Handler delayRun--->", String.valueOf(Thread.currentThread().getName()));
            //            mHandler.obtainMessage(UI_SUCCESS).sendToTarget();
            //            mTextView.post(new Runnable() {
            //                @Override
            //                public void run() {
            //                    Log.e("setText--->", String.valueOf(Thread.currentThread().getName()));
            //                    mTextView.setText(mTextInputEditText.getText());
            //                    mTextInputEditText.setText("");
            //                }
            //            });
            //这里定义发送通知ui更新界面
//            mHandlerMessage = mHandler.obtainMessage();
//            mHandlerMessage.what = UI_SUCCESS;
            //在这里读取数据库增加list值，界面显示读取的标签信息
//            editString = mTextInputEditText.getText().toString();
//            searchDB(editString);
//            mHandler.sendMessage(mHandlerMessage);
        }
    };

    private void searchDB(String editString) {
        int lx = Constant.getLx(editString);//根据传入的值返回对象类型
//        String temp[] = editString.split("-");
        boolean tempStr = true;
        //防止扫描重复判断
        if (mDataList.size() > 0) {
            for (Map map : mDataList) {
                if (map.get("epccode").toString().equals(editString)) {
                    tempStr = false;
                    break;
                }
            }
        }
        if (tempStr) {
            switch (lx) {
                case Constant.LX_MJGDA:
                    String kfname = "";
                    String mjjname = "";
                    String nLOrR = "";
                    Mjj mjj = null;
                    Kf kf = null;
                    // 查询文件存放的位置
                    Mjjgda mjjgda = null;
                    Epc ecp = (Epc) DBDataUtils.getInfo(Epc.class, "epccode", editString);
                    if (ecp != null) {
                        mjjgda = MjgdaSearchDb.getInfoHasOp(Mjjgda.class, "bm", "=",ecp.getBm() + "",
                                "jlid", "=", ecp.getJlid() + "", "status", "!=", "-1");
                        if (mjjgda != null) {
                            Map map = new HashMap();
                            map.put("id", size1++);
                            map.put("title", ecp.getArchiveno());
                            map.put("epccode", editString);
                            map.put("bm", mjjgda.getBm());
                            map.put("jlid", mjjgda.getJlid());
                            Mjjg mjjg = (Mjjg) DBDataUtils.getInfo(Mjjg.class, "id", mjjgda.getMjgid() + "");
                            if (mjjg != null) {
                                nLOrR = mjjg.getZy() == 1 ? "左" : "右";
                                mjj = (Mjj) DBDataUtils.getInfo(Mjj.class, "id", mjjg.getMjjid() + "");
                            }
                            if (mjj != null) {
                                mjjname = mjj.getMc() + "/";
                                kf = (Kf) DBDataUtils.getInfo(Kf.class, "id", mjj.getKfid() + "");
                            }

                            if (kf != null) {
                                kfname = kf.getMc() + "/";
                            }
                            String name = kfname + mjjname + nLOrR + "/" + mjjg.getZs() + "组" + mjjg.getCs() + "层";
                            map.put("local", name);//界面显示存放位置
                            mDataList.add(map);
                        }
                    }else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ToastUtils.showShort("没查询到该条扫描记录" + editString);
                            }
                        });
                    }

                    break;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnScreen = true;
        //        if(isOnScreen && isRun) {
        if (isOnScreen) {
            //开启新进程
            mCheckMsgThread = new HandlerThread("BackThread");// 创建一个BackHandlerThread对象，它是一个线程
            mCheckMsgThread.start();// 启动线程
            //创建后台线程
            initBackThread();
            thread = new ThreadMe();
            thread.start();
            mSwitch.setChecked(false);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止查询
        isOnScreen = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
        startFlag = false;
        thread.interrupt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止查询
        isOnScreen = false;
        startFlag = false;
        runFlag = false;
        size1 = 0;
        //释放资源
        if (mCheckMsgHandler != null) {
            mCheckMsgThread.quit();
        }
        mCheckMsgHandler.removeCallbacksAndMessages(null);
        finish();
    }

    /**
     * 刷新监听。
     */
    //    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
    //        @Override
    //        public void onRefresh() {
    //            mSwipeMenuRecyclerView.postDelayed(() -> mSwipeRefreshLayout.setRefreshing(false), 2000);
    //        }
    //    };
    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        /**
         * 当RecyclerView滑动时触发
         * 类似点击事件的MotionEvent.ACTION_MOVE
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。
                //                size += 50;
                //                for (int i = size - 50; i < size; i++) {
                //                    Map map = new HashMap();
                //                    map.put("id", i);
                //                    map.put("title", "我是第" + i + "个。");
                //                    mDataList.add(map);
                //                }
                //                mUpGuidanceAdapter.notifyDataSetChanged();
            }
        }

        /**
         * 当RecyclerView的滑动状态改变时触发
         */
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mShouldScroll) {
                mShouldScroll = false;
                smoothMoveToPosition(mSwipeMenuRecyclerView, mToPosition);
            }
        }
    };
    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
           /* {
                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_green)// 点击的背景。
                        .setImage(R.mipmap.ic_action_add) // 图标。
                        .setWidth(width) // 宽度。
                        .setHeight(height); // 高度。
                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);

                swipeLeftMenu.addMenuItem(closeItem); // 添加一个按钮到左侧菜单。
            }*/

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
           /* {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。

                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
            }*/
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            //详细信息
            StringBuilder sb = new StringBuilder();
            sb.append("Title:").append(mDataList.get(position).get("title")).append("\n");
            sb.append("位置:").append(mDataList.get(position).get("local")).append("\n");
            new AlertDialog.Builder(BaseActivity.activity)
                    .setTitle("详细信息：")
                    .setMessage(sb)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    })
                    .create().show();
        }

        @Override
        public void onItemClick(int position, int listSize) {
            //            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();

            if (position != -1) {
                mDataList.remove(position);
                mUpGuidanceAdapter.notifyItemRemoved(position);
                mUpGuidanceAdapter.notifyItemRangeChanged(position, listSize);
            }
        }

        @Override
        public void onItemClick(int position, int listSize, ProgressBar pb) {

        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }

            // TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                mDataList.remove(adapterPosition);
                mUpGuidanceAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };

    @Override
    public void onSuccess(BaseResponse baseResponse, int act) {

    }

    @Override
    public void onError(BusinessException e, int act) {

    }

    class ThreadMe extends Thread {
        private List<String> epcList;

        @Override
        public void run() {
            super.run();
            while (runFlag) {

                if (startFlag) {
                    epcList = manager.inventoryRealTime(); //
                    if (epcList != null && !epcList.isEmpty()) {
                        SoundUtil.play(1, 0);
                        Message sMessage = mHandler.obtainMessage();
                        sMessage.what = UI_SUCCESS;
                        for (String epc : epcList) {
                            searchDB(epc);

                        }
                        mHandler.sendMessage(sMessage);
                    }
                    epcList = null;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    protected int getMenuID() {
        return R.menu.menu_set_power;
    }


    MenuItem menuItem;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Power:
                //                                Intent intent = new Intent(this, SettingPower.class);
                //                                startActivity(intent);
                mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            // 开启switch，设置提示信息
                            startFlag = true;
                        } else {
                            // 关闭swtich，设置提示信息
                            startFlag = false;
                        }
                    }
                });
                startFlag = false;
                mSwitch.setChecked(false);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                SettingDialogFragment dialogFragment  = SettingDialogFragment.newInstance(R.layout.setting_power_dialog);
                dialogFragment.show(ft, "settingDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
