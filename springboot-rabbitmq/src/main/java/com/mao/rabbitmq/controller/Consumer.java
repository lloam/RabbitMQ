package com.mao.rabbitmq.controller;

import com.mao.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * Author: lloam
 * Date: 2021/9/14 16:31
 * Description:
 * 消费者
 */
@Configuration
@Slf4j
public class Consumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void consumer(Message message) {
        String msg = new String(message.getBody());
        log.info("收到来自 confirm.queue 队列的消息：{}",msg);
    }
}
