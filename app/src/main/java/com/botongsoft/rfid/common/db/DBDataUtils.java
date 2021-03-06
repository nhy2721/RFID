package com.botongsoft.rfid.common.db;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * db相关Annotation
 *
 * @Check check约束
 * @Column 列名
 * @Finder 一对多、多对一、多对多关系(见sample的Parent、Child中的使用)
 * @Foreign 外键
 * @Id 主键，当为int类型时，默认自增。 非自增时，需要设置id的值
 * @NoAutoIncrement 不自增
 * @NotNull 不为空
 * @Table 表名
 * @Transient 不写入数据库表结构
 * @Unique 唯一约束
 */
public class DBDataUtils {

    public static int getCount(Class<?> entityType) {
        DbUtils db = DataBaseCreator.create();
        int count = 0;
        try {
            List list = db.findAll(Selector.from(entityType));
            if (list != null) {
                count = list.size();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int getCount(Class<?> entityType, String key, String op, String value) {
        DbUtils db = DataBaseCreator.create();
        int count = 0;
        try {
            List list = db.findAll(Selector.from(entityType).where(key, op,
                    value));
            if (list != null) {
                count = list.size();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int getCount(Class<?> entityType, String key1, String op1, String value1, String key2, String op2, String value2) {
        DbUtils db = DataBaseCreator.create();
        int count = 0;
        try {
            List list = db.findAll(Selector.from(entityType).where(key1, op1,
                    value1).and(key2, op2, value2));
            if (list != null) {
                count = list.size();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int getCount(Class<?> entityType, String key1, String op1, String value1, String key2, String op2, String value2,
                               String key3, String op3, String value3, String key4, String op4, String value4) {
        DbUtils db = DataBaseCreator.create();
        int count = 0;
        try {
            List list = db.findAll(Selector.from(entityType).where(key1, op1,
                    value1).and(key2, op2, value2).and(key3, op3, value3).and(key4, op4, value4));
            if (list != null) {
                count = list.size();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return count;
    }
    /****
     * @return
     */
    // public static List<PayOrderInfo> getInfos() {
    // DbUtils dbUtils = DbUtils.create(ApplicationApp.context);
    // List<PayOrderInfo> list = null;
    // try {
    // list = dbUtils.findAll(Selector.from(PayOrderInfo.class).orderBy(
    // "times", true));
    //
    // } catch (DbException e) {
    // e.printStackTrace();
    // }
    // return list;
    // }

    /****
     * 分页按条件查询
     *
     * @param pageSize
     * @param pageIndex
     * @param key
     * @param value
     * @return
     */
    /**
     * 获取数据
     *
     * @param entityType
     * @return
     */
    public static Object getInfos(Class<?> entityType) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取数据
     *
     * @param entityType
     * @return
     */
    public static Object getInfosHasOp(Class<?> entityType, String key, String op, String value) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, op, value));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取数据
     *
     * @param entityType
     * @return
     */
    public static Object getInfosHasOp(Class<?> entityType, String key, String op, String value, String key1, String op1, String value1) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, op, value).and(key1, op1, value1));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取数据
     *
     * @param entityType
     * @return
     */
    public static Object getInfosHasOp(Class<?> entityType, String key, String op, String value,
                                       String key1, String op1, String value1, String key2, String op2, String value2) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, op, value).and(key1, op1, value1)
                    .and(key2, op2, value2));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取数据
     *
     * @param entityType
     * @return
     */
    public static Object getInfosHasOp(Class<?> entityType, String key, String op, String value,
                                       String key1, String op1, String value1,
                                       String key2, String op2, String value2,
                                       String key3, String op3, String value3
    ) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, op, value).and(key1, op1, value1)
                    .and(key2, op2, value2).and(key3, op3, value3));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取数据
     *
     * @param entityType
     * @return
     */
    public static Object getInfosHasOp(Class<?> entityType, String key, String op, String value,
                                       String key1, String op1, String value1,
                                       String key2, String op2, String value2,
                                       String key3, String op3, String value3,
                                       String key4, String op4, String value4) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, op, value).and(key1, op1, value1)
                    .and(key2, op2, value2).and(key3, op3, value3).and(key4, op4, value4));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static long count(Class<?> entityType, String key, String op, String value) throws DbException {
        DbUtils db = DataBaseCreator.create();
        return db.count(Selector.from(entityType).where(key, op, value));
    }

    public static long count(Class<?> entityType, String key, String op, String value,
                             String key2, String op2, String value2) throws DbException {
        DbUtils db = DataBaseCreator.create();
        return db.count(Selector.from(entityType).where(key, op, value).and(key2, op2, value2));
    }

    public static long countOr(Class<?> entityType, String key, String op, String value,
                               String key2, String op2, String value2) throws DbException {
        DbUtils db = DataBaseCreator.create();
        return db.count(Selector.from(entityType).where(key, op, value).or(key2, op2, value2));
    }

    /**
     * 获取数据
     *
     * @param key
     * @param value
     * @return
     */
    public static Object getInfoHasOp(Class<?> entityType, String key, String op, String value) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key, op, value).orderBy(key, true));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfoHasOp(Class<?> entityType, String key1, String op1, String value1,
                                      String key2, String op2, String value2) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, op1, value1).and(key2, op2, value2));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfoHasOp(Class<?> entityType, String key1, String op1, String value1,
                                      String key2, String op2, String value2, String key3, String op3, String value3) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, op1, value1).and(key2, op2, value2).and(key3, op3, value3));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfoHasOp(Class<?> entityType, String key1, String op1, String value1,
                                      String key2, String op2, String value2, String key3, String op3,
                                      String value3, String key4, String op4, String value4) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, op1, value1).and(key2, op2, value2).and(key3, op3, value3).and(key4, op4, value4));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 获取数据
     *
     * @param key
     * @param value
     * @return
     */
    public static Object getInfo(Class<?> entityType, String key, String value) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key, "=", value));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfo(Class<?> entityType, String key1, String value1, String key2, String value2) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, "=", value1).and(key2, "=", value2));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfo(Class<?> entityType, String key1, String value1, String key2, String value2, String key3, String value3) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, "=", value1).and(key2, "=", value2).and(key3, "=", value3));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfo(Class<?> entityType, String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, "=", value1).and(key2, "=", value2).and(key3, "=", value3).and(key4, "=", value4));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfo(Class<?> entityType, String key1, String value1, String key2, String value2,
                                 String key3, String value3, String key4, String value4, String key5, String value5) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, "=", value1).and(key2, "=", value2).and(key3, "=", value3).and(key4, "=", value4).and(key5, "=", value5));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfo(Class<?> entityType, String key1, String value1, String key2, String value2,
                                 String key3, String value3, String key4, String value4, String key5, String value5,
                                 String key6, String value6) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, "=", value1).and(key2, "=", value2).and(key3, "=", value3)
                    .and(key4, "=", value4).and(key5, "=", value5).and(key6, "=", value6));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Object getInfo(Class<?> entityType, String key1, String value1, String key2, String value2,
                                 String key3, String value3, String key4, String value4, String key5, String value5,
                                 String key6, String value6, String key7, String value7) {
        DbUtils db = DataBaseCreator.create();
        Object info = null;
        try {
            info = db.findFirst(Selector.from(entityType)
                    .where(key1, "=", value1).and(key2, "=", value2).and(key3, "=", value3)
                    .and(key4, "=", value4).and(key5, "=", value5).and(key6, "=", value6).and(key7, "=", value7));

        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }
    /**
     * 获取数据
     *
     * @param key
     * @param value
     * @return
     */
    //	public static Object getInfos(Class<?> entityType, String key, String value) {
    //		DbUtils db = DataBaseCreator.create();
    //		Object list = null;
    //		try {
    //			list = db.findAll(Selector.from(entityType).orderBy("times", true)
    //					.where(key, "=", value));
    //		} catch (DbException e) {
    //			e.printStackTrace();
    //		}
    //		return list;
    //
    //	}

    /**
     * 获取数据
     *
     * @param key
     * @param value
     * @return
     */
    //	public static Object getInfos(Class<?> entityType, String key, int value,
    //			String key1, int value1) {
    //		DbUtils db = DataBaseCreator.create();
    //		Object list = null;
    //		try {
    //			list = db.findAll(Selector.from(entityType).orderBy("times", true)
    //					.where(key, "=", value)
    //					.and(WhereBuilder.b(key1, "=", value1)));
    //		} catch (DbException e) {
    //			e.printStackTrace();
    //		}
    //		return list;
    //
    //	}

    /***
     * 根据账号获取聊天消息
     *
     * @param entityType
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    //	public static Object getMessageLogs(Class<?> entityType, String key,
    //			String value, String key1, int value1) {
    //		DbUtils db = DataBaseCreator.create();
    //		Object list = null;
    //		try {
    //			list = db.findAll(Selector.from(entityType).orderBy("times", false)
    //					.where(key, "=", value)
    //					.and(WhereBuilder.b(key1, "=", value1)));
    //		} catch (DbException e) {
    //			e.printStackTrace();
    //		}
    //		return list;
    //
    //	}
    /**
     * 获取数据
     *
     * @param key
     * @param value
     * @return
     */
    public static Object getInfos(Class<?> entityType, String key, String value) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, "=", value));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 获取数据
     *
     * @param key
     * @param value
     * @return
     */
    public static Object getInfos(Class<?> entityType, String key, boolean value) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, "=", value));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 删除数据
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean deleteInfo(Class<?> entityType, String key,
                                     String value) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.delete(entityType, WhereBuilder.b(key, "=", value));
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 删除数据
     *
     * @param key1
     * @param value1
     * @return
     */
    public static boolean deleteInfo(Class<?> entityType, String key1, String value1, String key2, String value2) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.delete(entityType, WhereBuilder.b(key1, "=", value1).and(key2, "=", value2));
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 删除数据
     *
     * @param key1
     * @param value1
     * @return
     */
    public static boolean deleteInfo(Class<?> entityType, String key1, String value1, String key2,
                                     String value2, String key3, String op3, String value3) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.delete(entityType, WhereBuilder.b(key1, "=", value1).and(key2, "=", value2).and(key3, op3, value3));
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 删除数据
     *
     * @param key1
     * @param value1
     * @return
     */
    public static boolean deleteInfo(Class<?> entityType, String key1, String value1, String key2,
                                     String value2, String key3, String op3, String value3,
                                     String key4, String op4, String value4) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.delete(entityType, WhereBuilder.b(key1, "=", value1).and(key2, "=", value2).and(key3, op3, value3)
                    .and(key4, op4, value4));
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
    /**
     * 删除全部数据
     *
     * @param
     * @param
     * @return
     */
    public static boolean deleteInfos(Class<?> entityType) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.deleteAll(entityType);
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static boolean deleteInfos(Class<?> entityType, String key1, String value1, String key2, String value2) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.delete(entityType, WhereBuilder.b(key1, "=", value1).and(key2, "=", value2));
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static boolean deleteInfos(Class<?> entityType, String key1, String value1, String key2,
                                      String value2, String key3, String value3) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.delete(entityType, WhereBuilder.b(key1, "=", value1).and(key2, "=", value2).and(key3, "=", value3));
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static boolean deleteInfos(Class<?> entityType, String key1, String op1, String value1, String key2, String op2,
                                      String value2, String key3, String op3, String value3, String key4, String op4, String value4) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.delete(entityType, WhereBuilder.b(key1, op1, value1).and(key2, op2, value2).and(key3, op3, value3).and(key4, op4, value4));
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static boolean deleteInfos(Class<?> entityType, String key1, String op1, String value1,
                                      String key2, String op2, String value2,
                                      String key3, String op3, String value3,
                                      String key4, String op4, String value4,
                                      String key5, String op5, String value5,
                                      String key6, String op6, String value6,
                                      String key7, String op7, String value7) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.delete(entityType, WhereBuilder.b(key1, op1, value1).and(key2, op2, value2).
                    and(key3, op3, value3).and(key4, op4, value4).and(key5, op5, value5)
                    .and(key6, op6, value6).and(key7, op7, value7));
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }


    /***
     * 删单条数据
     *
     * @param obj
     */
    public static void deleteInfo(Object obj) {
        DbUtils db = DataBaseCreator.create();
        try {
            db.delete(obj);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public static boolean delList(List<?> entities) {
        DbUtils db = DataBaseCreator.create();
        boolean isSuccess = false;
        try {
            db.deleteAll(entities);
            isSuccess = true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static void save(Object obj) {
        try {
            //			DbUtils dbUtils = DbUtils.create(ApplicationApp.context);
            DbUtils dbUtils = DataBaseCreator.create();
            dbUtils.save(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAll(List list) {
        try {
            //			DbUtils dbUtils = DbUtils.create(ApplicationApp.context);
            DbUtils dbUtils = DataBaseCreator.create();
            dbUtils.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveOrupdate(Object obj) {
        DbUtils dbUtils = DataBaseCreator.create();
        try {
            dbUtils.saveOrUpdate(obj);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /***
     * 更新
     *
     * @param
     */
    public static void updateAll(List list) {
        try {
            DbUtils dbUtils = DataBaseCreator.create();
            dbUtils.updateAll(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /***
     * 更新
     *
     * @param
     */
    public static void update(Object obj) {
        try {
            DbUtils dbUtils = DataBaseCreator.create();
            dbUtils.update(obj);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /***
     * 更新
     *
     * @param payOrderInfo
     */
    // public static void update(PayOrderInfo payOrderInfo) {
    // try {
    // DbUtils dbUtils = DbUtils.create(ApplicationApp.context);
    //
    // List<PayOrderInfo> stus = dbUtils.findAll(Selector.from(
    // PayOrderInfo.class).where("o", "=", payOrderInfo.o));
    // if (!ListUtils.isEmpty(stus)) {
    // PayOrderInfo payOrder = stus.get(0);
    // payOrder.payFlag = payOrderInfo.payFlag;
    // payOrder.t = payOrderInfo.t;
    // payOrder.u = payOrderInfo.u;
    // dbUtils.update(payOrder);
    // }
    // } catch (DbException e) {
    // e.printStackTrace();
    // }
    // }

    /****
     * 每个用户只保存最新的一条用户聊天记录
     *
     * @param info
     */
    //	public static void saveUpdatePushMessage(PushMessage info) {
    //		DbUtils db = DataBaseCreator.create();
    //		try {
    //			PushMessage item = db.findFirst(Selector.from(PushMessage.class)
    //					.where("open", "=", info.open));
    //
    //			LogUtils.d("huhui", "---info= " + info.open);
    //
    //			if (item == null) {
    //				db.save(info);
    //			} else {
    //				item.content = info.content;
    //				item.date = info.date;
    //				item.type = info.type;
    //				item.times = info.times;
    //				db.update(item);
    //			}
    //
    //		} catch (DbException e) {
    //			e.printStackTrace();
    //		}
    //	}


}
