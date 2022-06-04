package com.fenrir.moviecovidanalysis.exception;

public class DuplicateCredentialsException extends RuntimeException {
    public DuplicateCredentialsException(String message) {
        super(message);
    }
}
