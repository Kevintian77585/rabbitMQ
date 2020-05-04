package com.mmr.rabbitmq.work;

import com.mmr.rabbitmq.util.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recieve1 {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消费者
       Consumer consumer = new DefaultConsumer(channel) {
            //消息到达处罚
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String str = new String(body, "utf-8");
                System.out.println("客户1端收到的信息是：" + str);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("消费者1处理完成");
                }
            }
        };
        boolean actoAck = true;
        channel.basicConsume(QUEUE_NAME, actoAck, consumer);

    }
}
