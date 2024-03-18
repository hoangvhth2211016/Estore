package server.estore.Exception;

import org.springframework.http.HttpStatus;

public class KeyExpiredException extends ApiException{
    public KeyExpiredException(String message){
        super(message, HttpStatus.GONE);
    }
}
