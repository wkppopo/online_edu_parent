package com.atguigu.edu.handler;

import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.videoFeignservice.VideoFeignService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class VideoServiceHandler implements VideoFeignService {
    @Override
    public RetVal uploadAliyunVideo(MultipartFile file) {
        return RetVal.error().data("uploadError","上传失败-兜底方法");
    }

    @Override
    public RetVal deleteSingleVideo(String videoId) {
        return RetVal.error().data("deleteError","删除单个视频-失败兜底方法");
    }

    @Override
    public RetVal deleteMultiVideo(List<String> videoIdList) {
        return RetVal.error().data("deleteMultiError","删除多个视频-失败兜底方法");
    }
}
