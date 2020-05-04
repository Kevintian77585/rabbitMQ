package com.mmr.rabbitmq.workfair;

import com.mmr.rabbitmq.util.ConnectUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectUtils.getConnection();
        Channel channel = connection.createChannel();
        /**
         *每个消费者 发送确认消息之前，消息队列不会发送下一个消息
         * 限制发送给同一个消费者 不得超过一条消息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount,false);
         boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        for (int i = 0; i < 50; i++) {
            String msg = "hello work_queue" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("服务器发送的消息是："+msg);
            Thread.sleep(20);
        }

        channel.close();
        connection.close();

    }
}
