package server.estore.Service;


import server.estore.Model.Mail.MailTemplate;


public interface MailService {
    
    void send(MailTemplate mailTemplate);

}
