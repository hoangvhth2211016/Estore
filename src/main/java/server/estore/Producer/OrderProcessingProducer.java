package server.estore.Producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.estore.Model.Order.Dto.OrderDto;


@Service
public class OrderProcessingProducer {
    
    @Value("${rabbitmq.order.exchange}")
    private String exchange;
    
    @Value("${rabbitmq.order.process.key}")
    private String orderProcessKey;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingProducer.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void send(OrderDto dto){
        logger.info("Forward to order service to process -> " + dto);
        
        rabbitTemplate.convertAndSend(exchange, orderProcessKey, dto);
    }

}
