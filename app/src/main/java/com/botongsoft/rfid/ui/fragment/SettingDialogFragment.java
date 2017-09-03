package com.botongsoft.rfid.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.handheld.UHFLonger.UHFLongerManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pc on 2017/6/27.
 */

public class SettingDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final int MSG_SUBMIT = 0;
    @BindView(R.id.button_min)
    Button buttonMin;
    @BindView(R.id.button_plus)
    Button buttonPlus;
    @BindView(R.id.button_set)
    Button buttonSet;
    @BindView(R.id.editText_power)
    EditText editValues;
    @BindView(R.id.textView1)
    TextView textView1;
    private int value = 30;//
    private UHFLongerManager manager;
    protected View mRootView;


    //写一个静态方法产生实例
    public static SettingDialogFragment newInstance(int layoutId) {
        SettingDialogFragment fragment = new SettingDialogFragment();
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
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
            //            dialog.getWindow().setDimAmount(0);//去掉遮罩层
        }
        mRootView = inflater.inflate(getArguments().getInt("layoutId"), null);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
        initView();
    }

    private void initDate() {
        manager = BaseApplication.application.getmanager();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) { //此处可以设置Dialog的style等等
        super.onCreate(savedInstanceState);
        //        setCancelable(false);//无法直接点击外部取消dialog
        //        setStyle(DialogFragment.STYLE_NO_FRAME,0); //NO_FRAME就是dialog无边框，0指的是默认系统Theme


    }

    private void initView() {


        buttonMin.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonSet.setOnClickListener(this);
        value = getSharedValue();
        editValues.setText("" + value);
    }

    private int getSharedValue() {
        SharedPreferences sp = BaseApplication.application.getSharedPreferences("power", 0);
        int value = sp.getInt("value", 0);
        if (value == 0) {
            value = 30;
        }
        return  value;
    }

    @Override
    public void onClick(View v) {  //点击事件
        switch (v.getId()) {
            case R.id.button_min://
                if(value > 5){
                    value = value - 1;
                }else {
                    value = 30;
                }
                editValues.setText(value + "");
                break;
            case R.id.button_plus://
                if(value < 30){
                    value = value + 1;
                }else {
                    value = 5;
                }
                editValues.setText(value + "");
                break;
            case R.id.button_set://
                value = Integer.parseInt(editValues.getText().toString());
                if(manager.setOutPower((short) value)){
                    saveSharedValue(value);
                    ToastUtils.showShort("保存成功");
                    dismiss();
                }else{
                    ToastUtils.showShort("保存失败");
                }
                break;

            default:
                break;
        }
    }
    //save Value
    private void saveSharedValue(int value){
        SharedPreferences shared = BaseApplication.application.getSharedPreferences("power", 0);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("value", value);
        editor.commit();
    }

}
