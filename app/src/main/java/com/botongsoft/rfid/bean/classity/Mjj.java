package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

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
    private  int zs;
    @Column
    private  int noleft;
    @Column
    private  int noright;
    @Column
    private  String zlbq;
    @Column
    private String ylbq;
    @Column
    private String bz;

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
