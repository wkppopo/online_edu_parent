package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.response.SubjectVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author wang
 * @since 2022-07-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    EduSubject existSubject(String parentSubjectName, String s);

    //查询所有课程
    List<SubjectVO> getAllSubject();

    Boolean uploadSubject(MultipartFile file) throws IOException;
}
