package com.atguigu.edu.controller;

import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/uploadFile")
    public RetVal uploadFile(MultipartFile file) throws Exception {
        return ossService.uploadFile(file);
    }

    @DeleteMapping("/deleteSingleFile")
    public RetVal deleteSingleFile(String fileName){
        if (ossService.deleteSingleFile(fileName)){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
}
