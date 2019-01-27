package com.xuecheng.test.rabbitmq.mq;

import com.rabbitmq.client.Channel;
import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收消息执行流程
 * 如果没有创建交换机和队列,就创建
 * 监听指定名字的队列就可以了
 */
@Component
public class ReceiveHandler {
    /**
     * 使用RabbitListener注解,queues,表示该方法监听的队列
     */
    @RabbitListener(queues  = RabbitmqConfig.QUEUE_INFORM_EMAIL)
    public void receive_email(String msg, Message message, Channel channel){
        System.out.println("email:"+msg);
    }

    @RabbitListener(queues  = RabbitmqConfig.QUEUE_INFORM_SMS)
    public void receive_sms(String msg, Message message, Channel channel){
        System.out.println("sms"+msg);
    }
}
