package com.botongsoft.rfid.busines;

import java.io.Serializable;

/**
 * Created by renpingqing on 17/1/19.
 */
public class MyBusinessInfo implements Serializable {

    private String name;

    private int listSize;
    private int tag;//请求同步接口编号1000,1001,1002,1003...
    private Object obj;

    public MyBusinessInfo(String name, Integer tag, Integer listSize, Object obj) {
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

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
