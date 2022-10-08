package com.lizp.config;

import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author lizp4
 * @date 2022-10-6 11:26
 * all fields not null
 */
@Builder
@Getter
public class TransformEntityConfig<T, R> {
    /**
     * source path
     */
    private final String sourcePath;

    /**
     * output path
     */
    private final String outputPath;

    /**
     * origin class
     */
    private final Class<T> originClass;

    /**
     * target class
     */
    private final Class<R> targetClass;

    /**
     * transform function
     */
    private final Function<T, R> transformFunc;

}
