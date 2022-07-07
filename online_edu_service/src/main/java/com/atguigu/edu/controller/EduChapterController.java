package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.exception.MyException1;
import com.atguigu.edu.response.ChapterVO;
import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器   /edu/chapter
 * </p>
 *
 * @author wang
 * @since 2022-07-05
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    //1.查询所有章节小节信息
    @GetMapping("getChapterAndSection/{courseId}")
    public RetVal getChapterAndSection(@PathVariable String courseId){
        List<ChapterVO> chapterAndSectionList= eduChapterService.getChapterAndSection(courseId);
        return RetVal.success().data("chapterAndSectionList",chapterAndSectionList);
    }

    //2.添加章节
    @PostMapping
    public RetVal saveChapter(EduChapter chapter){
        boolean flag=eduChapterService.saveChapter(chapter);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
    //3.根据章节id查询章节信息
    @GetMapping("getChapter/{id}")
    public RetVal getChapter(@PathVariable String id){
        EduChapter chapter = eduChapterService.getById(id);
        return RetVal.success().data("chapter",chapter);
    }
    //4.更新章节信息
    @PutMapping
    public RetVal updateChapter(EduChapter chapter){
        boolean flag = eduChapterService.updateById(chapter);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
    //5.删除章节信息
    @DeleteMapping("{chapterId}")
    public RetVal deleteChapter(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }

}

