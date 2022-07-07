package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.response.CourseConfirmVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2022-07-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CourseConfirmVO queryCourseConfirmInfo(String courseId);
}
