package com.lizp.service;

import com.lizp.annotation.TableField;
import com.lizp.annotation.TableName;
import com.lizp.config.TransformEntityConfig;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.lizp.constant.Constant.FILE_SEPARATOR;
import static com.lizp.constant.Constant.LINE_SEPARATOR;

/**
 * @author lizp4
 * @date 2022-10-8 15:26
 * AbstractTransformEntityService
 */
public abstract class AbstractTransformEntityService<T, R> implements TransformEntityService<T, R> {
    protected TransformEntityConfig<T, R> transformEntityConfig;

    protected File outputFile;

    protected FileWriter fileWriter;

    @Override
    @SneakyThrows
    public void transform(TransformEntityConfig<T, R> transformEntityConfig) {
        this.transformEntityConfig = transformEntityConfig;

        readData();

        fileWriter.close();
    }

    /**
     * read data by someway
     */
    public abstract void readData();


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


    protected String generateSql(R targetEntity) throws IllegalAccessException {
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
