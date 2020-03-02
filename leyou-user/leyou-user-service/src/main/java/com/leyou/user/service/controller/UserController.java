package com.leyou.user.service.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.service.UserService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2020/2/28.
 * <p>
 * by author wz
 * <p>
 * com.leyou.user.service.controller
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验参数是否有用
     *
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data, @PathVariable(value = "type", required = false) Integer type) {

        if (type == null) {
            //默认为 1，用户名
            type = 1;
        }

        Boolean isUseful = this.userService.checkData(data, type);

        if (isUseful == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(isUseful);
    }

    @PostMapping("code")
    public ResponseEntity<Boolean> sendVerifyCode(@RequestParam(name = "phone") String phone) {
        if (StringUtils.isBlank(phone)) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        Boolean boo = this.userService.sendVerifyCode(phone);
        if (boo == null && !boo) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(boo);
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("code") String code) {


        Boolean boo = this.userService.registerUser(username,password,phone,code);
        if (boo == null || !boo) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password){
        User user = this.userService.queryUser(username,password);
        if (user == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(user);
    }
}
