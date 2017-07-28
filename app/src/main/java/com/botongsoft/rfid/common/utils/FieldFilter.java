package com.botongsoft.rfid.common.utils;

import java.lang.reflect.Field;

/**
 * Created by pc on 2017/7/28.
 */

public interface FieldFilter {
    boolean accept(Field field);
}
