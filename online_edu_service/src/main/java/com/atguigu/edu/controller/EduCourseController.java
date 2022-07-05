package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.request.CourseCondition;
import com.atguigu.edu.request.CourseInfoVo;
import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author wang
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //1.保存课程信息
    @PostMapping("saveCourseInfo")
    public RetVal saveCourseInfo(CourseInfoVo courseInfoVO){
        courseService.saveCourseInfo(courseInfoVO);
        return RetVal.success();
    }

    //2.分页查询课程信息带条件
    @GetMapping("queryCoursePageByCondition/{pageNum}/{pageSize}")
    public RetVal queryCoursePageByCondition(
            @PathVariable("pageNum") long pageNum,
            @PathVariable("pageSize") long pageSize,
            CourseCondition courseCondition) {
        Page<EduCourse> coursePage = new Page<>(pageNum, pageSize);
        courseService.queryCoursePageByCondition(coursePage, courseCondition);
        long total = coursePage.getTotal();
        List<EduCourse> courseList = coursePage.getRecords();
        return RetVal.success().data("total", total).data("courseList", courseList);
    }

    //3.根据课程id查询课程信息
    @GetMapping("{id}")
    public RetVal getCourseById(@PathVariable String id){
        CourseInfoVo courseInfoVo=courseService.getCourseById(id);
        return RetVal.success().data("courseInfoVo",courseInfoVo);
    }

    //4.执行修改课程
    @PutMapping("updateCourseInfo")
    public RetVal updateCourseInfo(CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return RetVal.success();
    }

}

