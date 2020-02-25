package com.leyou.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by Administrator on 2020/2/17.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LySearchService {
    public static void main(String[] args) {
        SpringApplication.run(LySearchService.class,args);
    }
}
