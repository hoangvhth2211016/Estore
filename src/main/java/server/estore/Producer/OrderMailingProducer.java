package server.estore.Producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Model.Order.Order;

@Service
public class OrderMailingProducer {
    
    @Value("${rabbitmq.order.exchange}")
    private String exchange;
    
    @Value("${rabbitmq.order.mail.key}")
    private String orderMailKey;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderMailingProducer.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void send(OrderDetailRes orderDetail){
        logger.info("ready to create pdf file and send to user -> " + orderDetail);
        rabbitTemplate.convertAndSend(exchange, orderMailKey, orderDetail);
    }
}
