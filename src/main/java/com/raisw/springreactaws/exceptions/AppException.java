package com.raisw.springreactaws.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public AppException(ErrorMessage errorMessage, String value) {
        super(String.format(errorMessage.label, value));
        this.errorMessage = errorMessage;
    }

    public AppException(ErrorMessage errorMessage, Integer value) {
        super(String.format(errorMessage.label, value));
        this.errorMessage = errorMessage;
    }


}
