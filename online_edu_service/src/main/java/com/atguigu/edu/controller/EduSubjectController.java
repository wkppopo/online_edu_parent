package com.atguigu.edu.controller;


import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.response.SubjectVO;
import com.atguigu.edu.service.EduSubjectService;
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
}

