package com.leyou.auth.client;

import com.leyou.user.pojo.api.UserServiceApi;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Administrator on 2020/3/2.
 * <p>
 * by author wz
 * <p>
 * com.leyou.auth.client
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserServiceApi {
}
