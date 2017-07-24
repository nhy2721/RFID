package com.botongsoft.rfid.busines;

import android.content.Context;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.botongsoft.rfid.R;
import com.botongsoft.rfid.bean.http.BaseResponse;
import com.botongsoft.rfid.common.constants.Constant;
import com.botongsoft.rfid.common.service.http.BusinessRequest;
import com.botongsoft.rfid.common.service.http.BusinessResolver;
import com.botongsoft.rfid.common.service.http.RequestTask;

public class FilesBusines {

    /****
     * 获取工作动态接口
     *
     * @param context
     *
     * @return
     */
    public static RequestTask getWorkState(Context context,
                                           BusinessResolver.BusinessCallback<BaseResponse> callback, int kf, int mjj, int mjg,int mjgda) {
        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", "9999");
        req.put("kfanchor", kf);
        req.put("mjjanchor", mjj);
        req.put("mjganchor", mjg);
        req.put("mjgdaanchor", mjgda);
        JSONArray str_jsons = new JSONArray();
        str_jsons.add(req);
        str_json.put("req", str_jsons);
        request.paramsJSON = str_json.toJSONString();
        request.cls = BaseResponse.class;
        request.RESULT_ACT = Constant.ACT_GET_WORDS;
        task.showDialog(request);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        return task;
    }

    /****
     * 提交库房数据接口
     *
     * @param context
     *
     * @param kf
     * @return
     */
    public static RequestTask putKfState(Context context,
                                         BusinessResolver.BusinessCallback<BaseResponse> callback, Object kf) {
        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", "1002");
        req.put("kf", kf);
        JSONArray str_jsons = new JSONArray();
        str_jsons.add(req);
        str_json.put("req", str_jsons);
        request.paramsJSON = str_json.toJSONString();
        request.cls = BaseResponse.class;
        request.RESULT_ACT = Constant.ACT_PUT_KF;
        task.showDialog(request);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        return task;
    }

    /****
     * 提交库房密集架数据接口
     *
     * @param context
     *
     * @param
     * @return
     */
    public static RequestTask getState(Context context,
                                       BusinessResolver.BusinessCallback<BaseResponse> callback, int anchor, int reqType) {
        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", reqType);
        req.put("anchor", anchor);
        JSONArray str_jsons = new JSONArray();
        str_jsons.add(req);
        str_json.put("req", str_jsons);
        request.paramsJSON = str_json.toJSONString();
        request.cls = BaseResponse.class;
        request.RESULT_ACT = reqType;
        task.showDialog(request);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        return task;
    }

}
