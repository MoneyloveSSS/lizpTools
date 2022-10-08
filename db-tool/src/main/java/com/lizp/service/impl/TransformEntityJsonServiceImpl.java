package com.lizp.service.impl;

import com.lizp.service.AbstractTransformEntityService;
import com.lizp.service.TransformEntityService;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author lizp4
 * @date 2022-10-8 15:58
 * transfer A entity‘ from JSON to B entity by producing B's insert SQL
 */
public class TransformEntityJsonServiceImpl<T, R> extends AbstractTransformEntityService<T, R> implements TransformEntityService<T, R> {
    @Override
    @SneakyThrows
    public void readData() {
        File sourceJsonFile = new File(transformEntityConfig.getSourcePath());
        FileInputStream fileInputStream = new FileInputStream(sourceJsonFile);

    }
}
