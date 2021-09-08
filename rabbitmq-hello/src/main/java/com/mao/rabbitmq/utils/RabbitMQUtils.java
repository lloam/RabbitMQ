package com.mao.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author: lloam
 * Date: 2021/9/8 19:36
 * Description: 此类为连接工厂创建信道的工具类
 */
public class RabbitMQUtils {
    public static Channel getChannel() throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置主机
        factory.setHost("192.168.124.168");
        // 设置用户名
        factory.setUsername("admin");
        // 设置密码
        factory.setPassword("123");
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建信道
        return connection.createChannel();
    }
}
