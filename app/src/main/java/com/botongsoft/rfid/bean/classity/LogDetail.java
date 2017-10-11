package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 日志明细表
 * Created by pc on 2017/6/14.
 */
@Table
public class LogDetail implements java.io.Serializable {

    @Id
    private int lid;//安卓端主键
    @Column
    private int llogid;//安卓端日志表主表ID
    @Column
    private int id;//日志明细表自己的id
    @Column
    private int logid;//日志对应的服务器主ID

    @Column
    private String bm;//日志对应的服务器主ID
    @Column
    private int jlid;

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getLlogid() {
        return llogid;
    }

    public void setLlogid(int llogid) {
        this.llogid = llogid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogid() {
        return logid;
    }

    public void setLogid(int logid) {
        this.logid = logid;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public int getJlid() {
        return jlid;
    }

    public void setJlid(int jlid) {
        this.jlid = jlid;
    }

    public String getOldcfwz() {
        return oldcfwz;
    }

    public void setOldcfwz(String oldcfwz) {
        this.oldcfwz = oldcfwz;
    }

    public String getNewcfwz() {
        return newcfwz;
    }

    public void setNewcfwz(String newcfwz) {
        this.newcfwz = newcfwz;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getAnchor() {
        return anchor;
    }

    public void setAnchor(long anchor) {
        this.anchor = anchor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column

    private String oldcfwz;//旧存放位置
    @Column
    private String newcfwz;//新存放位置
    @Column
    private  int  type;//类型

    @Column
    private long anchor;//版本号
    @Column
    private int status;//数据状态 为0代表未上传，为9 代表已上传



}
