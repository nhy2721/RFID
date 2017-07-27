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

import java.util.List;
import java.util.concurrent.Executors;

public class FilesBusines {

    /****
     * 提交本地版本号到服务器获取服务器更新数量接口
     *
     * @param context
     *
     * @return
     */
    public static RequestTask getWorkState(Context context,
                                           BusinessResolver.BusinessCallback<BaseResponse> callback, Long kf, Long mjj, Long mjg, Long mjgda,Long checkPlanAnchor) {
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
        req.put("checkPlanAnchor", checkPlanAnchor);
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
     * @param
     * @return
     */
    public static RequestTask putDa(RequestTask task, Context context,
                                    BusinessResolver.BusinessCallback<BaseResponse> callback, int reqType, List mjgdaList, List mjgdaDelList) {
        //        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", reqType);
        req.put("mjgda", mjgdaList);
        if(mjgdaDelList!=null){
            req.put("mjgdadel", mjgdaDelList);
        }
        JSONArray str_jsons = new JSONArray();
        str_jsons.add(req);
        str_json.put("req", str_jsons);
        request.paramsJSON = str_json.toJSONString();
        request.cls = BaseResponse.class;
        request.RESULT_ACT = reqType;
        //        task.showDialog(request);
        //                task.execute(request);
        //        task.executeOnExecutor(Executors.newFixedThreadPool(20), request);//背压问题
        task.executeOnExecutor(Executors.newCachedThreadPool(), request);
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
                                       BusinessResolver.BusinessCallback<BaseResponse> callback, Long anchor, int reqType) {
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
