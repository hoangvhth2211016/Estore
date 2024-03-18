package server.estore.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.estore.Model.Mail.MailTemplate;
import server.estore.Model.Order.Dto.OrderDetailRes;
import server.estore.Service.FileService;
import server.estore.Service.MailService;

import java.util.Date;

@Service
public class OrderMailingConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderMailingConsumer.class);
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private FileService fileService;
    
    @RabbitListener(queues = "${rabbitmq.order.mail.queue}")
    public void consume(OrderDetailRes orderDetail){
        logger.info("generate invoice and send email from order -> "+orderDetail);
        
        byte[] invoice = fileService.generateInvoiceAsPdf(orderDetail);
        
        MailTemplate mailTemplate =MailTemplate.builder()
                .to(orderDetail.getEmail())
                .subject("Invoice order: "+ orderDetail.getOrderNr())
                .body("Dear "+orderDetail.getUsername()+",\n\nThank you for choosing us " +
                        "for your recent purchase! We appreciate your business and are delighted to have you as our valued customer." +
                        "\n\nWe look forward to serving you again soon!\n\nBest regards,\n\nEstore")
                .file(invoice)
                .fileName("invoice_"+new Date().getTime()+".pdf")
                .build();
        
        mailService.send(mailTemplate);
    }
    
}
