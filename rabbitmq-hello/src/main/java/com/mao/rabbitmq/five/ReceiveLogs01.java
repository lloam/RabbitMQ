package com.mao.rabbitmq.five;

import com.mao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author: lloam
 * Date: 2021/9/10 16:26
 * Description: 发布订阅模式
 * 消费者消费消息
 */
public class ReceiveLogs01 {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();

        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 声明一个临时队列
        /**
         * 生成一个临时队列、队列的名称是随机的
         * 当消费者断开与队列的链接的时候  队列就自动删除
         */
        String queueName = channel.queueDeclare().getQueue();

        /**
         * 绑定交换机与队列
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("ReceiveLogs01 等待接收消息，那接收到的消息打印在屏幕上");


        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("控制台打印接收到的消息：" + new String(message.getBody()));
        };

        // 消费消息
        channel.basicConsume(queueName, true, deliverCallback, (consumerTag) ->{});
    }
}
