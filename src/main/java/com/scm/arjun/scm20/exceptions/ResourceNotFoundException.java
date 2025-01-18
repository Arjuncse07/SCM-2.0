package com.scm.arjun.scm20.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }


    public ResourceNotFoundException(){
        super("Resource Not Found");
    }


}
