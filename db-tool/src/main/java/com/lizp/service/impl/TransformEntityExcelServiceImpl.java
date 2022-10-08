package com.lizp.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.lizp.excel.ExcelListener;
import com.lizp.service.AbstractTransformEntityService;

/**
 * @author lizp4
 * @date 2022-10-6 11:23
 * transfer A entity‘s excel to B entity by producing B's insert SQL
 */
public class TransformEntityExcelServiceImpl<T, R> extends AbstractTransformEntityService<T, R> {

    @Override
    public void readData() {
        EasyExcelFactory.read(transformEntityConfig.getSourcePath(), transformEntityConfig.getOriginClass(), new ExcelListener<T, R>(this)).sheet().doRead();

    }

}
