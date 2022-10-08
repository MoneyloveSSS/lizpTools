package com.lizp.transform;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author lizp4
 * @date 2022-10-8 10:14
 */
@Data
public class Origin {

    @ExcelProperty(value = "name")
    private String name;

    @ExcelProperty(value = "is_good")
    private Integer good;
}
