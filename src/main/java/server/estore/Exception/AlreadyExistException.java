package server.estore.Exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends ApiException{
    public AlreadyExistException(String obj) {
        super(obj + " already exist", HttpStatus.CONFLICT);
    }
}
