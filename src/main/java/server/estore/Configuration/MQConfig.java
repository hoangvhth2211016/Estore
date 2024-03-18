package server.estore.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class MQConfig {
    
    @Value("${rabbitmq.order.exchange}")
    private String orderExchange;
    
    @Value("${rabbitmq.order.process.queue}")
    private String orderProcessQueue;
    
    @Value("${rabbitmq.order.process.key}")
    private String orderProcessKey;
    
    @Value("${rabbitmq.order.mail.queue}")
    private String orderMailQueue;
    
    @Value("${rabbitmq.order.mail.key}")
    private String orderMailKey;
    
    @Value("${rabbitmq.mail.exchange}")
    private String mailExchange;
    
    @Value("${rabbitmq.mail.queue}")
    private String mailQueue;
    
    @Value("${rabbitmq.mail.key}")
    private String mailKey;
    
    @Value("${rabbitmq.dead_letter.exchange}")
    private String deadLetterExchange;
    
    @Value("${rabbitmq.dead_letter.queue}")
    private String deadLetterQueue;
    
    @Value("${rabbitmq.dead_letter.key}")
    private String deadLetterKey;
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return objectMapper;
    }
    
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter(objectMapper());
    }
    
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(orderExchange);
    }
    
    @Bean
    public Queue orderProcessQueue(){
        return QueueBuilder
                .durable(orderProcessQueue)
                .deadLetterExchange(deadLetterExchange)
                .deadLetterRoutingKey(deadLetterKey)
                .build();
    }
    
    @Bean
    public Binding orderProcessQueueBinding(){
        return BindingBuilder
                .bind(orderProcessQueue())
                .to(orderExchange())
                .with(orderProcessKey);
    }
    
    @Bean
    public Queue orderMailQueue(){
        return QueueBuilder
                .durable(orderMailQueue)
                .deadLetterExchange(deadLetterExchange)
                .deadLetterRoutingKey(deadLetterKey)
                .build();
    }
    
    @Bean
    public Binding orderMailQueueBinding(){
        return BindingBuilder
                .bind(orderMailQueue())
                .to(orderExchange())
                .with(orderMailKey);
    }
    
    @Bean
    public TopicExchange mailExchange(){
        return new TopicExchange(mailExchange);
    }
    
    @Bean
    public Queue mailQueue(){
        return QueueBuilder
                .durable(mailQueue)
                .deadLetterExchange(deadLetterExchange)
                .deadLetterRoutingKey(deadLetterKey)
                .build();
    }
    
    @Bean
    public Binding mailQueueBinding(){
        return BindingBuilder
                .bind(mailQueue())
                .to(mailExchange())
                .with(mailKey);
    }
    
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }
    
    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder
                .durable(deadLetterQueue)
                .build();
    }
    
    @Bean
    public Binding deadLetterQueueBinding(){
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(deadLetterKey);
    }
}
