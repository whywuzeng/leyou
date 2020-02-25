package com.leyou;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by Administrator on 2020/1/22.
 * <p>
 * by author wz
 * <p>
 * com.leyou
 */
@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        //1.添加corsconfiguration
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许的域
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
         //是否发送cookie 信息
        corsConfiguration.setAllowCredentials(false);

        //允许的请求方法
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("POST");

        // 4）允许的头信息
        corsConfiguration.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(configSource);
    }
}
