package server.estore.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Model.Order.Dto.OrderDto;
import server.estore.Producer.MailProducer;
import server.estore.Producer.OrderMailingProducer;
import server.estore.Service.OrderService;

@Service
public class OrderProcessingConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingConsumer.class);
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderMailingProducer orderMailingProducer;
    
    @Autowired
    private MailProducer mailProducer;
    
    @RabbitListener(queues = "${rabbitmq.order.process.queue}")
    public void consume(OrderDto dto){
            
            OrderDetailRes newOrder = orderService.create(dto);
            orderMailingProducer.send(newOrder);
            
    }

}
