package com.atguigu.edu.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MyException extends Exception{
    @ExceptionHandler(Exception.class)
    public void myException(Exception e){
        log.info("异常被捕获...全局捕获");
        System.out.println("myException 0"+e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public void myException1(Exception e){
        log.info("异常被捕获. 特殊异常..全局捕获");
        System.out.println("myException 1"+e.getMessage());
    }

    @ExceptionHandler(MyException1.class)
    public void myException2(Exception e){
        log.info("异常被捕获...全局捕获");
        System.out.println("自定义异常打印异常信息："+e.getMessage());
    }
}
