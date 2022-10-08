package com.lizp.transform;

import com.lizp.client.TransformEntityExcelClient;
import com.lizp.config.TransformEntityConfig;
import org.junit.jupiter.api.Test;

/**
 * @author lizp4
 * @date 2022-10-8 10:09
 */
class TransformEntityExcelTest {

    @Test
    void testTransform() {
        TransformEntityConfig<Origin, Output> transformEntityConfig = TransformEntityConfig.<Origin, Output>builder()
                .sourcePath("D:\\tmp\\2022-10-8.xlsx")
                .originClass(Origin.class)
                .outputPath("D:\\tmp")
                .targetClass(Output.class)
                .transformFunc(origin -> {
                    Output output = new Output();
                    output.setOutputName(origin.getName());
                    output.setOutputGood(origin.getGood());
                    return output;
                }).build();
        TransformEntityExcelClient.transform(transformEntityConfig);
    }




}
