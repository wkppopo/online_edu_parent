package com.atguigu.edu;


import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;

public class DeleteVideoTest {
    private static final String accessKeyId="LTAI5tRz4K1qcNiygSS2i9nY";
    private static final String accessKeySecret="GdRMAGUnMF5T7kpTQYjTG5D6otY7vM";

    /**
     * 删除视频
     * @param client 发送请求客户端
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    public static DeleteVideoResponse deleteVideo(DefaultAcsClient client) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds("03040e192e7f483eb10b18ef2f12ed66,d6a1742672f64861bfa7648741187263");
        return client.getAcsResponse(request);
    }

    /*请求示例*/
    public static void main(String[] argv) {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            response = deleteVideo(client);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    //填入AccessKey信息 初始化 创建客户端
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
