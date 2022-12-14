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
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizp4
 * @date 2022-10-6 11:23
 * transfer A entity‘s excel to B entity by producing B's insert SQL
 */
public class TransformEntityExcelServiceImpl<T, R> implements TransformEntityService<T, R> {

    private TransformEntityConfig<T, R> transformEntityConfig;

    private File outputFile;

    private FileWriter fileWriter;

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    @SneakyThrows
    public void transform(TransformEntityConfig<T, R> transformEntityConfig) {
        this.transformEntityConfig = transformEntityConfig;

        EasyExcelFactory.read(transformEntityConfig.getSourcePath(), transformEntityConfig.getOriginClass(), new ExcelListener<T, R>(this)).sheet().doRead();

        fileWriter.close();
    }

    @Override
    @SneakyThrows
    public void output(T sourceEntity) {
        R targetEntity = transformEntityConfig.getTransformFunc().apply(sourceEntity);
        String insertSql = generateSql(targetEntity);

        String tableName = transformEntityConfig.getTargetClass().getAnnotation(TableName.class).value();
        if (outputFile == null) {
            outputFile = new File(transformEntityConfig.getOutputPath() + FILE_SEPARATOR + tableName + ".txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            fileWriter = new FileWriter(outputFile);
        }

        fileWriter.write(insertSql);
    }


    private String generateSql(R targetEntity) throws IllegalAccessException {
        List<String> fieldNameList = new ArrayList<>(16);
        List<String> valueList = new ArrayList<>(16);

        for (Field field : transformEntityConfig.getTargetClass().getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
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

        return "INSERT INTO " + tableName + "(" + fieldNameString + ") VALUES(" + valueString + ");" + LINE_SEPARATOR;
    }
}
