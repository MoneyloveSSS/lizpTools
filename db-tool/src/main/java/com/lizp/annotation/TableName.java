package com.lizp.annotation;

import java.lang.annotation.*;

/**
 * @author lizp4
 * @date 2022-10-6 11:06
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableName {
    /**
     * db table name
     * @return
     */
    String value();
}
