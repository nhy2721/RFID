package com.botongsoft.rfid.ui.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.handheld.UHFLonger.UHFLongerManager;


/**
 * 
 * @author Administrator
 *
 */
public class SettingPower extends Activity implements OnClickListener {

	private Button buttonMin;
	private Button buttonPlus;
	private Button buttonSet;
	private EditText editValues ;
	private int value = 30 ;//
	private UHFLongerManager manager ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.setting_power);
		super.onCreate(savedInstanceState);
		initView();

		manager = BaseApplication.application.getmanager();
	}
	
	private void initView(){
		buttonMin = (Button) findViewById(R.id.button_min);
		buttonPlus = (Button) findViewById(R.id.button_plus);
		buttonSet = (Button) findViewById(R.id.button_set);
		editValues = (EditText) findViewById(R.id.editText_power);
		
		buttonMin.setOnClickListener(this);
		buttonPlus.setOnClickListener(this);
		buttonSet.setOnClickListener(this);
		value =  getSharedValue();
		editValues.setText("" +value);
		
	}
	
	//get Value
	private int getSharedValue(){
		SharedPreferences shared = getSharedPreferences("power", 0);
		return shared.getInt("value", 30);
	}

	//save Value
	private void saveSharedValue(int value){
		SharedPreferences shared = getSharedPreferences("power", 0);
		Editor editor = shared.edit();
		editor.putInt("value", value);
		editor.commit();
	}
	@Override
	public void onClick(View v) {
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
                finish();
			}else{
				ToastUtils.showShort("保存失败");
			}
			break;

		default:
			break;
		}
		
	}
	
	
}
