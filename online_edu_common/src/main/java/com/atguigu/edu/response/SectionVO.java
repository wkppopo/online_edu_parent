package com.atguigu.edu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SectionVO {
    @ApiModelProperty(value = "小节ID")
    private String id;

    @ApiModelProperty(value = "小节名称")
    private String title;
}
