package com.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
@SuppressWarnings("all")
public class Producer02 {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM="exchange_fanout_inform";

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
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            //声明队列
            /*
            1.队列名称
            2.是否持久化
            3.队列是否独占此连接
            4.队列不再使用时是否自动删除此队列
            5.队列参数
             */
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false, false, null);
            String messageSms = "这个消息只发给sms";
            String messageEmail = "这个消息这发给email";
            //交换机和队列绑定
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "");
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,"");
            //消息发布
            /*
            1.Exchange的名称，如果没有指定，则使用Default Exchange
            2.routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
            3.消息包含的属性
            4.消息体
             */
            channel.basicPublish(EXCHANGE_FANOUT_INFORM, QUEUE_INFORM_SMS, null, messageSms.getBytes());
            channel.basicPublish(EXCHANGE_FANOUT_INFORM, QUEUE_INFORM_EMAIL, null, messageEmail.getBytes());
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
