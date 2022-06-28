package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.service.EduTeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class EduTeacherServiceImplTest {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Test
    public void test(){
        System.out.println(eduTeacherService);
        List<EduTeacher> list = eduTeacherService.list(null);
        for (EduTeacher eduTeacher : list) {
            System.out.println(eduTeacher);
        }
    }

}