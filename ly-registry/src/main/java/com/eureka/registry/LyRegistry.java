package com.eureka.registry;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by Administrator on 2020/1/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou.parent.leyou
 */
@SpringBootApplication
@EnableEurekaServer
public class LyRegistry {
    public static void main(String[] args)
    {
        SpringApplication.run(LyRegistry.class,args);
    }
}
