package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.entity.SubjectExcel;
import com.atguigu.edu.listener.SubjectListener;
import com.atguigu.edu.mapper.EduSubjectMapper;
import com.atguigu.edu.response.SubjectVO;
import com.atguigu.edu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    /**
     * 当SubjectListener 类中的service使用了依赖注入后，下面subjectListener也必须使用依赖注入，否则空指针异常
     */
    @Autowired
    private SubjectListener subjectListener;

    //excelListener用到
    @Override
    public EduSubject existSubject(String parentSubjectName, String id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",parentSubjectName);
        wrapper.eq("parent_id",id);
        return baseMapper.selectOne(wrapper);
    }

    //获取所有的课程数据并转化成vo
    @Override
    public List<SubjectVO> getAllSubject() {
        //查询所有的集合 All
        List<EduSubject> eduAllSubjects = baseMapper.selectList(null);
        //创建一级结构化集合，最终返回
        ArrayList<SubjectVO> parentSubjects = new ArrayList<>();
        //eduAllSubjects遍历 获取一级数据
        for (EduSubject eduAllSubject : eduAllSubjects) {
            if (eduAllSubject.getParentId().equals("0")) {
                //parent_id=0 说明是一级数据
                SubjectVO parentVo = new SubjectVO();
                parentVo.setId(eduAllSubject.getId());
                parentVo.setTitle(eduAllSubject.getTitle());
                parentSubjects.add(parentVo);
            }
        }
        //创建一个hashMap 用来装一级数据的id和title
        HashMap<String, SubjectVO> map = new HashMap<>();
        for (SubjectVO parentSubject : parentSubjects) {
            map.put(parentSubject.getId(),parentSubject);
        }
        //遍历所有集合过滤出来所有的二级数据，然后根据hashMap中的键值对找对应的组
        for (EduSubject children : eduAllSubjects) {
            if (!children.getParentId().equals("0")) {
                //父id不等于0 则是二级
                SubjectVO childrenVo = new SubjectVO();
                childrenVo.setId(children.getId());
                childrenVo.setTitle(children.getTitle());
                SubjectVO subjectVO = map.get(children.getParentId());
                subjectVO.getChildren().add(childrenVo);
            }
        }
        return parentSubjects;
    }

    //上传excel的实现
    @Override
    public Boolean uploadSubject(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        //subjectListener ：必须使用依赖注入
        EasyExcel.read(inputStream, SubjectExcel.class,subjectListener).doReadAll();
        return true;
    }
}
