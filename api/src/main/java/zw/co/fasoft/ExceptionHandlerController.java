package zw.co.fasoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import zw.co.fasoft.exceptions.Error;
import zw.co.fasoft.exceptions.RecordNotFoundException;

import java.nio.file.FileAlreadyExistsException;

import static zw.co.fasoft.exceptions.Error.of;

/**
 * @author Fasoft
 * @date 30/May/2024
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class ExceptionHandlerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);



    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    Error recordNotFoundError(RecordNotFoundException e) {
        LOGGER.info("Record not found error: {}", e.getMessage());
        return Error.of(404, e.getMessage());
    }



    @ExceptionHandler(FileAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody
    Error recordAlreadyExistError(FileAlreadyExistsException e) {
        LOGGER.info("File already exist error: {}", e.getMessage());
        return Error.of(409, e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error httpClientErrorException(HttpClientErrorException e) {
        LOGGER.info("Error occured while processing request: Details {}", e.getMessage());
        return Error.of(400, e.getMessage());
    }

}