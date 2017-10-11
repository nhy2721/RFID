package com.botongsoft.rfid.ui.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.classity.CheckError;
import com.botongsoft.rfid.bean.classity.CheckPlan;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatil;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatilDel;
import com.botongsoft.rfid.bean.classity.Epc;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.LogDetail;
import com.botongsoft.rfid.bean.classity.LogMain;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.classity.MjjgdaDelInfos;
import com.botongsoft.rfid.common.db.DataBaseCreator;
import com.botongsoft.rfid.common.utils.StringFormatUtil;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.botongsoft.rfid.ui.activity.BaseActivity;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by pc on 2017/9/5.
 */

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    EditTextPreference editTextIpAddress;
    Preference initDbPreference;
    private AlertDialog.Builder builder;
    private Class[] tables = {CheckError.class, CheckPlan.class, CheckPlanDeatil.class,
            Kf.class, Mjj.class, Mjjg.class, Mjjgda.class, MjjgdaDelInfos.class, CheckPlanDeatilDel.class,
            Epc.class, LogMain.class, LogDetail.class};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //        SwitchCompat ss = (SwitchCompat) getActivity().findViewById(R.id.switch_compat);
        //        ss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        //            @Override
        //            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //                Logger.d("SwitchCompat " + buttonView + " changed to " + isChecked);
        //            }
        //        });

        //        final CheckBoxPreference checkboxPref = (CheckBoxPreference) getPreferenceManager()
        //                .findPreference(getString(R.string.save_net_mode));
        //
        //        checkboxPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
        //
        //            /**
        //             * @param preference The changed Preference.
        //             * @param newValue   The new value of the Preference.
        //             * @return True to update the state of the Preference with the new value.
        //             */
        //            @Override
        //            public boolean onPreferenceChange(Preference preference, Object newValue) {
        //
        //                boolean checked = Boolean.valueOf(newValue.toString());
        //                PrefUtils.setSaveNetMode(checked);
        //                return true;
        //
        //            }
        //        });

        editTextIpAddress = (EditTextPreference) getPreferenceManager().findPreference(getString(R.string.ipaddress));
        if (null != editTextIpAddress) {
            editTextIpAddress.setOnPreferenceClickListener(this);
            editTextIpAddress.setOnPreferenceChangeListener(this);
        }
        initDbPreference = getPreferenceManager().findPreference(getString(R.string.initdb));
        if (null != initDbPreference) {
            initDbPreference.setOnPreferenceClickListener(this);
        }
        //        de.setSummary("192.168.0.66");
        //        de.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
        //            @Override
        //            public boolean onPreferenceChange(Preference preference, Object newValue) {
        //                String value = newValue.toString();
        //               if(!StringFormatUtil.ipCheck(value)){
        //                   ToastUtils.showLong("ip 地址不符合规范");
        //                   return  false;
        //               }else {
        //                   de.setSummary(value);
        //                   SharedPreferences shared = BaseApplication.application.getSharedPreferences("ipaddress", 0);
        //                   SharedPreferences.Editor editor = shared.edit();
        //                   editor.putString("value", value);
        //                   editor.commit();
        //                   LogUtils.d("onPreferenceChange");
        //                   return true;
        //               }
        //
        //            }
        //        });
        //        de.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
        //            @Override
        //            public boolean onPreferenceClick(Preference preference) {
        //                LogUtils.d("onPreferenceClick");
        //                String  sss = (String) preference.getSummary();
        ////                SharedPreferences sp = BaseApplication.application.getSharedPreferences("ipaddress", 0);
        ////                String value = sp.getString("value", "");
        ////                if ("".equals(value)) {
        ////                    value = "";
        ////                }
        ////
        //                de.setText(sss);
        //                return true;
        //            }
        //        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        SharedPreferences sp = BaseApplication.application.getSharedPreferences("ipaddress", 0);
        String value = sp.getString("value", "");
        if ("".equals(value)) {
            value = "请先配置好服务器地址";
        }
        editTextIpAddress.setSummary(value);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        LogUtils.d(s + " onSharedPreferenceChanged");
        ToastUtils.showToast(s + " onSharedPreferenceChanged", 500);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.ipaddress))) {
            String value = newValue.toString();
            if (!StringFormatUtil.ipCheck(value)) {
                ToastUtils.showLong("ip 地址不符合规范");
                return false;
            } else {
                editTextIpAddress.setSummary(value);
                SharedPreferences shared = BaseApplication.application.getSharedPreferences("ipaddress", 0);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("value", value);
                editor.commit();
                return true;
            }

        } else if (key.equals(getString(R.string.initdb))) {
            ToastUtils.showLong(getString(R.string.initdb));
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.ipaddress))) {
            String sss = (String) preference.getSummary();
            editTextIpAddress.setText(sss);
            return true;

        } else if (key.equals(getString(R.string.initdb))) {
            builder = new AlertDialog.Builder(BaseActivity.activity);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle(R.string.initdb_title_dialog);
            builder.setMessage(R.string.dialog_initdb_message);

            //监听下方button点击事件
            builder.setPositiveButton(R.string.postive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new Thread() {
                        @Override
                        public void run() {
                            DbUtils db = DataBaseCreator.create();
                            for (Class table : tables) {
                                try {
                                    db.deleteAll(table);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }.start();
                }
            });
            builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            //设置对话框是可取消的
            builder.setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        return false;
    }
}