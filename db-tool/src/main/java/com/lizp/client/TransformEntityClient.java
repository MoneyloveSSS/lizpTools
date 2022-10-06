package com.lizp.client;

import com.lizp.config.TransformEntityConfig;
import com.lizp.service.TransformEntityService;
import com.lizp.service.impl.TransformEntityServiceImpl;

/**
 * @author lizp4
 * @date 2022-10-6 11:23
 * transfer A entity‘s excel to B entity by producing B's insert SQL
 */
public class TransformEntityClient {

    /**
     * begin to transform
     *
     * @param transformEntityConfig
     */
    public <T, R> void transform(TransformEntityConfig<T, R> transformEntityConfig) {
        TransformEntityService<T, R> transformEntityService = new TransformEntityServiceImpl<>();
        transformEntityService.transform(transformEntityConfig);
    }

}
