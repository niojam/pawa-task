package ee.pawadeck.taskmanagement.error;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static ee.pawadeck.taskmanagement.error.ErrorResponse.errorResponse;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@Component
@RestControllerAdvice
public class DefaultExceptionHandlerAdvice {

    private static final String INVALID_JSON_FORMAT = "JSON with an invalid format provided";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        if (exception.getCause() instanceof JsonProcessingException) {
            JsonProcessingException cause = (JsonProcessingException) exception.getCause();
            log.warn(cause.getOriginalMessage());
            return errorResponse(ErrorCode.INVALID_REQUEST, INVALID_JSON_FORMAT);
        }

        log.warn(exception.getMessage());
        return errorResponse(ErrorCode.INVALID_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException exception){
        log.warn(exception.getMessage());
        return errorResponse(ErrorCode.INVALID_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        return errorResponse(ErrorCode.INVALID_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.warn(exception.getMessage());
        return errorResponse(ErrorCode.INVALID_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleBindException(BindException exception) {
        log.warn(exception.getMessage());
        return errorResponse(ErrorCode.INVALID_REQUEST, exception.getMessage());
    }

    // handle any other unexpected exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalAndAnyOther(Exception exception) {
        log.error(exception.getMessage(), exception);
        return errorResponse(ErrorCode.TECHNICAL_ERROR, "Technical error");
    }

}
