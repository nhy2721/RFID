package com.botongsoft.rfid.common;

import android.support.annotation.StringDef;

import com.botongsoft.rfid.bean.classity.CheckError;
import com.botongsoft.rfid.bean.classity.CheckPlan;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatil;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatilDel;
import com.botongsoft.rfid.bean.classity.Epc;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.LogDetail;
import com.botongsoft.rfid.bean.classity.LogMain;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.classity.MjjgdaDelInfos;
import com.botongsoft.rfid.bean.classity.ServerLogRecord;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/7
 * Description:
 */
public class Constant {
    public static void main(String args[]) {




        System.out.println(Constant.reqDatas("B001000121111"));
    }
    public static final String THEME_MODEL = "theme_model";
    public static final String USER_GENDER = "user_gender";

    //rank type 排行等级
    public static final int TYPE_HOT_RANKING = 0;
    public static final int TYPE_RETAINED_RANKING = 1;
    //左右 
    public static final int VALUE_LEFT = 1;
    public static final int VALUE_RIGHT = 2;
    /**
     * 类型为密集架格
     */
    public static final int LX_MJJG = 1;
    /**
     * 类型为档案
     */
    public static final int LX_MJGDA = 2;
    /**
     * 类型为案卷载体
     */
    public static final int LX_AJZT = 3;

    /**
     * 根据传入的RFID条码分解成数组 如果这个分界方法变了，所有有使用这个方法的地方需要
     * 重新检查新获得的数组位置是否正确
     * @param value "B000100000121111"
     * @return
     */
    public static String[] reqDatas(String value) {
        //value = "B,0001,00000121,1,1,1"
        String[] s = new String[6];
        s[0]=value.substring(0,1);//B  标签头
        s[1]=value.substring(1,5);//0001 库房ID 前面补0
        s[2]=value.substring(5,13);//00000121 密集架ID 前面补0
        s[3]=value.substring(13,14);//1或2 左或右
        s[4]=value.substring(14,15);//组数
        s[5]=value.substring(15,16);//层数
        //        System.out.println(Integer.valueOf(s[2]).toString());
        return s;
    }

    public static Mjjg getMjjg(String[] s) {
        return (Mjjg) DBDataUtils.getInfo(Mjjg.class, "mjjid", Integer.valueOf(s[2]).toString(), "zy", Integer.valueOf(s[3]).toString(),
                "cs", Integer.valueOf(s[5]).toString(), "zs", Integer.valueOf(s[4]).toString());
    }

    /**
     * 自动补0
     * @param value
     * @return
     */
    public static String coverNum(String value) {
        String s =String.format("%016d", Integer.valueOf( value));
        return s;
    }

    /**
     * 根据扫描的值返回类型
     */
    public static int getLx(String value) {
        int lx;
        //        String temp[] = value.split("-");
        //        if (temp.length == 2) {//目前暂定密集格档案格式为 表名“bm-jlid”
        //            lx = Constant.LX_MJGDA;
        //        } else {
        //            lx = Constant.LX_MJJG;
        //        }

        if (value.substring(0, 1).equals("B")) {
            lx = Constant.LX_MJJG;
        } else if (value.substring(0, 1).equals("A")) {
            lx = Constant.LX_AJZT;
        } else {
            lx = Constant.LX_MJGDA;
        }
        return lx;
    }

    public static final int delayRun = 800;
    ;

    @StringDef({
            Gender.MALE,
            Gender.FEMALE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        String MALE = "male";

        String FEMALE = "female";
    }

    public static final int UPFLOOR = 1;

    public static final Class[] tables = {CheckError.class, CheckPlan.class, CheckPlanDeatil.class,
            Kf.class, Mjj.class, Mjjg.class, Mjjgda.class, MjjgdaDelInfos.class, CheckPlanDeatilDel.class,
            Epc.class, LogMain.class, LogDetail.class, ServerLogRecord.class};
}
