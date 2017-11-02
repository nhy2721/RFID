package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;

/**
 * 用来记录服务器删除档案、EPC的删除记录ID
 * Created by pc on 2017/10/25.
 */

public class ServerLogRecord {
    @Id
    private int lid;//安卓端主键
    @Column
    private int type;//type表记录类型[1,2]  1:档案删除记录类型,2:epc删除记录类型
    @Column
    private int serverlogid;//服务器储存的日志主记录ID

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getServerlogid() {
        return serverlogid;
    }

    public void setServerlogid(int serverlogid) {
        this.serverlogid = serverlogid;
    }
}
