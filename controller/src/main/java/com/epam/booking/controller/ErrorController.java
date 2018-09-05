package com.epam.booking.controller;

import com.epam.booking.exception.ResourceNotFoundException;
import com.epam.booking.util.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ErrorController extends HttpServlet {
    private static final String WRONG_REQUEST_MESSAGE = "Resource was not found.";
    private static final String ERROR_MESSAGE = "Something went wrong.";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDTO exceptionHandler(Exception exception) {
        ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ERROR_MESSAGE, exception.getMessage());
        exception.printStackTrace();
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class, NoHandlerFoundException.class})
    public ErrorDTO handle404(Exception exception) {
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(),
                WRONG_REQUEST_MESSAGE, exception.getMessage());
        exception.printStackTrace();
        return error;
    }

}
