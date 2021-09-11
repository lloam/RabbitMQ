package com.mao.rabbitmq.eight;

import com.mao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * Author: lloam
 * Date: 2021/9/11 19:59
 * Description:
 * 死信队列
 * 生产者
 */
public class Producer {

    // 声明普通队列
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    // 开始发消息
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

/*        // 死信消息     设置过期时间 TTL 单位 10s = 10000ms
        AMQP.BasicProperties properties
                = new AMQP.BasicProperties()
                    .builder().expiration("10000").build();*/

        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, message.getBytes());
        }

    }
}
