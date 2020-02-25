package com.leyou.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by Administrator on 2020/1/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.item.mapper")
public class LyItemService {

    public static void main(String[] args) {
        SpringApplication.run(LyItemService.class);
    }
}
