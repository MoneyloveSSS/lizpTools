package com.lizp.client;

import com.lizp.config.TransformEntityConfig;
import com.lizp.service.TransformEntityService;
import com.lizp.service.impl.TransformEntityJsonServiceImpl;

/**
 * @author lizp4
 * @date 2022-10-6 11:23
 * transfer A entity from a JSON array File to B entity by producing B's insert SQL
 * use jackson to deserialize
 */
public class TransformEntityJsonClient {
    private TransformEntityJsonClient() {
        //do nothing
    }

    /**
     * begin to transform
     *
     * @param transformEntityConfig
     */
    public static <T, R> void transform(TransformEntityConfig<T, R> transformEntityConfig) {
        TransformEntityService<T, R> transformEntityService = new TransformEntityJsonServiceImpl<>();
        transformEntityService.transform(transformEntityConfig);
    }

}
