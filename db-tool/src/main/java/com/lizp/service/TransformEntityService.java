package com.lizp.service;

import com.lizp.config.TransformEntityConfig;

/**
 * @author lizp4
 * @date 2022-10-6 11:23
 * transfer A entity‘s excel to B entity by producing B's insert SQL
 */
public interface TransformEntityService<T, R> {
    /**
     * begin to transform
     *
     * @param transformEntityConfig
     */
    void transform(TransformEntityConfig<T, R> transformEntityConfig);

    /**
     * output insert sql to target path
     *
     * @param sourceEntity source entity
     */
    void output(T sourceEntity);
}
