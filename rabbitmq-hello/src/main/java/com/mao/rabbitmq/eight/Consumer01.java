package com.mao.rabbitmq.eight;

import com.mao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: lloam
 * Date: 2021/9/11 19:36
 * Description:
 *  死信队列    实战
 *  消费者 01
 */
public class Consumer01 {

    // 普通交换机名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    // 死信交换机名称
    public static final String DEAD_EXCHANGE = "dead_exchange";

    // 普通队列名称
    public static final String NORMAL_QUEUE = "normal_queue";

    // 死信队列名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        // 声明死信和普通交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, "direct");
        channel.exchangeDeclare(DEAD_EXCHANGE, "direct");
        // 声明普通队列
        Map<String, Object> arguments = new HashMap<>();
        // 过期时间 10s = 10000ms，一般在生产方设置消息过期时间
        // arguments.put("x-message-ttl", 10000);
        // 正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信 RoutingKey
        arguments.put("x-dead-letter-routing-key", "lisi");
/*        // 设置队列的长度 6 超过长度消息将会转发到死信队列
        arguments.put("x-max-length", 6);*/

        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        // 声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 绑定交换机与队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        System.out.println("等待接收消息......");
        // 接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");

            if (msg.equals("info5")) {
                System.out.println("Consumer01 接收到的消息是：" + msg + "，该消息是被拒绝的");
                // requeue：是否放回原队列
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            }else {
                System.out.println("Consumer01 接收到的消息是：" + msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }

        };
        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, consumerTag -> {});


    }
}
