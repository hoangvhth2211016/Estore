package server.estore.Producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.estore.Model.Mail.MailTemplate;

@Service
public class MailProducer {
    @Value("${rabbitmq.mail.exchange}")
    private String exchange;
    
    @Value("${rabbitmq.mail.key}")
    private String mailKey;
    
    private static final Logger logger = LoggerFactory.getLogger(MailProducer.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void send(MailTemplate mailTemplate){
        logger.info("Send mail request to queue -> " + mailTemplate);
        rabbitTemplate.convertAndSend(exchange, mailKey, mailTemplate);
    }
    
}
