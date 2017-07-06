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

    public static boolean delInfo(List<?> entities) {
        return DBDataUtils.delList(entities);
    }


    public static boolean delInfo(Class<Mjjgda> entities, String key1, String value1, String key2, String value2) {
        return DBDataUtils.deleteInfo(entities, key1, value1, key2, value2);
    }
}
