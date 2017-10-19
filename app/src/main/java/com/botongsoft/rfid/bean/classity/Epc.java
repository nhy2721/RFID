package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**  epc与档号对照表
 * Created by pc on 2017/6/14.
 */
@Table
public class Epc implements java.io.Serializable {

    @Id
    private int lid;//安卓端主键
    @Column
    private int jlid;
    @Column
    private String bm;
    @Column
    private int epccode;
    @Column
    private String archiveno;
    @Column
    private long anchor;
    @Column
    private int status;
    @Column
    private int ztcode;

    public int getZtcode() {
        return ztcode;
    }

    public void setZtcode(int ztcode) {
        this.ztcode = ztcode;
    }

    public int getLid() {
        return lid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getJlid() {
        return jlid;
    }

    public void setJlid(int jlid) {
        this.jlid = jlid;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public int getEpccode() {
        return epccode;
    }

    public void setEpccode(int epccode) {
        this.epccode = epccode;
    }

    public String getArchiveno() {
        return archiveno;
    }

    public void setArchiveno(String archiveno) {
        this.archiveno = archiveno;
    }

    public long getAnchor() {
        return anchor;
    }

    public void setAnchor(long anchor) {
        this.anchor = anchor;
    }
}
