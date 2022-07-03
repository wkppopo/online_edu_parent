package com.atguigu.edu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Student {
    @ExcelProperty(value = "学生id",index = 0)
    private Integer id;
    @ExcelProperty(value = "学生name",index = 1)
    private String name;
}
