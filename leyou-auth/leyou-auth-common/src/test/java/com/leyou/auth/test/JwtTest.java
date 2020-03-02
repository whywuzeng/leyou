package com.leyou.auth.test;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;

import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by Administrator on 2020/3/2.
 * <p>
 * by author wz
 * <p>
 * com.leyou.auth.test
 */

public class JwtTest {

    private static final String pubKeyPath = "C:\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "C:\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath,priKeyPath,"secret");
    }

    @Before
    public void testGetRsa() throws Exception {
        publicKey = RsaUtils.getPublicKey(pubKeyPath);
        privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);

        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU4MzU2MzMwNX0.W17kSAlXoo7TLmQ8ucZMagP7Kgv91-CZLDbgtmdZDhP2yZG9ipaVFuxzCWMS7vBgu3SsHwbcXuvDy9KTjGtGkUwKrmn8WRGPmA9Ng6PJeau503E4DD6I2iUt4c8peMidrR5bxWH9TLgiJPxDRkWNEa-nduU-n6OpRI9X2LkHk5c";

        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id:"+user.getId());
        System.out.println("userName"+user.getUsername());
    }
}
