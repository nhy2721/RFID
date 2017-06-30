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
