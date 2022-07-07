package com.atguigu.edu.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.edu.service.VideoService;
import com.atguigu.edu.utils.VideoInitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoServiceImpl implements VideoService {
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;


    /**
     *  文件流式上传
     *  返回：VideoId
     */
    @Override
    public String uploadAliyunVideo(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        String title=fileName.substring(0,fileName.lastIndexOf("."));

        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        if (response.isSuccess()) {
            return response.getVideoId();
        } else {
            throw new RuntimeException(response.getCode()+""+response.getMessage());
        }
    }

    //删除单个视频
    @Override
    public boolean deleteSingleVideo(String videoId) throws ClientException {
        DefaultAcsClient client = VideoInitClient.initVodClient(accessKeyId, accessKeySecret);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoId);
        DeleteVideoResponse response = client.getAcsResponse(request);
        return true;
    }


}
