package com.atguigu.edu;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadURLStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadURLStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class ReadVideoTest {
    private static final String accessKeyId="LTAI5tRz4K1qcNiygSS2i9nY";
    private static final String accessKeySecret="GdRMAGUnMF5T7kpTQYjTG5D6otY7vM";

    /**
     * 视频点播服务 步骤：需要的参数只要有三个
     *              1. 初始化服务
     *              2.获取视频播放地址
     */
    //填入AccessKey信息 初始化 创建客户端
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        //通过视频id获取播放地址
        request.setVideoId("d6a1742672f64861bfa7648741187263");
        return client.getAcsResponse(request);
    }

    /*以下为调用示例*/
    public static void main(String[] argv) {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            //从客户端中 获取播放地址
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址 遍历
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
//************************************************************************************
    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("d6a1742672f64861bfa7648741187263");
        return client.getAcsResponse(request);
    }

    /*以下为调用示例*/
//    public static void main(String[] argv) {
//        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
//        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
//        try {
//            response = getVideoPlayAuth(client);
//            //播放凭证
//            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
//            //VideoMeta信息
//            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
//        } catch (Exception e) {
//            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
//        }
//        System.out.print("RequestId = " + response.getRequestId() + "\n");
//    }

}
