package com.leyou.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by Administrator on 2020/2/28.
 * <p>
 * by author wz
 * <p>
 * com.leyou.user.service
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.user.service.mapper")
public class LYUserServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(LYUserServiceApp.class,args);
    }
}
