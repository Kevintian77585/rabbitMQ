package com.mmr.rabbitmq.ps;

import com.mmr.rabbitmq.util.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recieve1 {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    private static final String QUEUE_NAME = "test_queue_fanout_email";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        //定义消费者
        Consumer consumer = new DefaultConsumer(channel) {
            //消息到达处罚
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String str = new String(body, "utf-8");
                System.out.println("1邮件队列收到的信息是：" + str);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("1邮件处理队列处理完成");
                    //给发送者发送回执信息  说明上一条信息已经消费完成了
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean actoAck = true;
        channel.basicConsume(QUEUE_NAME, actoAck, consumer);

    }
}
