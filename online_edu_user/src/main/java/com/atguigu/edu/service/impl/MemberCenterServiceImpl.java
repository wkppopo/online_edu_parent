package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.MemberCenter;
import com.atguigu.edu.mapper.MemberCenterMapper;
import com.atguigu.edu.service.MemberCenterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-07-08
 */
@Service
public class MemberCenterServiceImpl extends ServiceImpl<MemberCenterMapper, MemberCenter> implements MemberCenterService {

    @Override
    public Integer queryRegisterNum(String day) {
        return baseMapper.queryRegisterNum(day);
    }
}
