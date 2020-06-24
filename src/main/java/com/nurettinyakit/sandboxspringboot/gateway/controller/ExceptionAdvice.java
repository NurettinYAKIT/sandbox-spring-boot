package com.nurettinyakit.sandboxspringboot.gateway.controller;

import com.nurettinyakit.sandboxspringboot.domain.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.util.WebUtils.ERROR_REQUEST_URI_ATTRIBUTE;
import static org.springframework.web.util.WebUtils.ERROR_STATUS_CODE_ATTRIBUTE;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public Map<String, Object> handleNotFound(final RuntimeException ex,
                                              final ServletWebRequest webRequest) {
        log.warn("Requested resource not found.", ex);
        return handleException(NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IllegalCallerException.class)
    @ResponseStatus(FORBIDDEN)
    public Map<String, Object> handleForbidden(final RuntimeException ex,
                                               final ServletWebRequest webRequest) {
        log.warn("Requested resource not allowed for requesting party.", ex);
        return handleException(FORBIDDEN, webRequest);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraintViolationException(final ValidationException ex,
                                                                  final ServletWebRequest webRequest) {
        log.error("Not valid due to validation error: {}.", ex.getMessage());
        return handleException(BAD_REQUEST, webRequest);
    }

    @ExceptionHandler({IllegalStateException.class, NullPointerException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleInternalServerErrorException(final RuntimeException ex,
                                                                  final ServletWebRequest webRequest) {
        log.error("Internal Server error.", ex);
        return handleException(INTERNAL_SERVER_ERROR, webRequest);
    }

    private Map<String, Object> handleException(final HttpStatus status, final ServletWebRequest webRequest) {
        webRequest.setAttribute(ERROR_STATUS_CODE_ATTRIBUTE, status.value(), SCOPE_REQUEST);

        String path = webRequest.getRequest().getRequestURI();
        if (hasText(webRequest.getRequest().getQueryString())) {
            path += "?" + webRequest.getRequest().getQueryString();
        }
        webRequest.setAttribute(ERROR_REQUEST_URI_ATTRIBUTE, path, SCOPE_REQUEST);

        return new DefaultErrorAttributes().getErrorAttributes(webRequest, false);
    }
}
