package com.atguigu.edu.controller;

import com.atguigu.edu.response.RetVal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu")
@CrossOrigin //设置跨域访问 这个注解加载控制类上
public class EntranceController {
    //1. 登录接口  /edu/user/login   /edu/user/info
    @PostMapping("/user/login")
    public RetVal login(){
        return RetVal.success().data("token","admin");
    }

    //2. 用户信息
    @GetMapping("/user/info")
    public RetVal info(){
        return RetVal.success()
                .data("name","admin")
                .data("roles","[admin]")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
