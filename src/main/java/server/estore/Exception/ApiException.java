package server.estore.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException{

    @Setter
    private HttpStatus status;

    public ApiException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
