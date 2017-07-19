package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * Created by pc on 2017/6/14.
 */
@Table
public class Mjjgda implements java.io.Serializable {

    @Id
    private int lid;//安卓端主键
    @Column
    private int id;
    @Column
    private int mjgid;
    @Column
    private String bm;
    @Column
    private String jlid;
    @Column
    private int xh;
    @Column
    private int flag;
    @Column
    private int mjjid;
    @Column
    private int kfid;
    @Column
    private int status;
    @Column
    private int anchor;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAnchor() {
        return anchor;
    }

    public void setAnchor(int anchor) {
        this.anchor = anchor;
    }

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

    public int getMjgid() {
        return mjgid;
    }

    public void setMjgid(int mjgid) {
        this.mjgid = mjgid;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getJlid() {
        return jlid;
    }

    public void setJlid(String jlid) {
        this.jlid = jlid;
    }

    public int getXh() {
        return xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getMjjid() {
        return mjjid;
    }

    public void setMjjid(int mjjid) {
        this.mjjid = mjjid;
    }

    public int getKfid() {
        return kfid;
    }

    public void setKfid(int kfid) {
        this.kfid = kfid;
    }

    public String getScanInfo() {
        return scanInfo;
    }

    public void setScanInfo(String scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Column
    private String scanInfo;//页面扫描的档案记录条码
    @Transient
    private int color = 0;//转换颜色图片

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
