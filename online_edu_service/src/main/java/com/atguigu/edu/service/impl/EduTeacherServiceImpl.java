package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.mapper.EduTeacherMapper;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.edu.request.TeacherConditionVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-06-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void queryTeacherPageByCondition(Page<EduTeacher> eduTeacherPage, TeacherConditionVO teacherConditionVO) {
        //创建条件对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //获取从controller传递来的值
        String name = teacherConditionVO.getName();
        Integer level = teacherConditionVO.getLevel();
        String beginTime = teacherConditionVO.getBeginTime();
        String endTime = teacherConditionVO.getEndTime();
        //判断四个值是否可用
        if (StringUtils.isNotEmpty(name)) {
            wrapper.like("name",name);
        }
        if (level!=null) {
            wrapper.eq("level", level);
        }
        if (StringUtils.isNotEmpty(beginTime)){
            wrapper.ge("gmt_create",beginTime);
        }
        if (StringUtils.isNotEmpty(endTime)) {
            wrapper.le("gmt_create",endTime);
        }
        wrapper.orderByDesc("gmt_create",teacherConditionVO.getBeginTime());
        //根据条件对象执行分页查询
        this.baseMapper.selectPage(eduTeacherPage,wrapper);
    }
}
