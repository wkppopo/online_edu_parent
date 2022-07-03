package com.atguigu.edu.service1.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.entity.SubjectExcel;
import com.atguigu.edu.exception.MyException1;
import com.atguigu.edu.listener.SubjectListener;
import com.atguigu.edu.mapper.EduSubjectMapper;
import com.atguigu.edu.response.SubjectVO;
import com.atguigu.edu.service1.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    @Resource
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

    @Override
    public boolean deleteSubjectById(String id) {
        //删除节点，需要判断是否有子节点
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer integer = baseMapper.selectCount(wrapper);
        if (integer==0) {
            //如果查询到的数量为0，表示它没有子节点
            return baseMapper.deleteById(id)>0;
        }else{
            throw new MyException1(20001,"该节点存在子节点");
        }
    }

    /**
     *  保存二级分类信息
     */
    @Override
    public boolean savChildSubject(EduSubject eduSubject) {
        //判断二级分类是否存在
        EduSubject childSubject= existSubject(eduSubject.getTitle(),eduSubject.getParentId());
        if (childSubject==null) {
            return baseMapper.insert(eduSubject)>0;
        }
        return false;
    }

    /**
     * 保存一级分类信息
     */
    @Override
    public boolean savParentSubject(EduSubject eduSubject) {
        //判断该一级分类是否存在
        EduSubject parentSubject= existSubject(eduSubject.getTitle(),"0");
        if (parentSubject==null) {
            parentSubject=new EduSubject();
            parentSubject.setTitle(eduSubject.getTitle());
            parentSubject.setParentId("0");
            return baseMapper.insert(parentSubject)>0;
        }
        return false;
    }

}
