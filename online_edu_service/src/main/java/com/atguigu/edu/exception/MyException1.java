package com.atguigu.edu.exception;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RuntimeException 遇到事务可以回滚
 * Exception 遇到事务不能回滚
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException1 extends RuntimeException {
    @ApiModelProperty(value = "异常code")
    private Integer code;
    @ApiModelProperty(value = "异常信息")
    private String message;

}
