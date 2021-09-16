package com.mao.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author: lloam
 * Date: 2021/9/8 19:16
 * Description: 消费者：接收消息
 */
public class Consumer {

//    private static final String QUEUE_NAME = "hello";
    // 联邦交换机名称
    private static final String FED_EXCHANGE = "fed_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 host
        factory.setHost("192.168.124.169");
        // 设置账号
        factory.setUsername("admin");
        // 设置密码
        factory.setPassword("123");
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        // 联邦交换机
        // 声明交换机
        channel.exchangeDeclare(FED_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 声明队列
        channel.queueDeclare("node2_queue", true, false, false, null);
        // 绑定
        channel.queueBind("node2_queue", FED_EXCHANGE, "routeKey");

//        // 接收消息
//        DeliverCallback deliverCallback = (consumerTag, message) -> {
//            System.out.println(new String(message.getBody()));
//        };
//        CancelCallback cancelCallback = (consumerTag) -> {
//            System.out.println("消息消费中断");
//        };
        /**
         * 消费消息
         * 1.消费哪个队列的消息
         * 2.消费成功后是否要自动应答， true：代表自动应答 false：代表手动应答
         * 3.消费者成功消费消息的回调
         * 4.消费者取消消费消息的回调
         */
        // channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
