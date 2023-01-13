package com.lizp.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lizp.service.AbstractTransformEntityService;
import com.lizp.service.TransformEntityService;

/**
 * @author lizp4
 * @date 2022-10-6 11:23
 * transfer A entity‘s excel to B entity by producing B's insert SQL
 */
public class TransformEntityExcelServiceImpl<T, R> extends AbstractTransformEntityService<T, R> implements TransformEntityService<T, R> {

    @Override
    public void readData() {
        EasyExcelFactory.read(transformEntityConfig.getSourcePath(), transformEntityConfig.getOriginClass(), new ExcelListener<T, R>(this)).sheet().doRead();

    }

    /**
     * easy excel listener
     *
     * @author lizp4
     */
    public static class ExcelListener<T, R> extends AnalysisEventListener<T> {
        private final TransformEntityService<T, R> transformEntityService;

        public ExcelListener(TransformEntityService<T, R> transformEntityService) {
            this.transformEntityService = transformEntityService;
        }

        @Override
        public void invoke(T data, AnalysisContext context) {
            transformEntityService.output(data);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //do nothing
        }
    }
}
