package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Administrator on 2020/1/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou
 */

@SpringBootApplication
@EnableDiscoveryClient
public class LyUploadService {
    public static void main(String[] args) {
        SpringApplication.run(LyUploadService.class,args);
    }
}
