package com.uef.group6.Methods.Exceptions;

public class NameNotFoundException extends RuntimeException {
  public NameNotFoundException() {
    super();
  }

  public NameNotFoundException(String message) {
    super(message);
  }

  public NameNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public NameNotFoundException(Throwable cause) {
    super(cause);
  }

  protected NameNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
