package com.botongsoft.rfid.busines;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renpingqing on 17/1/19.
 */
public class MyBusinessInfo implements Serializable {

    private String name;
    private String icon;
    private int listSize;
    private List list;
    private Object obj;

    public MyBusinessInfo(String name, String icon, Integer listSize, List list,Object obj) {
        this.name = name;
        this.icon = icon;
        this.listSize = listSize;
        this.list = list;
        this.obj = obj;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
