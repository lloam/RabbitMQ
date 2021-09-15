package com.mao.rabbitmq.controller;

import com.mao.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Author: lloam
 * Date: 2021/9/12 11:19
 * Description:
 */
@RestController
@RequestMapping("/ttl")
@Slf4j
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sengMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间：{}，发送一条消息给两个 TTL 队列：{}", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自 TTL 为 10 秒的队列" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自 TTL 为 40 秒的队列" + message);
    }

    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendExpirationMsg(
            @PathVariable String message,
            @PathVariable String ttlTime
    ) {
        log.info("当前时间：{}，发送一条时长为 {} 毫秒TTL信息给队列 QC：{}",
                new Date().toString(),
                ttlTime,
                message);
        rabbitTemplate.convertAndSend("X", "XC", "消息来自 TTL 为 "+ ttlTime +" 秒的队列 QC" + message, msg -> {
            // 发送消息的时候，延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    // 开始发消息 基于插件的 消息及延迟的时间
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer delayTime) {

        log.info("当前时间：{}，发送一条时长为 {} 毫秒的消息给延迟队列 delayed.queue {}",
                new Date().toString(), delayTime, message);

        rabbitTemplate.convertAndSend(
                DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_ROUTING_KEY,
                message,
                msg -> {
                    // 发送消息的时候  延迟时长    单位：ms
                    msg.getMessageProperties().setDelay(delayTime);
                    return msg;
                });
    }
}
