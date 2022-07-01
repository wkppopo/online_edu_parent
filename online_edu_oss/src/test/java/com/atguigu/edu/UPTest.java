package com.atguigu.edu;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UPTest {
    //单个文件上传
    @Test
    public void test() {
        String endpoint = "https://oss-cn-huhehaote.aliyuncs.com";
        String accessKeyId = "LTAI5tRz4K1qcNiygSS2i9nY";
        String accessKeySecret = "GdRMAGUnMF5T7kpTQYjTG5D6otY7vM";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "online-oss-220217";
        // 如果未指定本地路径
        String filePath = "C:\\Users\\22196\\Desktop\\生命周期.png";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "1.png", new File(filePath));
        // 上传文件。
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
    }
    //删除单个文件
    @Test
    public void test2() {
        String endpoint = "https://oss-cn-huhehaote.aliyuncs.com";
        String accessKeyId = "LTAI5tRz4K1qcNiygSS2i9nY";
        String accessKeySecret = "GdRMAGUnMF5T7kpTQYjTG5D6otY7vM";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "online-oss-220217";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //***********
        ossClient.deleteObject(bucketName, "1.png");
        ossClient.shutdown();

    }
    //批量删除文件
    @Test
    public void test3() {
        String endpoint = "https://oss-cn-huhehaote.aliyuncs.com";
        String accessKeyId = "LTAI5tRz4K1qcNiygSS2i9nY";
        String accessKeySecret = "GdRMAGUnMF5T7kpTQYjTG5D6otY7vM";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "online-oss-220217";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //***********
        // 删除文件。
        // 填写需要删除的多个文件完整路径。文件完整路径中不能包含Bucket名称。
        List<String> keys = new ArrayList<String>();
        keys.add("1.png");
        keys.add("2.png");

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

    }
}
