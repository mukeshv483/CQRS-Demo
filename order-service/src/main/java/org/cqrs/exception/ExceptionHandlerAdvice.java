package org.cqrs.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(InventoryNotAvailableException.class)
    public ProblemDetail handleExceptionStatus(Exception ex,
                                               WebRequest request) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail
                        (HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        problemDetails.setTitle(ex.getMessage());
        problemDetails.setDetail("inventory not available!");
        return problemDetails;
    }
    @ExceptionHandler(Throwable.class)
    public ProblemDetail handleExceptionStatus5xx(Exception ex,
                                               WebRequest request) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail
                        (HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        problemDetails.setTitle(ex.getMessage());
        return problemDetails;
    }
}
