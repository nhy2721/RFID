package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

import java.util.Comparator;

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
    private long anchor;
    @Column
    private String scanInfo;//页面扫描的档案记录条码
    @Transient
    private int color = 0;//转换颜色图片
    @Transient
    private String title;//页面拼接的扫描标签

    public String getEpccode() {
        return epccode;
    }

    public void setEpccode(String epccode) {
        this.epccode = epccode;
    }

    @Transient

    private String epccode;//页面拼接的扫描标签

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public static Comparator<Mjjgda> nameComparator = new Comparator<Mjjgda>() {

        @Override
        public int compare(Mjjgda jc1, Mjjgda jc2) {
            return (jc1.getTitle().compareTo(jc2.getTitle()));
        }
    };
}
