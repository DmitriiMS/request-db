package com.github.dmitriims.requestdb.exceptions;

import com.github.dmitriims.requestdb.dto.OperationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiAdvice {

    private final Logger log = LoggerFactory.getLogger(ApiAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<OperationResponse> handleCustomRuntimeException(CustomRuntimeException cre) {
        log.warn(cre.getMessage());
        return new ResponseEntity<>(
                new OperationResponse(false,
                        cre.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
