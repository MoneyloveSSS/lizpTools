package com.lizp.transform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lizp.annotation.TableField;
import com.lizp.annotation.TableName;
import com.lizp.client.TransformEntityJsonClient;
import com.lizp.config.TransformEntityConfig;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * @author lizp4
 * @date 2022-10-8 10:09
 */
class TransformEntityJsonTest {

    @Test
    void testTransform() {
        TransformEntityConfig<Origin, Output> transformEntityConfig = TransformEntityConfig.<Origin, Output>builder()
                .sourcePath("D:\\tmp\\json.txt")
                .originClass(Origin.class)
                .outputPath("D:\\tmp")
                .targetClass(Output.class)
                .transformFunc(origin -> {
                    Output output = new Output();
                    output.setOutputName(origin.getName());
                    output.setOutputGood(origin.getGood());
                    return output;
                }).build();
        TransformEntityJsonClient.transform(transformEntityConfig);
    }


    /**
     * @author lizp4
     * @date 2022-10-8 10:14
     */
    @Data
    public static class Origin {

        @JsonProperty(value = "name")
        private String name;

        @JsonProperty(value = "is_good")
        private Integer good;
    }

    /**
     * @author lizp4
     * @date 2022-10-8 10:14
     */
    @Data
    @TableName("test_output")
    public static class Output {

        @TableField("output_name")
        private String outputName;

        @TableField("output_good")
        private Integer outputGood;
    }
}
