package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.response.ChapterVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wang
 * @since 2022-07-05
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterAndSection(String courseId);

    boolean saveChapter(EduChapter chapter);

    boolean deleteChapter(String chapterId);

    void deleteChapterByCourseId(String courseId);

}
