package com.atguigu.edu.handler;

import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.UserFeignService;
import org.springframework.stereotype.Component;

@Component
public class UserFeignHandler implements UserFeignService {
    @Override
    public RetVal queryRegisterNum(String day) {
        return RetVal.error().data("error","兜底方法");
    }
}
