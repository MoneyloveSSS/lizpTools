package com.lizp.client;

import com.lizp.config.TransformEntityConfig;
import com.lizp.service.TransformEntityService;
import com.lizp.service.impl.TransformEntityExcelServiceImpl;

/**
 * @author lizp4
 * @date 2022-10-6 11:23
 * transfer A entity‘s excel to B entity by producing B's insert SQL
 */
public class TransformEntityExcelClient {
    private TransformEntityExcelClient() {
        //do nothing
    }

    /**
     * begin to transform
     *
     * @param transformEntityConfig
     */
    public static <T, R> void transform(TransformEntityConfig<T, R> transformEntityConfig) {
        TransformEntityService<T, R> transformEntityService = new TransformEntityExcelServiceImpl<>();
        transformEntityService.transform(transformEntityConfig);
    }

}
