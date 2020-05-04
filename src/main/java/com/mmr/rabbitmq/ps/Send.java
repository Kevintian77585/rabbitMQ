package com.mmr.rabbitmq.ps;

import com.mmr.rabbitmq.util.ConnectUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机  fanout交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //发送消息
        String msg = "hello ps";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("订阅者发送的消息：" + msg);
        channel.close();
        connection.close();
    }
}
