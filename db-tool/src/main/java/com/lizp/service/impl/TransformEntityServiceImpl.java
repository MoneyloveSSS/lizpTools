package com.lizp.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.lizp.annotation.TableField;
import com.lizp.annotation.TableName;
import com.lizp.config.TransformEntityConfig;
import com.lizp.excel.ExcelListener;
import com.lizp.service.TransformEntityService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizp4
 * @date 2022-10-6 11:23
 * transfer A entity‘s excel to B entity by producing B's insert SQL
 */
public class TransformEntityServiceImpl<T, R> implements TransformEntityService<T, R> {

    private TransformEntityConfig<T, R> transformEntityConfig;

    @Override
    @SneakyThrows
    public void transform(TransformEntityConfig<T, R> transformEntityConfig) {
        this.transformEntityConfig = transformEntityConfig;

        File sourceExcel = new File(transformEntityConfig.getSourcePath());
        FileInputStream fileInputStream = new FileInputStream(sourceExcel);
        EasyExcelFactory.read(fileInputStream, transformEntityConfig.getOriginClass(), new ExcelListener(this)).sheet().doRead();
    }

    @Override
    @SneakyThrows
    public void output(T sourceEntity) {
        R targetEntity = transformEntityConfig.getTransformFunc().apply(sourceEntity);
        String insertSql = generateSql(targetEntity);
    }




    private String generateSql(R targetEntity) throws IllegalAccessException {
        List<String> fieldNameList = new ArrayList<>(16);
        List<String> valueList = new ArrayList<>(16);

        for (Field field : transformEntityConfig.getTargetClass().getFields()) {
            TableField tableField = field.getAnnotation(TableField.class);
            String fieldName = tableField.value();
            if (StringUtils.isBlank(fieldName)) {
                throw new UnsupportedOperationException("target class annotation TableField should not be empty");
            }

            Object value = field.get(targetEntity);
            if (value == null) {
                continue;
            }
            String sqlValue = "'" + value.toString() + "'";
            fieldNameList.add(fieldName);
            valueList.add(sqlValue);
        }
        String fieldNameString = String.join(",", fieldNameList);
        String valueString = String.join(",", valueList);

        TableName tableNameAnnotation = transformEntityConfig.getTargetClass().getAnnotation(TableName.class);
        String tableName = tableNameAnnotation.value();
        if (StringUtils.isBlank(tableName)) {
            throw new UnsupportedOperationException("target class annotation TableName should not be empty");
        }

        return "INSERT INTO" + tableName + "(" + fieldNameString + ") VALUES(" + valueString + ")";
    }
}
