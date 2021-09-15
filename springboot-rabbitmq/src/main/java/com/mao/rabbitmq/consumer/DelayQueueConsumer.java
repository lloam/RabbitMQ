package com.mao.rabbitmq.consumer;

import com.mao.rabbitmq.config.DelayedQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author: lloam
 * Date: 2021/9/12 17:27
 * Description:
 * 延迟队列 消费者
 * 基于插件的延迟消息
 */
@Component
@Slf4j
public class DelayQueueConsumer {

    // 监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("当前时间：{}，接收到延迟队列的消息：{}",
                new Date().toString(), msg);
    }
}
