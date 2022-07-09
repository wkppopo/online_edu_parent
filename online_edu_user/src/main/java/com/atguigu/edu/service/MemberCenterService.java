package com.atguigu.edu.service;

import com.atguigu.edu.entity.MemberCenter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wang
 * @since 2022-07-08
 */
public interface MemberCenterService extends IService<MemberCenter> {

    //获取每日注册人数
    Integer queryRegisterNum(String day);

}
