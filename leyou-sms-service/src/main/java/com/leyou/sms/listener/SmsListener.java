package com.leyou.sms.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.leyou.sms.properties.SmsProperties;
import com.leyou.sms.utils.SmsUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2020/2/28.
 * <p>
 * by author wz
 * <p>
 * com.leyou.sms.listener
 */
@EnableConfigurationProperties({SmsProperties.class})
@Component
public class SmsListener {

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private SmsUtils smsUtils;

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(durable = "true", value = "sms-listener-queue"),
                    exchange = @Exchange(value = "sms-listener-exchange", ignoreDeclarationExceptions = "true"),
                    key = "sms.verify.code"))
    public void smsListener(Map<String, String> map)  {

        if (map == null || map.size() < 1) {
            return;
        }

        String phoneNum = map.get("phone");
        String code = map.get("code");

        if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(code)) {
            // 放弃处理
            return;
        }

        //发送消息
        try {
            SendSmsResponse sendSmsResponse = this.smsUtils.sendSms(phoneNum, code,
                    smsProperties.getVerifyCodeTemplate(),
                    smsProperties.getSignName());
        } catch (ClientException e) {
            return;
        }

    }
}
