package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.response.SubjectVO;
import com.atguigu.edu.service1.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wang
 * @since 2022-07-01
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //上传excel /edu/subject/uploadSubject
    @PostMapping("/uploadSubject")
    public RetVal uploadSubject(MultipartFile file) throws IOException {
        Boolean flag=eduSubjectService.uploadSubject(file);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    @GetMapping("/getAllSubject")
    public RetVal getAllSubject(){
        List<SubjectVO> listVo=eduSubjectService.getAllSubject();
        return RetVal.success().data("allSubject",listVo);
    }
    //删除节点信息
    @DeleteMapping("/{id}")
    public RetVal deleteSubjectById(@PathVariable String id){
        boolean flag=eduSubjectService.deleteSubjectById(id);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    //保存一级分类节点信息
    @PostMapping("/savParentSubject")
    public RetVal savParentSubject(EduSubject eduSubject){
        boolean flag=eduSubjectService.savParentSubject(eduSubject);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    //保存二级分类节点信息
    @PostMapping("/savChildSubject")
    public RetVal savChildSubject(EduSubject eduSubject){
        boolean flag=eduSubjectService.savChildSubject(eduSubject);
        if (flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
}

