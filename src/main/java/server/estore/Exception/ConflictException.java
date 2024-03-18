package server.estore.Exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException{
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
