package com.atguigu.edu.controller;

import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.MemberCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author wang
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/member/center")
@CrossOrigin
public class MemberCenterController {
    @Autowired
    private MemberCenterService memberCenterService;

    //获取每日注册人数
    @GetMapping("/queryRegisterNum/{day}")
    public RetVal queryRegisterNum(@PathVariable String day){
        Integer registerNum=memberCenterService.queryRegisterNum(day);
        return RetVal.success().data("registerNum",registerNum);
    }
}

