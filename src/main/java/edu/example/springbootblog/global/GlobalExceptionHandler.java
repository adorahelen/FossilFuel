package edu.example.springbootblog.global;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404() {
        return "error-404";
    }

    @ExceptionHandler(Exception.class)
    public String handle500() {
        return "error-500";
    }
}

