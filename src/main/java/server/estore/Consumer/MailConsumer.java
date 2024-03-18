package server.estore.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.estore.Model.Mail.MailTemplate;
import server.estore.Service.MailService;

@Service
public class MailConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(MailConsumer.class);
    
    @Autowired
    private MailService mailService;
    
    @RabbitListener(queues = "${rabbitmq.mail.queue}")
    public void consume(MailTemplate mailTemplate){
        logger.info("Processing mail request -> " + mailTemplate.getTo());
        mailService.send(mailTemplate);
    }
    
}
