package com.lizp.annotation;

import java.lang.annotation.*;

/**
 * @author lizp4
 * @date 2022-10-6 11:06
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableField {
    /**
     * db field name
     * @return
     */
    String value();
}
