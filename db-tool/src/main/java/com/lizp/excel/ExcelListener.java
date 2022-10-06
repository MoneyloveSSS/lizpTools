package com.lizp.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lizp.service.TransformEntityService;

/**
 * easy excel listener
 *
 * @param <T>
 * @author lizp4
 */
public class ExcelListener extends AnalysisEventListener<Object> {
    private TransformEntityService transformEntityService;

    public ExcelListener(TransformEntityService transformEntityService) {
        this.transformEntityService = transformEntityService;
    }

    @Override
    public void invoke(Object data, AnalysisContext context) {
        transformEntityService.output(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //do nothing
    }
}