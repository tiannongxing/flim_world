package com.video_master.video_master_backend.config;

import com.video_master.video_master_backend.exception.PasswordMismatchException;
import com.video_master.video_master_backend.exception.UserNotFountException;
import com.video_master.video_master_backend.util.JackonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 全局异常处理机
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> exceptionHandler(MethodArgumentNotValidException ex) {
        List<String> errorMessage = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorMessage.add(error.getDefaultMessage()); // 这里获取的是注解中设置的 message
        });
        return errorMessage;
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> exceptionHandler(PasswordMismatchException pme) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(pme.getMessage());
        return errorMessage;
    }

    @ExceptionHandler(UserNotFountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> exceptionHandler(UserNotFountException unfe) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(unfe.getMessage());
        return errorMessage;
    }
}
