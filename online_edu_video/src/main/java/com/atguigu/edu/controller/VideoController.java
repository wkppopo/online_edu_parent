package com.atguigu.edu.controller;

import com.aliyuncs.exceptions.ClientException;
import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/aliyun")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;

    //1.视频上传
    @PostMapping("uploadAliyunVideo")
    public RetVal uploadAliyunVideo(MultipartFile file) throws Exception {
        String videoId = videoService.uploadAliyunVideo(file);
        return RetVal.success().data("videoId", videoId);
    }

    //删除单个视频
    @DeleteMapping("/deleteSingleVideo/{videoId}")
    public RetVal deleteSingleVideo(@PathVariable String videoId) throws ClientException {
        boolean flag=videoService.deleteSingleVideo(videoId);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    //删除多个视频
    @DeleteMapping("/deleteMultiVideo")
    public RetVal deleteMultiVideo(@RequestParam("videoIdList")List<String> videoIdList) throws ClientException {
        String join = StringUtils.join(videoIdList,",");
        boolean flag=videoService.deleteSingleVideo(join);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

}