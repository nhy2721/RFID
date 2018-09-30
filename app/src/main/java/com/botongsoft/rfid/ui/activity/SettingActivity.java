package com.botongsoft.rfid.ui.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.service.http.BusinessException;
import com.botongsoft.rfid.ui.fragment.SettingFragment;
import com.handheld.UHFLonger.UHFLongerManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.botongsoft.rfid.R.id.toolbar;


/**UHFL手持机功率管理
 * @author Administrator
 */
public class SettingActivity extends BaseActivity implements OnClickListener {

    @BindView(toolbar)
    Toolbar mToolbar;
    private int value = 30;//
    private UHFLongerManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.setting_power);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        initView();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingFragment()).commit();
        manager = BaseApplication.application.getmanager();
    }

    @Override
    protected void initEvents() {

    }

    private void initView() {


    }

    //get Value
    private int getSharedValue() {
        SharedPreferences shared = getSharedPreferences("power", 0);
        return shared.getInt("value", 30);
    }

    //save Value
    private void saveSharedValue(int value) {
        SharedPreferences shared = getSharedPreferences("power", 0);
        Editor editor = shared.edit();
        editor.putInt("value", value);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            default:
                break;
        }

    }


    @Override
    public void onSuccess(BaseResponse baseResponse, int act) {

    }

    @Override
    public void onError(BusinessException e, int act) {

    }
}
