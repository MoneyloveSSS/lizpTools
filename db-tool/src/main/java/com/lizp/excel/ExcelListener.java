package com.lizp.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lizp.service.TransformEntityService;

/**
 * easy excel listener
 *
 * @author lizp4
 */
public class ExcelListener<T, R> extends AnalysisEventListener<T> {
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