package com.leyou.auth;

import com.leyou.auth.properties.JwtProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by Administrator on 2020/3/2.
 * <p>
 * by author wz
 * <p>
 * com.leyou.auth
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableConfigurationProperties({JwtProperties.class})
public class LeyouAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouAuthApplication.class,args);
    }
}
