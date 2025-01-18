package com.scm.arjun.scm20.exceptions;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message){
        super(message);
    }

}
