package com.botongsoft.rfid.common.service.http;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.botongsoft.rfid.common.constants.Constant;
import com.botongsoft.rfid.common.utils.JsonUtils;
import com.botongsoft.rfid.common.utils.LogUtils;
import com.botongsoft.rfid.common.utils.RSA;
import com.botongsoft.rfid.common.DialogLoad;
import com.botongsoft.rfid.common.ShareManager;


@SuppressWarnings("rawtypes")
public class RequestTask extends
		AsyncTask<BusinessRequest, Void, BusinessResult> {

	private static final String TAG = "RequestTask";

	private BusinessResolver.BusinessCallback mCallback;

	private BusinessRequest request;

	/** 进度条 */
	// private ProDialog mProDialog;

	private Context mContext;

	public RequestTask(BusinessResolver.BusinessCallback callback, Context context) {
		mCallback = callback;
		this.mContext = context;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BusinessResult doInBackground(BusinessRequest... params) {
		request = params[0];
		BusinessResult result = new BusinessResult();
		String strParams = JsonUtils.paramsToJson(request);
		String resultJson = null;

		try {
			switch (request.requestType) {
			case BusinessRequest.REQUEST_TYPE_GET:
				if (request.params != null) {
					resultJson = BusinessResolver.getData(request.url,
							request.params);
				} else {
					resultJson = BusinessResolver.getData(request.url);
				}

				break;
			case BusinessRequest.REQUEST_TYPE_POST:
				resultJson = NetUtils.doPost(request.url, strParams);
				break;
			case BusinessRequest.REQUEST_TYPE_POST_ASE:
				/****** 对请求体加密 *****/
				LogUtils.d("huhui", "strParams---" + strParams);
				String business_no = ShareManager.getValue(mContext,
						Constant.USER_CODE);
				String str_key = ShareManager.getValue(mContext,
						Constant.RSA_KEY);
				if (request.url.equals(Constant.DOMAIN)) {
					strParams = "f="
							+ RSA.rsaEncode(strParams, business_no, str_key);
				} else {
					strParams = "s="
							+ RSA.rsaEncode(strParams, business_no, str_key);
				}

				resultJson = NetUtils.doPost(request.url, strParams);
				break;
			}

			LogUtils.d(TAG, resultJson);

			if (!TextUtils.isEmpty(resultJson)) {

				switch (request.resultType) {
				case BusinessRequest.RESULT_TYPE_OBJECT:
					// 返回对象
					if (request.cls == null) {
						LogUtils.e(TAG, "request.cls can't be null");
						result.success = false;
						break;
					}
					result.returnObject = JSON.parseObject(resultJson,
							request.cls);
					result.success = true;
					break;
				case BusinessRequest.RESULT_TYPE_VOID:
					result.returnObject = null;
					result.success = true;
					break;
				case BusinessRequest.RESULT_TYPE_CUSTOM:
					// 自定义接口
					if (request.cls == null) {
						LogUtils.e(TAG, "request.parser can't be null");
						result.success = false;
						break;
					}
					result.success = true;
					break;
				}
			} else {
				// 返回失败
				result.success = false;
			}

		} catch (BusinessException e) {
			result.exception = e;
			result.returnObject = null;
			result.success = false;
		} catch (Exception e) {
			e.printStackTrace();
			result.exception = new BusinessException(
					BusinessException.CODE_ILLEGAL_RETURN);
			result.returnObject = null;
			result.success = false;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(BusinessResult result) {
		if (mCallback != null && !isCancelled()) {
			if (result.success) {
				mCallback.onSuccess(result.returnObject, request.RESULT_ACT);
			} else {
				if (result.exception != null) {
					mCallback.onError(result.exception, request.RESULT_ACT);
				} else {
					result.exception = new BusinessException(
							BusinessException.CODE_NO_DATA);
					mCallback.onError(result.exception, request.RESULT_ACT);
				}
			}
		}
		DialogLoad.closeProgeress();
	}

	public void showDialog(BusinessRequest request) {
		if (request.isShowProDialog) {
			// mProDialog.setCancelable(request.isCancelProDialog);
			// mProDialog.setOnCancelListener(this);
			DialogLoad.showProgeress(mContext, mContext.getResources()
					.getString(request.proDialogMsgId));

		}
	}

}
