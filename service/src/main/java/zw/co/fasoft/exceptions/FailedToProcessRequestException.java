package zw.co.fasoft.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class FailedToProcessRequestException extends RuntimeException {
    public FailedToProcessRequestException(String message) {
        super(message);
    }
}
