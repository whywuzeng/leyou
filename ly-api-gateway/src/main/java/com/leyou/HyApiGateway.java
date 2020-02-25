package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Administrator on 2020/1/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class HyApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(HyApiGateway.class);
    }
}
