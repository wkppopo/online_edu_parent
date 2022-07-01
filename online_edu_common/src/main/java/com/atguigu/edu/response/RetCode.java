package com.atguigu.edu.response;
//定义操作状态码
public enum RetCode {
    OK(20000),ERROR(20001),LOGIN_ERROR(20002),ACCESS_ERROR(20003),REMOTE_ERROR(20004),REPEAT_ERROR(20005);

    private Integer code;

    private RetCode (Integer code){
        this.code=code;
    }

    public Integer getCode(){
        return code;
    }

}