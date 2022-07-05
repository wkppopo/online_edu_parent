package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.edu.request.TeacherConditionVO;
import com.atguigu.edu.response.RetVal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wang
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin //开启跨域
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询所有
    @GetMapping
    public RetVal getTeacherList(){
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return RetVal.success().data("teacherList",teacherList);
    }

    //removeById 逻辑删除
    @DeleteMapping("{id}")
    public RetVal removeById(@PathVariable String id) {
        eduTeacherService.removeById(id);
        new RetVal().data("sf",55).setCode(11).setMessage("asdf ");
        return RetVal.success();
    }

    //分页查询 无条件
    @GetMapping("/{num}/{size}")
    public RetVal getPage(@PathVariable Long num,@PathVariable Long size) {
        Page<EduTeacher> eduTeacherPage = new Page<>(num,size);

        eduTeacherService.page(eduTeacherPage, null);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return RetVal.success().data("teacherList",records).data("total",eduTeacherPage.getTotal());
    }

    //分页查询 带条件
    @GetMapping("/queryTeacherPageByCondition/{num}/{size}")
    public RetVal getPageByWrapper(@PathVariable Long num, @PathVariable Long size, TeacherConditionVO teacherConditionVO){
        Page<EduTeacher> eduTeacherPage = new Page<>(num,size);
        eduTeacherService.queryTeacherPageByCondition(eduTeacherPage,teacherConditionVO);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return RetVal.success().data("teacherList",records).data("total",eduTeacherPage.getTotal());
    }

    //添加讲师
    @PostMapping
    public RetVal saveTeacher(EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    //修改讲师
    @PutMapping()
    public RetVal modifyTeacherById(EduTeacher eduTeacher){
        boolean save = eduTeacherService.updateById(eduTeacher);
        if (save) {
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
    //根据Id查询讲师
    @GetMapping("{id}")
    public RetVal getTeacherById(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return RetVal.success().data("teacher",byId);
    }

}

