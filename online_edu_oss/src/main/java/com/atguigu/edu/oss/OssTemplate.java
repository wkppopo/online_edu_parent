package com.atguigu.edu.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.PutObjectRequest;
import com.atguigu.edu.response.RetVal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class OssTemplate {
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;

    //上传单个文件
    public RetVal uploadSingleFile(InputStream inputStream, String fileName) {
        OSS ossClient = new OSSClientBuilder().build("https://"+endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(new PutObjectRequest(bucketName, fileName, inputStream));
        ossClient.shutdown();
        //http://online-oss-220217.oss-cn-huhehaote.aliyuncs.com/1.png
        String urlName="http://"+bucketName+"."+endpoint+"/"+fileName;
        return RetVal.success().data("retVal",urlName);
    }

    //删除单个文件
    public boolean deleteSingleFile(String fileName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileName);
        ossClient.shutdown();
        return true;
    }
    //批量删除文件
    public RetVal deleteMultiFiles(List<String> keys){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys).withEncodingType("url"));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        try {
            for(String obj : deletedObjects) {
                String deleteObj =  URLDecoder.decode(obj, "UTF-8");
                System.out.println(deleteObj);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ossClient.shutdown();
        return RetVal.success();
    }

}
