package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by pc on 2017/6/14.
 */
@Table
public class Mjjg implements java.io.Serializable {

    @Id
    private int lid;//安卓端主键
    @Column
    private int id;
    @Column
    private String mc;
    @Column
    private int mjjid;
    @Column
    private int zy;
    @Column
    private int cs;
    @Column
    private int zs;
    @Column
    private int status;
    @Column
    private long anchor;

    public int getLid() {
        return lid;
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

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public int getMjjid() {
        return mjjid;
    }

    public void setMjjid(int mjjid) {
        this.mjjid = mjjid;
    }

    public int getZy() {
        return zy;
    }

    public void setZy(int zy) {
        this.zy = zy;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public int getZs() {
        return zs;
    }

    public void setZs(int zs) {
        this.zs = zs;
    }

    public int getCfsl() {
        return cfsl;
    }

    public void setCfsl(int cfsl) {
        this.cfsl = cfsl;
    }

    @Column
    private int cfsl;
}
