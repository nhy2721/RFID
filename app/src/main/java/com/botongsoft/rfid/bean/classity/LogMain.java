package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 日志主表
 * Created by pc on 2017/6/14.
 */
@Table
public class LogMain implements java.io.Serializable {

    @Id
    private int lid;//安卓端主键
    @Column
    private int id;
    @Column
    private String pch;
    @Column
    private int usid;
    @Column
    private int lx;
    @Column
    private String qzh;
    @Column
    private int sl;
    @Column
    private String sj;
    @Column
    private String newstart;
    @Column
    private String newend;
    @Column
    private String pdid;
    @Column
    private long anchor;//版本号
    @Column
    private int status;//数据状态 为0代表未上传，为9 代表已上传

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch;
    }

    public int getUsid() {
        return usid;
    }

    public void setUsid(int usid) {
        this.usid = usid;
    }

    public int getLx() {
        return lx;
    }

    public void setLx(int lx) {
        this.lx = lx;
    }

    public String getQzh() {
        return qzh;
    }

    public void setQzh(String qzh) {
        this.qzh = qzh;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getNewstart() {
        return newstart;
    }

    public void setNewstart(String newstart) {
        this.newstart = newstart;
    }

    public String getNewend() {
        return newend;
    }

    public void setNewend(String newend) {
        this.newend = newend;
    }

    public String getPdid() {
        return pdid;
    }

    public void setPdid(String pdid) {
        this.pdid = pdid;
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


}
