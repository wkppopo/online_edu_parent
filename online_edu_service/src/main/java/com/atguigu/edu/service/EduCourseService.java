package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.request.CourseCondition;
import com.atguigu.edu.request.CourseInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wang
 * @since 2022-07-04
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfo(CourseInfoVo courseInfoVO);

    void queryCoursePageByCondition(Page<EduCourse> coursePage, CourseCondition courseCondition);

    CourseInfoVo getCourseById(String id);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

}
