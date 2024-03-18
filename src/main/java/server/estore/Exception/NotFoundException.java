package server.estore.Exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException{

    public NotFoundException(String obj) {
        super(obj + " not found", HttpStatus.NOT_FOUND);
    }
}
