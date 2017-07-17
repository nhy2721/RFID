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
                                           BusinessResolver.BusinessCallback<BaseResponse> callback) {
        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", "9999");
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


}
