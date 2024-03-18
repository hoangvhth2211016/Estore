package server.estore.Service_Implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import server.estore.Exception.ServerErrorException;
import server.estore.Model.Mail.MailTemplate;
import server.estore.Model.Verification.Verification;
import server.estore.Service.MailService;

@Service
public class MailServiceImpl implements MailService {

    @Value("${store.email}")
    private String from;

    @Value("${store.app.url}")
    private String url;

    @Autowired
    private JavaMailSender javaMailSender;
    
    
    @Override
    public void send(MailTemplate mailTemplate) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
           
            helper.setFrom(from);
            helper.setTo(mailTemplate.getTo());
            helper.setSubject(mailTemplate.getSubject());
            helper.setText(mailTemplate.getBody());
          
            if(mailTemplate.getFile() != null && mailTemplate.getFileName() != null){
                helper.addAttachment(mailTemplate.getFileName(), new ByteArrayResource(mailTemplate.getFile()));
            }
            
            javaMailSender.send(message);
        }catch(MessagingException e){
            throw new ServerErrorException("Unable to send email");
        }
    
    }

}
