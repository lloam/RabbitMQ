package com.mao.rabbitmq.two;

import com.mao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Author: lloam
 * Date: 2021/9/8 19:59
 * Description: 生产者，发送大量消息
 */
public class Task01 {

    // 队列名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取信道
        Channel channel = RabbitMQUtils.getChannel();

        /**
         * 声明一个队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化（磁盘） 默认情况消息存储在内存中
         * 3.该队列是否只提供一个消费者进行消费，是否进行消息共享，true：只能一个消费者消费 false：可以多个消费者消费
         * 4.是否自动删除，最后一个消费者端开连接以后，该队列是否自动删除，true：自动删除，false：不自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 从控制台接收用户输入
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送消息完成：" + message);
        }
    }
}
