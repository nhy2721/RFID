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

public class FilesBusines {

    /****
     * 提交本地版本号到服务器获取服务器更新数量接口
     *
     * @param context
     *
     * @return
     */
    public static RequestTask getWorkState(Context context,
                                           BusinessResolver.BusinessCallback<BaseResponse> callback, Long kf, Long mjj,
                                           Long mjg, Long mjgda, Long checkPlanAnchor, Long epcAnchor, int delDaLogId) {
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
        req.put("epcAnchor", epcAnchor);
        req.put("delDaLogId", delDaLogId);
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
    public static RequestTask putDa(Context context,
                                    BusinessResolver.BusinessCallback<BaseResponse> callback, int reqType, List mjgdaList, List mjgdaDelList) {
        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", reqType);
        req.put("mjgda", mjgdaList);
        if (mjgdaDelList != null) {
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
//        task.executeOnExecutor(Executors.newCachedThreadPool(), request);
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
                                       BusinessResolver.BusinessCallback<BaseResponse> callback, Long anchor, int logID, int reqType) {
        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", reqType);
        req.put("anchor", anchor);
        req.put("delDaLogId", logID);
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

    /****
     * 提交档案纠错数据接口
     *
     * @param context
     *
     * @param
     * @return
     */
    public static RequestTask putCheckPlan(Context context,
                                           BusinessResolver.BusinessCallback<BaseResponse> callback,
                                           int reqType, List checkPlanDetailList, List checkPlanErrorList, List checkPlanDetailDelList) {
        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", reqType);
        if (checkPlanDetailList != null) {
            req.put("checkPlanDetail", checkPlanDetailList);
        }
        if (checkPlanErrorList != null) {
            req.put("checkPlanErrorList", checkPlanErrorList);
        }
        if (checkPlanDetailDelList != null) {
            req.put("checkPlanDetailDelList", checkPlanDetailDelList);
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
        //        task.executeOnExecutor(Executors.newCachedThreadPool(), request);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        return task;


    }
    /****
     * 提交日志接口
     *
     * @param context
     *
     * @param
     * @return
     */
    public static RequestTask putLog(Context context,
                                           BusinessResolver.BusinessCallback<BaseResponse> callback,
                                           int reqType, List logMainList, List logDetailList) {
        final RequestTask task = new RequestTask(callback, context);
        BusinessRequest request = new BusinessRequest(
                BusinessRequest.REQUEST_TYPE_POST,
                BusinessRequest.RESULT_TYPE_OBJECT);
        request.proDialogMsgId = R.string.request_hint_register;
        JSONObject str_json = new JSONObject();
        JSONObject req = new JSONObject();
        req.put("reqType", reqType);
        if (logMainList != null) {
            req.put("logMainList", logMainList);
        }else{
            req.put("logMainList", "");
        }
        if (logDetailList != null) {
            req.put("logDetailList", logDetailList);
        }else{
            req.put("logDetailList", "");
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
        //        task.executeOnExecutor(Executors.newCachedThreadPool(), request);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        return task;


    }
}
