package com.lizp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.lizp.service.AbstractTransformEntityService;
import com.lizp.service.TransformEntityService;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * @author lizp4
 * @date 2022-10-8 15:58
 * transfer A entity‘ from JSON array to B entity by producing B's insert SQL
 */
public class TransformEntityJsonServiceImpl<T, R> extends AbstractTransformEntityService<T, R> implements TransformEntityService<T, R> {

    @Override
    @SneakyThrows
    public void readData() {
        File sourceJsonFile = new File(transformEntityConfig.getSourcePath());
        StringBuilder jsonStringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceJsonFile));) {

            String line;
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
        }

        String jsonString = jsonStringBuilder.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, this.transformEntityConfig.getOriginClass());
        List<T> originDataList = objectMapper.readValue(jsonString, collectionType);
        originDataList.forEach(super::output);
    }
}
