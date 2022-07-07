package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduCourseDescription;
import com.atguigu.edu.mapper.EduCourseMapper;
import com.atguigu.edu.request.CourseCondition;
import com.atguigu.edu.request.CourseInfoVo;
import com.atguigu.edu.response.CourseConfirmVO;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduCourseDescriptionService;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.service.EduSectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-07-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduSectionService eduSectionService;


    //1.保存课程信息
    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVO) {
        //保存课程信息
        EduCourse eduCourse = new EduCourse();
        if (courseInfoVO.getChildSubjectId()==null){
            courseInfoVO.setChildSubjectId("");
        }
        BeanUtils.copyProperties(courseInfoVO,eduCourse);
        baseMapper.insert(eduCourse);

        //保存课程的描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());

        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);
        return eduCourse.getId();
    }

    //2.带条件分页查询
    @Override
    public void queryCoursePageByCondition(Page<EduCourse> coursePage, CourseCondition courseCondition) {
        //获取每个查询参数
        String title = courseCondition.getTitle();
        String status = courseCondition.getStatus();
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断以上传递过来的参数是否为空
        if(StringUtils.isNotEmpty(title)){
            wrapper.like("title",title);
        }
        if(StringUtils.isNotEmpty(status)){
            wrapper.ge("status",status);
        }
        baseMapper.selectPage(coursePage,wrapper);
    }

    //根据id查询课程信息一级描述信息
    @Override
    public CourseInfoVo getCourseById(String id) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(id);
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        EduCourseDescription byId = eduCourseDescriptionService.getById(id);
        if (byId!=null) {
            courseInfoVo.setDescription(byId.getDescription());
        }
        return courseInfoVo;
    }
    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        baseMapper.updateById(eduCourse);
        //修改课程描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    ///查询课程确认封面信息表
    @Override
    public CourseConfirmVO queryCourseConfirmInfo(String courseId) {
        return baseMapper.queryCourseConfirmInfo(courseId);
    }

    /**
     *  像这种在一个方法中操作多个业务对象的场景，**需要添加事务**
     */
    //删除课程信息
    @Override
    public void deleteCourseById(String courseId) {
        //a.该课程所对应的所有章节
        eduChapterService.deleteChapterByCourseId(courseId);
        //b.该课程所对应的所有小节
        eduSectionService.deleteSectionByCourseId(courseId);
        //c.该课程的基本信息
        baseMapper.deleteById(courseId);
        //c.该课程的描述信息
        eduCourseDescriptionService.removeById(courseId);
        //d.整个过程要保证完整性(分布式事务)
    }

}
