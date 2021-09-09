package com.mao.rabbitmq.utils;

/**
 * Author: lloam
 * Date: 2021/9/9 22:51
 * Description: 睡眠工具类
 */
public class SleepUtils {
    public static void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
