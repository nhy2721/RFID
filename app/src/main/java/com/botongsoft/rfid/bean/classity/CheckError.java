package com.botongsoft.rfid.bean.classity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by pc on 2017/6/14.
 */
@Table
public class CheckError implements java.io.Serializable {

    @Id
    private int lid;//安卓端主键
    @Column
    private int id;
    @Column
    private int mjgid;
    @Column
    private int pdid;
    @Column
    private int kfid;
    @Column
    private int mjjid;
    @Column
    private int zy;

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

    public int getPdid() {
        return pdid;
    }

    public void setPdid(int pdid) {
        this.pdid = pdid;
    }

    public int getKfid() {
        return kfid;
    }

    public void setKfid(int kfid) {
        this.kfid = kfid;
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
}
