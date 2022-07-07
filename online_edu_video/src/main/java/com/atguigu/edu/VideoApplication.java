package com.atguigu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient //开启服务发现
@EnableSwagger2
@SpringBootApplication
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class,args);
    }
}
