package com.mmr.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectUtils {

    /**
     * 获取MQ的连接
     *
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {

        //定义一个工厂连接
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务器地址
        factory.setHost("192.168.2.114");
        //设置端口号AMQP 5672
        factory.setPort(5672);
        //设置数据库 vhost
        factory.setVirtualHost("/vhost_mmr");
        //设置用户名密码
        factory.setUsername("user_mmr");
        factory.setPassword("123");
        return factory.newConnection();
    }
}
