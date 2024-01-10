package com.xprotec.app.exception;

public class WrappedException extends RuntimeException{

    public WrappedException(Throwable e) {
        super(e);
    }
}