package com.botongsoft.rfid.common.db;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class CheckDetailSearchDb {

    /**
     * 根据传入的bm，jlid返回密集格对象
     */

    /**
     * 获取数据
     *
     * @param entityType
     * @return
     */
    public static Object getInfosHasOp(Class<?> entityType, String key, String op, String value, String key1, String op1, String value1,int limit) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, op, value).and(key1, op1, value1).limit(limit));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }
}
