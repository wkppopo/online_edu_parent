package com.atguigu.edu.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService {
    String uploadAliyunVideo(MultipartFile file) throws IOException;

    boolean deleteSingleVideo(String videoId) throws ClientException;
}
