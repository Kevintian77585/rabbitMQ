package com.mmr.rabbitmq.workfair;

import com.mmr.rabbitmq.util.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recieve2 {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtils.getConnection();
        final Channel channel = connection.createChannel();
        //保证每次只分发一条消息
        channel.basicQos(1);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消费者
        Consumer consumer = new DefaultConsumer(channel) {
            //消息到达处罚
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String str = new String(body, "utf-8");
                System.out.println("客户2端收到的信息是：" + str);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("消费者2处理完成");
                    //给发送者发送回执信息  说明上一条信息已经消费完成了
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean actoAck = true;
        channel.basicConsume(QUEUE_NAME, actoAck, consumer);

    }
}
