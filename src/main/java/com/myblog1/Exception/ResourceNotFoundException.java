package com.myblog1.Exception;

import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.EntityNotFoundException;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
