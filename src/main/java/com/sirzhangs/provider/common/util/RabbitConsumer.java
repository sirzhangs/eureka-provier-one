package com.sirzhangs.provider.common.util;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class RabbitConsumer {
	
	@RabbitListener(queues = {"test_queue_work2","test_queue_work1"},containerFactory = "rabbitListenerContainerFactory")
	public void handleMessage(String info,Channel channel, Message message) throws IOException {
		System.out.println("接收者：" + info);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
