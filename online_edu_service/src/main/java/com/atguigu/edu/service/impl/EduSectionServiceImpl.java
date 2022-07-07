package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduSection;
import com.atguigu.edu.exception.MyException1;
import com.atguigu.edu.mapper.EduSectionMapper;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.edu.videoFeignservice.VideoFeignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程小节 服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-07-05
 */
@Service
public class EduSectionServiceImpl extends ServiceImpl<EduSectionMapper, EduSection> implements EduSectionService {

    @Autowired
    private VideoFeignService videoFeignService;

    @Override
    public void addSection(EduSection section) {
        //1.判断是否存在小节
        QueryWrapper<EduSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",section.getCourseId());
        queryWrapper.eq("chapter_id",section.getChapterId());
        queryWrapper.eq("title",section.getTitle());
        EduSection existSection = baseMapper.selectOne(queryWrapper);
        if(existSection==null){
            baseMapper.insert(section);
        }else{
            throw new MyException1(20001,"存在重复的小节");
        }
    }

    //根据小节id删除小节视频
    @Override
    public void deleteSection(String id) {
        EduSection eduSection = baseMapper.selectById(id);
        String videoSourceId = eduSection.getVideoSourceId();
        if (StringUtils.isNotEmpty(videoSourceId)) {
            // 远程调用Video服务 删除视频
            videoFeignService.deleteSingleVideo(videoSourceId);

        }
        //最后删除小节信息
        baseMapper.deleteById(id);
    }

    /**
     *  根据课程id删除所有的小节和视频
     */
    @Override
    public void deleteSectionByCourseId(String courseId) {
        QueryWrapper<EduSection> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        //根据上面的wrapper条件查询所有的小节List
        List<EduSection> eduSectionList = baseMapper.selectList(wrapper);
        //创建一个新集合用来装videoID
        ArrayList<String> videoIdList = new ArrayList<>();
        //遍历section集合
        for (EduSection eduSection : eduSectionList) {
            String videoSourceId = eduSection.getVideoSourceId();
            if (StringUtils.isNotEmpty(videoSourceId)) {
                videoIdList.add(videoSourceId);
            }
        }
        // 调用微服务接口 批量删除所有的视频
        videoFeignService.deleteMultiVideo(videoIdList);

        baseMapper.delete(wrapper);
    }
}

