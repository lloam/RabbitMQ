package com.mao.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: lloam
 * Date: 2021/9/14 11:37
 * Description: 发布确认高级篇
 * 配置类，声明与绑定交换机
 */
@Configuration
public class ConfirmConfig {
    // 交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    // 队列
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    // RoutingKey
    public static final String CONFIRM_ROUTING_KEY = "key1";

    // 声明交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    // 声明队列
    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }
    // 绑定
    @Bean
    public Binding queueBindingToExchange(
            @Qualifier("confirmExchange") DirectExchange confirmExchange,
            @Qualifier("confirmQueue") Queue confirmQueue
    ) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }
}
