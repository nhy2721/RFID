package com.botongsoft.rfid.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.listener.OnSingleClickListener;
import com.botongsoft.rfid.ui.fragment.MyDialogFragment;

import java.util.List;

import static com.botongsoft.rfid.R.id.textView1;

/**
 * Created by pc on 2017/6/27.
 */

public class ScanCheckPlanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private final List<Mjj> mjjList;
    private Context mContext;
    private int columns;
    private  FragmentManager fm;
    private int pdid;

    public ScanCheckPlanListAdapter(Context context, List<Mjj> MjjList, int columns,FragmentManager fm,int pdid) {
        this.mjjList = MjjList;
        this.columns = columns;
        this.mContext = context;
        this.fm = fm;
        this.pdid= pdid;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkplanlist_list, parent, false);
            return new EBookListHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mjjList == null || mjjList.isEmpty()) {
            return TYPE_EMPTY;
        } else {
            return TYPE_DEFAULT;
        }
    }

    public int getItemColumnSpan(int position) {
        switch (getItemViewType(position)) {
            case TYPE_DEFAULT:
                return 1;
            default:
                return columns;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EBookListHolder) {
            final Mjj mjj = mjjList.get(position);

            ((EBookListHolder) holder).tv_book_title.setText(mjj.getMc());
            if (mjj.getNoleft() == 0) {
                ((EBookListHolder) holder).tView1.setText("左");
            }else{
                ((EBookListHolder) holder).mRelativeLayout1.setVisibility(View.INVISIBLE);
            }
            if (mjj.getNoright() == 0) {
                ((EBookListHolder) holder).tView2.setText("右");
            }else{
                ((EBookListHolder) holder).mRelativeLayout2.setVisibility(View.INVISIBLE);
            }
            if(!mjj.isShowLeft()){
                ((EBookListHolder) holder).mRelativeLayout1.setVisibility(View.INVISIBLE);
            }
            if(!mjj.isShowRrigh()){
                ((EBookListHolder) holder).mRelativeLayout2.setVisibility(View.INVISIBLE);
            }
            ((EBookListHolder) holder).tView1.setOnClickListener(new OnSingleClickListener(200) {
                @Override
                protected void onSingleClick(View view) {

                    ToastUtils.showShort("textView1");
                    MyDialogFragment mdf =   MyDialogFragment.newInstance(R.layout.checkplan_mjjglist_fragment, mjj,"left",pdid);
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    mdf.show(ft,"dd");
                }
            });
            ((EBookListHolder) holder).tView2.setOnClickListener(new OnSingleClickListener(200) {
                @Override
                protected void onSingleClick(View view) {
                    ToastUtils.showShort("textView2");
                    MyDialogFragment mdf =   MyDialogFragment.newInstance(R.layout.checkplan_mjjglist_fragment,mjj,"right",pdid);
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    mdf.show(ft,"dd");
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //                    Bundle b = new Bundle();
                    //                    b.putParcelable("BookDetail", bookInfo);
                    //                    b.putString("bookId", bookInfo.getId());
                    //                    Bitmap bitmap;
                    //                    GlideBitmapDrawable imageDrawable = (GlideBitmapDrawable) ((EBookListHolder) holder).iv_book_img.getDrawable();
                    //                    if (imageDrawable != null) {
                    //                        bitmap = imageDrawable.getBitmap();
                    //                        b.putParcelable("book_img", bitmap);
                    //                    }
                    //                    Intent intent = new Intent(mContext, EBookDetailActivity.class);
                    //                    intent.putExtras(b);
                    //                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //                        if (BaseActivity.activity == null) {
                    //                            UIUtils.startActivity(intent);
                    //                            return;
                    //                        }
                    //                        ActivityOptionsCompat options = ActivityOptionsCompat.
                    //                                makeSceneTransitionAnimation(BaseActivity.activity, ((EBookListHolder) holder).iv_book_img, "book_img");
                    //                        BaseActivity.activity.startActivity(intent, options.toBundle());
                    //                    } else {
                    //                        UIUtils.startActivity(intent);
                    //                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mjjList.isEmpty()) {
            return 1;
        }
        return mjjList.size();
    }

    class EBookListHolder extends RecyclerView.ViewHolder {


        private final TextView tv_book_title;
        private final TextView tView1;
        private final TextView tView2;
        private final RelativeLayout mRelativeLayout1;
        private final RelativeLayout mRelativeLayout2;


        public EBookListHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.tv_book_title);
            tView1 = (TextView) itemView.findViewById(textView1);
            tView2 = (TextView) itemView.findViewById(R.id.textView2);
            mRelativeLayout1 = (RelativeLayout) itemView.findViewById(R.id.RelativeLayout1);
            mRelativeLayout2 = (RelativeLayout) itemView.findViewById(R.id.RelativeLayout2);

        }


    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}

