package com.atguigu.edu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectVO {
    @ApiModelProperty(value = "课程类别ID")
    private String id;
    @ApiModelProperty(value = "课程分类名称")
    private String title;
    @ApiModelProperty(value = "课程分类子集合")
    private List<SubjectVO> children=new ArrayList<>();
}
