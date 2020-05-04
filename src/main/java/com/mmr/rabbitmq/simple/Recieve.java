package com.mmr.rabbitmq.simple;

import com.mmr.rabbitmq.util.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recieve {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //定义队列的消费者
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String strMsg = new String(body, "utf-8");
                System.out.println("recieve msg=" + strMsg);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

    private static void oldConsumer() throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] body = delivery.getBody();
            String msgString = new String(body);
            System.out.println("recieve msg=" + msgString);

        }
    }
}
