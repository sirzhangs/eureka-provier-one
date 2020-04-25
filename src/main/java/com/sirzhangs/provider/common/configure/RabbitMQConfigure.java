package com.sirzhangs.provider.common.configure;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigure {
	
	@Value("${spring.rabbitmq.username}")
	private String username;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Value("${spring.rabbitmq.host}")
	private String host;
	
	@Value("${spring.rabbitmq.port}")
	private Integer port;
	
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setHost(host);
		connectionFactory.setPort(port);
//		connectionFactory.setPublisherConfirms(true);
		connectionFactory.setPublisherConfirmType(ConfirmType.CORRELATED);
		return connectionFactory;
	}
	
	@Bean
	public DirectExchange directExchange() {
//		DirectExchange directExchange = new DirectExchange(name, durable, autoDelete, arguments);
//		Exchange exchange = ExchangeBuilder.directExchange(name).autoDelete().durable(true).withArguments(arguments).build();
		return new DirectExchange("test_direct_exchange");
	}
	
	@Bean
	public FanoutExchange fanoutExchange() {
//		FanoutExchange fanoutExchange  = new FanoutExchange(name, durable, autoDelete,arguments);
//		Exchange exchange = ExchangeBuilder.fanoutExchange(name).autoDelete().durable(true).withArguments(arguments).build();
		return new FanoutExchange("test_fanout_exchange");
	}
	
	@Bean
	public TopicExchange topicExchange() {
//		TopicExchange topicExchange = new TopicExchange(name, durable, autoDelete, arguments);
//		Exchange exchange = ExchangeBuilder.topicExchange("").autoDelete().durable(true).withArguments(arguments).build();
		return new TopicExchange("test_topic_exchange");
	}
	
	@Bean
	public Queue testQueueWorkOne() {
//		Queue queue = new org.springframework.amqp.core.Queue(name, durable, exclusive, autoDelete, arguments);
//		Queue queue = QueueBuilder.durable("").exclusive().autoDelete().withArguments(arguments).build();
		return new Queue("test_queue_work1");
	}
	
	@Bean
	public Queue testQueueWorkTwo() {
//		Queue queue = new org.springframework.amqp.core.Queue(name, durable, exclusive, autoDelete, arguments);
//		Queue queue = QueueBuilder.durable("").exclusive().autoDelete().withArguments(arguments).build();
		return new Queue("test_queue_work2");
	}
	
	@Bean
	public Queue testQueueWorkThree() {
		return new Queue("test_queue_work3");
	}
	
	@Bean
	public Binding bindTwo(DirectExchange directExchange,Queue testQueueWorkTwo) {
//		Binding binding = new Binding(destination, destinationType, exchange, routingKey, arguments);
//		Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
		Binding binding = BindingBuilder.bind(testQueueWorkTwo).to(directExchange).with("topic.message");
		return binding;
	}
	
	@Bean
	public Binding bindOne(TopicExchange topicExchange,Queue testQueueWorkOne) {
//		Binding binding = new Binding(destination, destinationType, exchange, routingKey, arguments);
//		Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
		Binding binding = BindingBuilder.bind(testQueueWorkOne).to(topicExchange).with("topic.#");
		return binding;
	}
	
	@Bean
	public Binding bindThree(FanoutExchange fanoutExchange,Queue testQueueWorkThree) {
		Binding binding = BindingBuilder.bind(testQueueWorkThree).to(fanoutExchange);
		return binding;
	}
	
	@Bean
	public RabbitTemplate customRabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setConfirmCallback(msgSendConfirmCallBack());
		return rabbitTemplate;
	}
	
	@Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }
	
	@Bean
	public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory  containerFactory = new SimpleRabbitListenerContainerFactory();
		containerFactory.setConnectionFactory(connectionFactory());
		containerFactory.setMaxConcurrentConsumers(5);
		containerFactory.setConcurrentConsumers(1);
		containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
//		containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO); //设置确认模式自动确认
		return containerFactory;
	}

}
