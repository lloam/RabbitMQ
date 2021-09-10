package com.mao.rabbitmq.three;

import com.mao.rabbitmq.utils.RabbitMQUtils;
import com.mao.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author: lloam
 * Date: 2021/9/9 23:01
 * Description:
 */
public class Worker04 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        System.out.println("C2等待接收消息处理时间较长");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            // 沉睡 1 秒，模拟业务操作
            SleepUtils.sleep(30);
            System.out.println("接收到消息：" + new String(message.getBody(), "UTF-8"));

            // 手动应答
            /**
             * 1.消息的标记 tag
             * 2.是否批量应答 false：不批量应答信道中的消息   true：批量
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        // 设置不公平分发
        // int prefetchCount = 1;
        // 预取值是 5
        int prefetchCount = 5;
        channel.basicQos(prefetchCount);
        // 采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消费消息回调逻辑");
        });
    }
}
