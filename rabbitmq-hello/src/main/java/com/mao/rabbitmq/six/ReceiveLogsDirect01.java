package com.mao.rabbitmq.six;

import com.mao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * Author: lloam
 * Date: 2021/9/11 15:08
 * Description: 消费者
 * 路由模式消费者  console
 */
public class ReceiveLogsDirect01 {

    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 声明并绑定队列
        channel.queueDeclare("console", false, false, false,null);
        channel.queueBind("console", EXCHANGE_NAME, "info");
        channel.queueBind("console", EXCHANGE_NAME, "warning");

        System.out.println("ReceiveLogsDirect01 等待接收消息，那接收到的消息打印在屏幕上");


        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("控制台打印接收到的消息：" + new String(message.getBody()));
        };

        // 消费消息
        channel.basicConsume("console", true, deliverCallback, (consumerTag) ->{});

    }
}
