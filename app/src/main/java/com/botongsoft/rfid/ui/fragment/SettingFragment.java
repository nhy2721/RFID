package com.botongsoft.rfid.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.common.utils.StringFormatUtil;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by pc on 2017/9/5.
 */

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    EditTextPreference de;

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

        de = (EditTextPreference) getPreferenceManager().findPreference(getString(R.string.ipaddress));

        //        de.setSummary("192.168.0.66");
        de.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String value = newValue.toString();
               if(!StringFormatUtil.ipCheck(value)){
                   ToastUtils.showLong("ip 地址不符合规范");
                   return  false;
               }else {
                   de.setSummary(value);
                   SharedPreferences shared = BaseApplication.application.getSharedPreferences("ipaddress", 0);
                   SharedPreferences.Editor editor = shared.edit();
                   editor.putString("value", value);
                   editor.commit();
                   LogUtils.d("onPreferenceChange");
                   return true;
               }

            }
        });
        de.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                LogUtils.d("onPreferenceClick");
                String  sss = (String) preference.getSummary();
//                SharedPreferences sp = BaseApplication.application.getSharedPreferences("ipaddress", 0);
//                String value = sp.getString("value", "");
//                if ("".equals(value)) {
//                    value = "";
//                }
//
                de.setText(sss);
                return true;
            }
        });
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
        de.setSummary(value);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        LogUtils.d(s + "");

    }
}