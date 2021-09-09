package com.mao.rabbitmq.three;

import com.mao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Author: lloam
 * Date: 2021/9/9 22:43
 * Description: 生产者：生产消息，消息未应答将重新进入队列
 */
public class Task2 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();

        boolean durable = true; // 是否持久化
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            // MessageProperties.PERSISTENT_TEXT_PLAIN : 保证消息持久化
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println("生产者发送消息：" + message);
        }
    }
}
