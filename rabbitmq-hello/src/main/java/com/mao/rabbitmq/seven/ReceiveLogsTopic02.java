package com.mao.rabbitmq.seven;

import com.mao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * Author: lloam
 * Date: 2021/9/11 17:10
 * Description:
 * 声明主题模式交换机 与绑定队列
 * 消费者
 */
public class ReceiveLogsTopic02 {

    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 声明队列
        String queueName = "Q2";
        channel.queueDeclare(queueName, false, false, false, null);
        // 绑定队列
        channel.queueBind(queueName, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");
        System.out.println("Q2 等待接收消息......");
        
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody(), "UTF-8"));
            System.out.println("接收队列：" + queueName + "  绑定键：" + message.getEnvelope().getRoutingKey());
        };

        // 接收消息
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

    }
}
