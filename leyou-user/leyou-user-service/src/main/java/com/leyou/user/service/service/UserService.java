package com.leyou.user.service.service;

import com.leyou.user.pojo.User;
import com.leyou.user.service.mapper.UserMapper;
import com.leyou.utils.CodecUtils;
import com.leyou.utils.NumberUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/2/28.
 * <p>
 * by author wz
 * <p>
 * com.leyou.user.service.service
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    static final String KEY_PREFIX = "user:code:phone";

    static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /**
     * 检查是否存在
     *
     * @param data
     * @param type
     * @return
     */
    public Boolean checkData(String data, Integer type) {

        User record = new User();

        switch (type) {
            case 1:
                record.setUsername(data);
                break;

            case 2:
                record.setPhone(data);
                break;
        }
        //是否存在
        return userMapper.selectCount(record) == 0;
    }

    public Boolean sendVerifyCode(String phone) {

        //生成
        String code = NumberUtils.generateCode(6);

        try {
            HashMap<String, String> map = new HashMap<>();

            map.put("phone", phone);
            map.put("code", code);

//        exchange = @Exchange(value = "sms-listener-exchange", ignoreDeclarationExceptions = "true"),
//        key = "sms.verify.code")

            this.amqpTemplate.convertAndSend("sms-listener-exchange", "sms.verify.code", map);

            redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);

            return true;
        } catch (AmqpException e) {
            e.printStackTrace();
            LOGGER.error("发送短信失败,phone:{},code{}", phone, code);
            return false;
        }

    }


    public Boolean registerUser(String username, String password, String phone, String code) {
        String key = KEY_PREFIX + phone;
        String codeCache = this.redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(codeCache)) {
            return false;
        }
        if (!code.equals(codeCache)) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPhone(phone);

        user.setId(null);
        user.setCreated(new Date());
        //1f8fe7059d1a86060f3a82bfcf2ea06e
        String encodePassword = CodecUtils.passwordBcryptEncode("", password.trim());

        user.setPassword(encodePassword);

        boolean isSuccessful = this.userMapper.insertSelective(user) == 1;
        if (isSuccessful) {
            try {
                this.redisTemplate.delete(key);
            } catch (Exception e) {
                LOGGER.error("删除缓存验证码失败，code:{}", code, e);
            }
        }
        return isSuccessful;
    }

    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        if (user == null) {
            return null;
        }
        if (!StringUtils.isBlank(user.getPassword())&& CodecUtils.passwordConfirm(password, user.getPassword()))
        {
            return  user;
        }
        return null;
    }
}
