package com.leyou.auth.properties;

import com.leyou.auth.utils.RsaUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2020/3/2.
 * <p>
 * by author wz
 * <p>
 * com.leyou.auth.properties
 */
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties {
    private String secret;
    private String pubKeyPath;
    private String priKeyPath;
    private int expire;
    private String cookieName;
    private int cookieMaxAge;


    private PublicKey publicKey;

    private PrivateKey privateKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProperties.class);

    //在构造方法之前调用
    @PostConstruct
    public void init(){
        File pubkey = new File(pubKeyPath);
        File prikey = new File(priKeyPath);
        try {
            if (!pubkey.exists()||!prikey.exists())
            {
                RsaUtils.generateKey(pubKeyPath,priKeyPath,secret);
            }

            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey =  RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            LOGGER.error("公钥密钥初始化失败!",e);
            throw new RuntimeException();
        }
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public int getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getPriKeyPath() {
        return priKeyPath;
    }

    public void setPriKeyPath(String priKeyPath) {
        this.priKeyPath = priKeyPath;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
