package com.leyou.auth.controller;

import com.leyou.auth.properties.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.utils.CookieUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2020/3/2.
 * <p>
 * by author wz
 * <p>
 * com.leyou.auth.controller
 */
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties properties;


    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response){
        String token = this.authService.authentication(username,password);

        if (StringUtils.isBlank(token))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        CookieUtils.setCookie(request,response,properties.getCookieName(),token,properties.getCookieMaxAge(),true);
        return ResponseEntity.ok().build();
    }
}
