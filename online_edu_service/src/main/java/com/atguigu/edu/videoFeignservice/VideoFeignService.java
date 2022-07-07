package com.atguigu.edu.videoFeignservice;

import com.atguigu.edu.handler.VideoServiceHandler;
import com.atguigu.edu.response.RetVal;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 远程调用的方法一定要带上被调用类的完整路径名 /aliyun
 */
@Service
@FeignClient(value = "EDU-VIDEO",fallback = VideoServiceHandler.class)
public interface VideoFeignService {
    //1.视频上传
    @PostMapping("/aliyun/uploadAliyunVideo")
    public RetVal uploadAliyunVideo(MultipartFile file);

    //删除单个视频
    @DeleteMapping("/aliyun/deleteSingleVideo/{videoId}")
    public RetVal deleteSingleVideo(@PathVariable String videoId);

    //删除多个视频
    @DeleteMapping("/aliyun/deleteMultiVideo")
    public RetVal deleteMultiVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
