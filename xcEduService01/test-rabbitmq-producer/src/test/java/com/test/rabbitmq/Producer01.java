package com.test.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer01 {
    //队列名称
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        //创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //虚拟机路径
        factory.setVirtualHost("/");
        try {
            //创建tcp连接
            connection = factory.newConnection();
            //创建会话通道
            channel = connection.createChannel();
            //声明队列
            /*
            1.队列名称
            2.是否持久化
            3.队列是否独占此连接
            4.队列不再使用时是否自动删除此队列
            5.队列参数
             */
            channel.queueDeclare(QUEUE,true,false, false, null);
            String message = "helloworld小明"+System.currentTimeMillis();
            //消息发布
            /*
            1.Exchange的名称，如果没有指定，则使用Default Exchange
            2.routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
            3.消息包含的属性
            4.消息体
             */
            channel.basicPublish("", QUEUE, null, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
