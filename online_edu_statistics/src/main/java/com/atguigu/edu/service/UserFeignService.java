package com.atguigu.edu.service;

import com.atguigu.edu.handler.UserFeignHandler;
import com.atguigu.edu.response.RetVal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "EDU-USER",fallback = UserFeignHandler.class)
public interface UserFeignService {
    //从用户微服务中获取指定日期注册的用户数量
    @GetMapping("/member/center/queryRegisterNum/{day}")
    public RetVal queryRegisterNum(@PathVariable String day);

}
