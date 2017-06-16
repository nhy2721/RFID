package com.botongsoft.rfid.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botongsoft.rfid.R;


public class DialogLoad {
	private static Dialog progressDialog;

	public static int isShowProgressDialog = 0;

	public static Dialog getProgress(final Context context, String mMessage) {
		progressDialog = new Dialog(context, R.style.loading_dialog);
		if (mMessage == null)
			mMessage = "正在加载中...";
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(true);
		progressDialog.setContentView(initLayout(context, mMessage),
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT));
		progressDialog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				isShowProgressDialog = 0;

				closeProgeress();
			}
		});
		return progressDialog;
	}

	/**
	 * 自定义dialog
	 * 
	 * @param context
	 * @param msg
	 */
	private static View initLayout(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息
		return layout;
	}

	/**
	 * 显示进度条对话框
	 */
	public static void showProgeress(Context context, String message) {
		try {
			if (progressDialog == null) {
				progressDialog = getProgress(context, message);
			}

			progressDialog.show();
			++isShowProgressDialog;
		} catch (Exception e) {
		}
	}

	public static void showProgeress(Context context) {
		try {
			if (progressDialog == null) {
				getProgress(context, null);
			}
			progressDialog.show();
			++isShowProgressDialog;
		} catch (Exception e) {
		}
	}

	/**
	 * 关闭进度条对话框
	 */
	public static void closeProgeress() {
		if (--isShowProgressDialog <= 0) {
			if (progressDialog != null) {
				try {
					progressDialog.dismiss();
				} catch (Exception e) {
				}
				progressDialog = null;
			}
			isShowProgressDialog = 0;
		}
	}

	public static void close() {
		progressDialog = null;
		isShowProgressDialog = 0;
	}
}
