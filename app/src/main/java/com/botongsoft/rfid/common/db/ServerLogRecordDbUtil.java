package com.botongsoft.rfid.common.db;

import com.botongsoft.rfid.bean.classity.ServerLogRecord;

/**
 * Created by pc on 2017/11/2.
 */

public class ServerLogRecordDbUtil {
    /**
     * 根据类型获取服务器删除记录的id
     *
     * @param typeId
     * @return
     */
    public static ServerLogRecord getServerLogRecordType(int typeId) {
        return (ServerLogRecord) DBDataUtils.getInfo(ServerLogRecord.class, "type", String.valueOf(typeId));
    }
}
