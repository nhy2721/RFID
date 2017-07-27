package com.botongsoft.rfid.bean.classity;

import com.alibaba.fastjson.annotation.JSONField;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by pc on 2017/6/14.
 */
@Table
public class CheckPlan implements java.io.Serializable {

    @Id
    private int lid;//安卓端主键
    @Column
    private int id;
    @Column
    private int pdid;
    @Column
    @JSONField(format="yyyy-MM-dd")
    private String kssj;
    @Column
    @JSONField (format="yyyy-MM-dd")
    private String jssj;
    @Column
    private String fw;
    @Column
    private String pdr;
    @Column
    private String bz;
    @Column
    private int status;
    @Column
    private long anchor;

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

    public int getPdid() {
        return pdid;
    }

    public void setPdid(int pdid) {
        this.pdid = pdid;
    }


    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getFw() {
        return fw;
    }

    public void setFw(String fw) {
        this.fw = fw;
    }

    public String getPdr() {
        return pdr;
    }

    public void setPdr(String pdr) {
        this.pdr = pdr;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getAnchor() {
        return anchor;
    }

    public void setAnchor(long anchor) {
        this.anchor = anchor;
    }
}
