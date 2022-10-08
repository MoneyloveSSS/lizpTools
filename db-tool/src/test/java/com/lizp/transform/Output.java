package com.lizp.transform;

import com.lizp.annotation.TableField;
import com.lizp.annotation.TableName;
import lombok.Data;

/**
 * @author lizp4
 * @date 2022-10-8 10:14
 */
@Data
@TableName("test_output")
public class Output {

    @TableField("output_name")
    private String outputName;

    @TableField("output_good")
    private Integer outputGood;
}
