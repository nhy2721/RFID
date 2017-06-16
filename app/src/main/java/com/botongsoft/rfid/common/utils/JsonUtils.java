package com.botongsoft.rfid.common.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.botongsoft.rfid.common.service.http.BusinessRequest;
import com.botongsoft.rfid.common.service.http.NetUtils;

public class JsonUtils {
	/**
	 * 得到请求报文
	 * 
	 * @return
	 */
	public static String paramsToJson(BusinessRequest mRequest) {
		String strParams = null;
		if (mRequest.requestType == BusinessRequest.REQUEST_TYPE_GET
				&& mRequest.params != null) {
			strParams = NetUtils.mapToParams(mRequest.params);
		} else if (mRequest.params != null) {
			strParams = JSON.toJSONString(mRequest.params);
		} else if (mRequest.paramsObject != null) {
			// strParams = JSON.toJSONString(mRequest.paramsObject);
			strParams = JSON.toJSONString(mRequest.paramsObject,
					SerializerFeature.SortField);
		} else {
			strParams = mRequest.paramsJSON;
		}

		return strParams;
	}

}
