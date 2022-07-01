package com.atguigu.edu.service;

import com.atguigu.edu.response.RetVal;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    RetVal uploadFile(MultipartFile multipartFile) throws Exception;

    boolean deleteSingleFile(String fileName);

}
