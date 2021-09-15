package com.mao.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author: lloam
 * Date: 2021/9/14 17:13
 * Description:
 */
@Component
@Slf4j
public class MyCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 接口是 RabbitTemplate 的内部接口，需要注入才能使用
     */
    @PostConstruct
    public void init() {
        // 注入
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }


    /**
     * 交换机确认回调方法
     * 1、发消息    交换机接收到了 回调
     *  1.1 correlationData 保存回调消息的 ID 及相关信息
     *  1.2 交换机收到消息     ack = true
     *  1.3 cause   null
     * 2.发消息    交换机接受失败了    回调
     *  2.1 correlationData 保存回调消息的 ID 及相关信息
     *  2.2 交换机没有收到消息   ack = false
     *  2.3 cause   失败的原因
     * @param correlationData
     * @param ack
     * @param cause
     */
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机已经收到 Id 为 {} 的消息",id);
        }else {
            log.info("交换机还未收到 Id 为 {} 的消息，原因是 {}", id, cause);
        }
    }


    /**
     * 可以在当消息传递过程中不可达目的地时将消息返回给生产者
     * 只有不可达目的地的时候  才进行回退
     * @param returned
     */
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息 {} ，被交换机 {} 退回，退回的原因是：{}，路由 key：{}",returned.getMessage(), returned.getExchange(),returned.getReplyText(),returned.getRoutingKey());
    }
}
