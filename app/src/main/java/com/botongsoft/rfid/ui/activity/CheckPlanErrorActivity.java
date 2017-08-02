package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.service.http.BusinessException;

import butterknife.ButterKnife;

/**
 * Created by pc on 2017/8/2.
 */

public class CheckPlanErrorActivity extends BaseActivity {
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_checkplan);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;


    }

    @Override
    protected void initEvents() {

    }

    @Override
    public void onSuccess(BaseResponse baseResponse, int act) {

    }

    @Override
    public void onError(BusinessException e, int act) {

    }
}
