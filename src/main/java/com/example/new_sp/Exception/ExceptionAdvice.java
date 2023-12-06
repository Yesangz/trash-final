package com.example.new_sp.Exception;


import com.example.new_sp.Result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException exception){
        //日志
        return new Result(null,exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException exception){
        //日志
        return new Result(null,exception.getCode(), exception.getMessage());
    }
}
