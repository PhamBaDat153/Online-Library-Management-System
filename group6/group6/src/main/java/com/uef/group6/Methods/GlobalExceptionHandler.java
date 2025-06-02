package com.uef.group6.Methods;


import com.uef.group6.Methods.ErrorRespones.ErrorRespone;
import com.uef.group6.Methods.Exceptions.BadRequestException;
import com.uef.group6.Methods.Exceptions.IDNotFoundException;
import com.uef.group6.Methods.Exceptions.NameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Trả về mã lỗi 404 khi không tìm thấy ID
    @ExceptionHandler(IDNotFoundException.class)
    public ResponseEntity<ErrorRespone> handleIDNotFoundException(IDNotFoundException exception) {
        ErrorRespone errorRespone = new ErrorRespone();
        errorRespone.setStatus(HttpStatus.NOT_FOUND.value());
        errorRespone.setMessage(exception.getMessage());
        errorRespone.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorRespone>(errorRespone, HttpStatus.NOT_FOUND);
    }

    //Trả về mã lỗi 404 khi không tìm thấy tên
    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity<ErrorRespone> handleBookNameNotFoundException(NameNotFoundException exception) {
        ErrorRespone errorRespone = new ErrorRespone();
        errorRespone.setStatus(HttpStatus.NOT_FOUND.value());
        errorRespone.setMessage(exception.getMessage());
        errorRespone.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorRespone>(errorRespone, HttpStatus.NOT_FOUND);
    }

    //Trả về lỗi 400 khi không nhập đúng định dạng
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorRespone> handleBadRequestException(BadRequestException exception) {
        ErrorRespone errorRespone = new ErrorRespone();
        errorRespone.setStatus(HttpStatus.BAD_REQUEST.value());
        errorRespone.setMessage(exception.getMessage());
        errorRespone.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorRespone>(errorRespone, HttpStatus.BAD_REQUEST);
    }
}
