package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * Created by pc on 2017/6/14.
 */
@Table
public class Mjj implements java.io.Serializable {

    @Id
    private int lid;//安卓端主键
    @Column
    private int id;
    @Column
    private int kfid;
    @Column
    private String mc;
    @Column
    private int cs;
    @Column
    private int zs;
    @Column
    private int noleft;
    @Column
    private int noright;
    @Column
    private String zlbq;
    @Column
    private String ylbq;
    @Column
    private String bz;
    @Transient
    private int leftState;//是否扫描过
    @Transient
    private int rightState;//是否扫描过
    @Transient
    private boolean showLeft;//显示左面
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

    public boolean isShowLeft() {
        return showLeft;
    }

    public void setShowLeft(boolean showLeft) {
        this.showLeft = showLeft;
    }

    public boolean isShowRrigh() {
        return showRrigh;
    }

    public void setShowRrigh(boolean showRrigh) {
        this.showRrigh = showRrigh;
    }

    @Transient
    private boolean showRrigh;//显示右面

    public Mjj() {
    }

    public Mjj(int lid, int id, int kfid, String mc, int cs, int zs, int noleft, int noright, String zlbq, String ylbq, String bz) {
        this.lid = lid;
        this.id = id;
        this.kfid = kfid;
        this.mc = mc;
        this.cs = cs;
        this.zs = zs;
        this.noleft = noleft;
        this.noright = noright;
        this.zlbq = zlbq;
        this.ylbq = ylbq;
        this.bz = bz;

    }


    public int getLeftState() {
        return leftState;
    }

    public void setLeftState(int leftState) {
        this.leftState = leftState;
    }

    public int getRightState() {
        return rightState;
    }

    public void setRightState(int rightState) {
        this.rightState = rightState;
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

    public int getKfid() {
        return kfid;
    }

    public void setKfid(int kfid) {
        this.kfid = kfid;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
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

    public int getNoleft() {
        return noleft;
    }

    public void setNoleft(int noleft) {
        this.noleft = noleft;
    }

    public int getNoright() {
        return noright;
    }

    public void setNoright(int noright) {
        this.noright = noright;
    }

    public String getZlbq() {
        return zlbq;
    }

    public void setZlbq(String zlbq) {
        this.zlbq = zlbq;
    }

    public String getYlbq() {
        return ylbq;
    }

    public void setYlbq(String ylbq) {
        this.ylbq = ylbq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
