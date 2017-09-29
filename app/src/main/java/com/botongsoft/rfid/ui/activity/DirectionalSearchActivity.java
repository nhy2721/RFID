package com.botongsoft.rfid.ui.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.Receiver.KeyReceiver;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.common.utils.KeyBoardUtils;
import com.botongsoft.rfid.common.utils.ListUtils;
import com.botongsoft.rfid.common.utils.LogUtils;
import com.botongsoft.rfid.common.utils.ScreenUtils;
import com.botongsoft.rfid.common.utils.SoundUtil;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.common.utils.UIUtils;
import com.botongsoft.rfid.hodler.SearchViewHolder;
import com.botongsoft.rfid.listener.OnItemClickListener;
import com.botongsoft.rfid.ui.adapter.DirectionalSearchAdapter;
import com.botongsoft.rfid.ui.fragment.SettingDialogFragment;
import com.botongsoft.rfid.ui.widget.RecyclerViewDecoration.ListViewDescDecoration;
import com.botongsoft.rfid.utils.customtabs.CustomTabActivityHelper;
import com.handheld.UHFLonger.UHFLongerManager;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.botongsoft.rfid.R.id.appBarLayout;
import static com.botongsoft.rfid.R.id.toolbar;

/**
 * 定向查找
 */
public class DirectionalSearchActivity extends BaseActivity {
    private static final int UI_SUCCESS = 0;
    private static final int UI_SUBMITSUCCESS = 1;
    private static final int UI_SUBMITSENDFAILUREMSG = 2;
    static final int SEND_SEARCHRESULT_REQUEST = 0;
    static final int CALL_REQUEST = 1;
    @BindView(appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(toolbar)
    Toolbar mToolbar;
    //    @BindView(R.id.swipe_layout)
    //    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    //    @BindView(R.id.fab)
    //    FloatingActionButton mFab;
    //    @BindView(R.id.tx_layout)
    //    TextInputLayout mTextInputLayout;
    //    @BindView(R.id.input_tx)
    //    TextInputEditText mTextInputEditText;
    //    @BindView(R.id.tv_info)
    //    TextView mTextView;
    @BindView(R.id.st_saoma)
    Switch mSwitch;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private int index;
    private String editString;
    private List<Mjjgda> mDataList = new ArrayList<>();
    ;
    //    private List<MjjgdaDelInfos> delInfosesList;//下架删除的记录存入MjjgdaDelInfos表
    private DirectionalSearchAdapter mDirectionalSearchAdapter;
    private int size = 50;

    private Activity mContext;
    Message sMessage;
    //后台运行的handler
    private Handler mCheckMsgHandler;
    //与UI线程管理的handler
    private Handler mHandler;
    private boolean isOnScreen;//是否在屏幕上
    private boolean isRun;//是否在RFID读取
    private static final int MSG_UPDATE_INFO = 1;
    private static final int MSG_SUBMIT = 2;
    //传递后台运行消息队列
    Message msg;
    //传递UI前台显示消息队列
    Message mHandlerMessage;
    Bundle mBundle;
    //    private PlaySoundPool soundPool;
    Thread thread;
    private static UHFLongerManager manager;
    private KeyReceiver keyReceiver;
    private boolean runFlag = true;
    private boolean startFlag = false;
    private PopupWindow mPopupWindow;
    private SearchViewHolder holder;
    private Bundle saveBundele;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_directionalsearch);
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
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRangle = appBarLayout.getTotalScrollRange();
                //初始verticalOffset为0，不能参与计算。
                if (verticalOffset == 0) {
                    //                    mFab.show();
                } else {
                    //                    mFab.hide();
                }
            }
        });
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
        //        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        mDirectionalSearchAdapter = new DirectionalSearchAdapter(this, mDataList);
        mDirectionalSearchAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mDirectionalSearchAdapter);
        keyReceiver = new KeyReceiver(manager, false, mSwitch);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, intentFilter);
        mTimer = new Timer();
    }

    @Override
    protected void initEvents() {
        index = getIntent().getIntExtra("index", 0);
        setTitle(getIntent().getStringExtra("title"));
        //        mDataList = new ArrayList<>();
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
        //        delInfosesList = new ArrayList<>();
        mProgressBar.setVisibility(View.GONE);
        //        mFab.setOnClickListener(new OnSingleClickListener() {
        //            @Override
        //            protected void onSingleClick(View view) {
        //                final DirectionalSearchEditorHolder bookShelfHolder = new DirectionalSearchEditorHolder(mContext, "");
        ////                final int inputSpace = DensityUtils.dp2px(getActivity(), 16);
        //                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //                builder.setCancelable(false)
        //
        //                        .setTitle(UIUtils.getContext().getString(R.string.add_bookshelf))
        //                        .setNegativeButton(R.string.cancel, (dialog, which) -> {
        //                            dialog.dismiss();
        ////                            KeyBoardUtils.closeKeyBord(bookShelfHolder.et_bookshelf_name, getActivity());
        //                        })
        //                        .setPositiveButton(R.string.ok, (dialog, which) -> {
        ////                            if (!bookShelfHolder.check()) {
        ////                                Snackbar.make(BaseActivity.activity.getToolbar(), R.string.bookshelf_name_is_empty, Snackbar.LENGTH_SHORT).show();
        ////                            } else {
        ////                                mBookshelfPresenter.addBookshelf(bookShelfHolder.getName(), bookShelfHolder.getRemark(), TimeUtils.getCurrentTime());
        ////                            }
        ////                            KeyBoardUtils.closeKeyBord(bookShelfHolder.et_bookshelf_name, getActivity());
        //                        }).create().show();
        //
        //            }
        //        });
    }

    int t = 0;

    private void initUiHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UI_SUCCESS:
                        if (!ListUtils.isEmpty(mDataList)) {
                            t = msg.arg1;
                            com.botongsoft.rfid.common.utils.LogUtils.d("UI_SUCCESS", String.valueOf(t));
                            //                            ToastUtils.showToast("序号位置："+position,500);
                            smoothMoveToPosition(mSwipeMenuRecyclerView, t);
                            //                        mDirectionalSearchAdapter.notifyItemChanged(t);
                            mDirectionalSearchAdapter.notifyDataSetChanged();


                            Message msg1 = mHandler.obtainMessage();
                            msg1.what = UI_SUBMITSUCCESS;
                            msg1.arg2=t;
                                                    mHandler.sendMessage(msg1);
//                                                    mHandler.sendMessageAtTime(msg1, 20);
//                            mHandler.sendMessageDelayed(msg1, 3);
                            //                        timerConnect();
                            //                            mDataList.get(position).setColor(0);
                            //                            mDirectionalSearchAdapter.notifyItemChanged(position);
                        }
                        break;
                    case UI_SUBMITSUCCESS:
                        if (!ListUtils.isEmpty(mDataList)) {
                           int t2 = msg.arg2;
                            com.botongsoft.rfid.common.utils.LogUtils.d("UI_SUBMIT", String.valueOf(t2));
                            smoothMoveToPosition(mSwipeMenuRecyclerView, t2);
                            mDataList.get(t2).setColor(0);
                            //                        mDirectionalSearchAdapter.notifyItemChanged(t);
                            mDirectionalSearchAdapter.notifyDataSetChanged();
                        }

                        break;
                    case UI_SUBMITSENDFAILUREMSG:
                        SoundUtil.play(2, 0);
                        Toast.makeText(UIUtils.getContext(), "更新数据失败", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        super.handleMessage(msg);//这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                        break;
                }
            }
        };
    }

    /*
        * 定时重连，延时300ms
        */
    private void timerConnect() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //初始化模块信息发送
                Message msg = mHandler.obtainMessage();
                msg.what = UI_SUBMITSUCCESS;
                mHandler.sendMessage(msg);
            }
        }, 200);
    }

    private void searchDB(String editString) {
        boolean tempStr = true;
        //        int lx = Constant.getLx(editString);//根据传入的值返回对象类型
        Mjjgda mjjgda = null;
        if (mDataList.size() > 0) {
            for (int i = 0; i < mDataList.size(); i++) {
                mjjgda = mDataList.get(i);
                if (String.format("%016d", Integer.valueOf(mjjgda
                        .getEpccode())).equals(editString)) {
                    SoundUtil.play(1, 0);
                    mjjgda.setColor(1);
                    sMessage.what = UI_SUCCESS;
                    sMessage.arg1 = i;
                    LogUtils.d("put_su", String.valueOf(i));
                    int _position = i + 1;
                    ToastUtils.showToast("序号：" + _position, 500);
                }
            }


        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        isOnScreen = true;
        //        if(isOnScreen && isRun) {
        if (isOnScreen) {


        }
        if (manager != null) {
            manager.clearSelect();
        }
        thread = new ThreadMe();
        thread.start();
        mSwitch.setChecked(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止查询
        isOnScreen = false;

        startFlag = false;
        thread.interrupt();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    @Override
    protected void onDestroy() {

        //停止查询
        isOnScreen = false;
        startFlag = false;
        runFlag = false;


        unregisterReceiver(keyReceiver);
        //  finish();//finish结束后就无法获得返回值了
        super.onDestroy();
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

                Toast.makeText(DirectionalSearchActivity.this, "滑到最底部了，去加载更多吧！", Toast.LENGTH_SHORT).show();
                //                size += 50;
                //                for (int i = size - 50; i < size; i++) {
                //                    Map map = new HashMap();
                //                    map.put("id", i);
                //                    map.put("title", "我是第" + i + "个。");
                //                    mDataList.add(map);
                //                }
                //                mDownFloorAdapter.notifyDataSetChanged();
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

        }

        @Override
        public void onItemClick(int position, int listSize) {
            //            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
            if (position != -1) {
                //                delInfosesList.remove(position);
                mDataList.remove(position);
                mDirectionalSearchAdapter.notifyItemRemoved(position);
                mDirectionalSearchAdapter.notifyItemRangeChanged(position, listSize);
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
                //                delInfosesList.remove(adapterPosition);
                mDirectionalSearchAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };

    @Override
    public void onSuccess(BaseResponse baseResponse, int act) {

    }

    @Override
    public void onError(BusinessException e, int act) {

    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //String change01 = data.getStringExtra("change01");
        //        String change02 = data.getStringExtra("change02");
        // 根据上面发送过去的请求吗来区别
        if (requestCode == SEND_SEARCHRESULT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Send SEARCHRESULT RESULT_OK", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Bundle bundle = data.getExtras();
                List list = (List) bundle.getSerializable("list");
                mDataList.clear();
                mDataList.addAll(list);

            }
        } else if (requestCode == CALL_REQUEST) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Call RESULT_CANCELED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showFloatingBar() {
        //        if (mFab != null) {
        //            mFab.show();
        //        }
    }

    public void hideFloatingBar() {
        //        if (mFab != null) {
        //            mFab.hide();
        //        }
    }

    class ThreadMe extends Thread {
        private List<String> epcList;

        @Override
        public void run() {
            super.run();
            while (runFlag) {

                if (startFlag) {
                    if (manager != null) {
                        epcList = manager.inventoryRealTime(); //
                        if (epcList != null && !epcList.isEmpty()) {

                            //                              sMessage = mHandler.obtainMessage();
                            //                            sMessage.what = UI_SUCCESS;

                            for (String epc : epcList) {
                                sMessage = mHandler.obtainMessage();
                                searchDB(epc);
                                mHandler.sendMessage(sMessage);
                            }

                        }
                        epcList = null;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        runFlag = false;
                        startFlag = false;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ToastUtils.showLong("硬件链接失败");
                            }
                        });
                    }

                }
            }
        }

    }

    @Override
    protected int getMenuID() {
        return R.menu.menu_search;
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
                SettingDialogFragment dialogFragment = SettingDialogFragment.newInstance(R.layout.setting_power_dialog);
                dialogFragment.show(ft, "settingDialog");
                return true;
            case R.id.action_search:
                ToastUtils.showLong("search");
                showSearchView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSearchView() {

        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (mPopupWindow == null) {
            holder = new SearchViewHolder(this, code -> {
                switch (code) {
                    case SearchViewHolder.RESULT_SEARCH_EMPTY_KEYWORD:
                        Snackbar.make(mAppBarLayout, R.string.keyword_is_empty, Snackbar.LENGTH_SHORT).show();
                        break;
                    case SearchViewHolder.RESULT_SEARCH_SEARCH:
                        String searchString = holder.et_search_content.getText().toString();
                        if (searchString.startsWith("@")) {
                            CustomTabActivityHelper.openCustomTab(//用函数打开一个网址效果速度比webview更好
                                    this,
                                    new CustomTabsIntent.Builder()
                                            .setShowTitle(true)
                                            .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                                            .addDefaultShareMenuItem()
                                            .build(),
                                    Uri.parse(searchString.replace("@", "")));
                        } else {
                            Intent intent = new Intent(this, SearchResultActivity.class);
                            intent.putExtra("q", searchString);
                            startActivityForResult(intent, SEND_SEARCHRESULT_REQUEST);
                        }
                        break;

                    case SearchViewHolder.RESULT_SEARCH_CANCEL:
                        mPopupWindow.dismiss();
                        break;
                }
            });
            mPopupWindow = new PopupWindow(holder.getContentView(),
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            // 设置popWindow的显示和消失动画
            //                mPopupWindow.setAnimationStyle(R.style.PopupWindowStyle);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    holder.et_search_content.setText("");
                    KeyBoardUtils.closeKeyBord(holder.et_search_content, DirectionalSearchActivity.this);
                    ValueAnimator animator = ValueAnimator.ofFloat(0.7f, 1f);
                    animator.setDuration(500);
                    animator.addUpdateListener(animation -> {
                        lp.alpha = (float) animation.getAnimatedValue();
                        lp.dimAmount = (float) animation.getAnimatedValue();
                        getWindow().setAttributes(lp);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    });
                    animator.start();
                }
            });
            mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }
        KeyBoardUtils.openKeyBord(holder.et_search_content, DirectionalSearchActivity.this);
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.7f);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            lp.alpha = (float) animation.getAnimatedValue();
            lp.dimAmount = (float) animation.getAnimatedValue();
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });
        mPopupWindow.showAtLocation(mToolbar, Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(activity));
        animator.start();
    }

}
