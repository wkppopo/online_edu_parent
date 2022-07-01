package com.atguigu.edu.service.impl;

import com.atguigu.edu.oss.OssTemplate;
import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private OssTemplate ossTemplate;

    //实现 上传单个文件
    @Override
    public RetVal uploadFile(MultipartFile multipartFile) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        //拼接上传文件的文件名
        String prefix= UUID.randomUUID().toString().replace("-","");
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName=prefix+suffix;
        return ossTemplate.uploadSingleFile(inputStream,fileName);
    }

    //实现 删除单个文件
    @Override
    public boolean deleteSingleFile(String fileName) {
        return ossTemplate.deleteSingleFile(fileName);
    }

}
