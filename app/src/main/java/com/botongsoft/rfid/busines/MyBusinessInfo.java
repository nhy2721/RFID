package com.botongsoft.rfid.busines;

import java.io.Serializable;

/**
 * Created by renpingqing on 17/1/19.
 */
public class MyBusinessInfo implements Serializable {

    private String name;//界面显示名称
    private String listSize;//需要更新条目数
    private int tag;//请求同步接口编号1000,1001,1002,1003...
    private Object obj;

    public MyBusinessInfo(String name, Integer tag, String listSize, Object obj) {
        this.name = name;
        this.tag = tag;
        this.listSize = listSize;
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListSize() {
        return listSize;
    }

    public void setListSize(String listSize) {
        this.listSize = listSize;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
