package com.leyou.user.pojo.api;

import com.leyou.user.pojo.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2020/3/2.
 * <p>
 * by author wz
 * <p>
 * com.leyou.user.pojo.api
 */

public interface UserServiceApi  {
    //验证用户是否存在，并返回user
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password);
}
