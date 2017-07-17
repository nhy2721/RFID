package com.botongsoft.rfid.common.utils;



import java.util.List;
import java.util.Map;

/**
 * 集合操作工具类
 * 
 * @author wangjie
 */
public class ListUtils {
    private static final String TAG = ListUtils.class.getSimpleName();

    /**
     * 判断List是否为空
     * 
     * @param
     * @return true表示为空
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        int objectSize = 0;
        if (object instanceof Map<?, ?>) {
            Map<?, ?> objectMap = (Map<?, ?>) object;
            objectSize = objectMap.size();
            LogUtils.d(TAG, "集合Map为size()=" + objectSize);
        } else if (object instanceof List<?>) {
            List<?> objectList = (List<?>) object;
            objectSize = objectList.size();
            LogUtils.d(TAG, "集合List为size()=" + objectSize);
        }

        if (objectSize == 0)
            return true;
        else
            return false;
    }

    /**
     * 集合设为NULL
     * 
     * @param
     */
    public static void setEmpty(Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof Map<?, ?>) {
            Map<?, ?> objectMap = (Map<?, ?>) object;
            objectMap.clear();
            objectMap = null;
            LogUtils.d(TAG, "集合Map设置为NULL");
        } else if (object instanceof List<?>) {
            List<?> objectList = (List<?>) object;
            objectList.clear();
            objectList = null;
            LogUtils.d(TAG, "集合List设置为NULL");
        }

        object = null;
    }
}
