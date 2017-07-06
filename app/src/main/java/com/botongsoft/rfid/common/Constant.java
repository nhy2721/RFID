package com.botongsoft.rfid.common;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/7
 * Description:
 */
public class Constant {
    public static final String THEME_MODEL = "theme_model";
    public static final String USER_GENDER = "user_gender";

    public static final String BOOK_ZONE_ID = "book_zone_id";

    /**
     * category index
     */
    public static final int CATEGORY_LITERATURE = 0;
    public static final int CATEGORY_POPULAR = 1;
    public static final int CATEGORY_CULTURE = 2;
    public static final int CATEGORY_LIFE = 3;
    public static final int CATEGORY_MANAGEMENT = 4;
    public static final int CATEGORY_TECHNOLOGY = 5;
    public static final int CATEGORY_COUNTRY = 6;
    public static final int CATEGORY_SUBJECT = 7;
    public static final int CATEGORY_AUTHOR = 8;
    public static final int CATEGORY_PUBLISHER = 9;
    public static final int CATEGORY_THRONG = 10;
    public static final int CATEGORY_RELIGION = 11;
    public static final int CATEGORY_OTHER = 12;

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

    public static int getLx(String value) {
        int lx;
        String temp[] = value.split("-");
        if (temp.length == 2) {//目前暂定密集格档案格式为 表名“bm-jlid”
            lx = Constant.LX_MJGDA;
        } else {
            lx = Constant.LX_MJJG;
        }
        return lx;
    }

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
}
