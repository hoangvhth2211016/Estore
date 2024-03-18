package server.estore.Model.Mail;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailTemplate implements Serializable {
    
    String to;
    
    String subject;
    
    String body;
    
    byte[] file;
    
    String fileName;
    
}
