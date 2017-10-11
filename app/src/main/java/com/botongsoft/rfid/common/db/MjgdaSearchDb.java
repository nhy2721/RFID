package com.botongsoft.rfid.common.db;

import com.botongsoft.rfid.bean.classity.Mjjgda;

import java.util.List;

public class MjgdaSearchDb {

    /**
     * 根据传入的bm，jlid返回密集格对象
     */

    public static Mjjgda getInfo(Class<?> entityType, String key1, String value1, String key2, String value2) {
        return (Mjjgda) DBDataUtils.getInfo(entityType, key1, value1, key2, value2);
    }

    public static Mjjgda getInfoHasOp(Class<?> entityType, String key1, String op1, String value1,
                                      String key2, String op2, String value2, String key3, String op3, String value3) {
        return (Mjjgda) DBDataUtils.getInfoHasOp(entityType, key1, op1, value1, key2, op2, value2,
                key3, op3, value3);
    }
    public static Mjjgda getInfoHasOp(Class<?> entityType, String key1, String op1, String value1,
                                      String key2, String op2, String value2) {
        return (Mjjgda) DBDataUtils.getInfoHasOp(entityType, key1, op1, value1, key2, op2, value2);
    }

    public static boolean delInfo(List<?> entities) {
        return DBDataUtils.delList(entities);
    }


    public static boolean delInfo(Class<Mjjgda> entities, String key1, String value1, String key2, String value2) {
        return DBDataUtils.deleteInfo(entities, key1, value1, key2, value2);
    }
}
