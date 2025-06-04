package com.uef.LibraryManagementWeb.UsefulMethod.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //trả về lỗi khi không tìm thấy thông tin
    @ExceptionHandler(InformationNotFoundException.class)
    public ResponseEntity<ErrorRespone> handleNotFoundException(InformationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorRespone(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new Date()));
    }

    //trả về lỗi khi điền thông tin không hợp lệ
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorRespone> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorRespone(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), new Date()));
    }

    //trả về lỗi khi hệ thống
    @ExceptionHandler(SystemErrorException.class)
    public ResponseEntity<ErrorRespone> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRespone(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), new Date()));
    }
}
