package com.botongsoft.rfid.bean.JsonBean;

import java.io.Serializable;

/**获取服务器各表更新数用于界面显示
 * Created by pc on 2017/7/17.
 */

public class CountJson implements Serializable {
    public String kf;//库房
    public String mjj;//密集架
    public String mjjg;//密集格
    public String mjgda;//档案
    public String checkplan;//盘点计划
    public String checkErrorNum;//盘点计划
    public String checkDetailNum;//盘点计划
}
