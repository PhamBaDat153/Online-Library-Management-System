package com.uef.LibraryManagementWeb.UsefulMethod.Exception;

public class InformationNotFoundException extends RuntimeException {


    public InformationNotFoundException() {
        super();
    }

    public InformationNotFoundException(String message) {
        super(message);
    }

    public InformationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public InformationNotFoundException(Throwable cause) {
        super(cause);
    }

    protected InformationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
