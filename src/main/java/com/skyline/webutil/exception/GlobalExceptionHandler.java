package com.skyline.webutil.exception;

import com.skyline.webutil.response.Result;
import com.skyline.webutil.response.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [FEATURE INFO]<br/>
 * handle global exception
 *
 * @author Skyline
 * @create 2022/6/10 16:06
 * @since 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public Result<?> handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return Result.failed(e.getErrorCode());
        }
        return Result.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<?> handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return Result.failed(ResultCode.VALIDATE_FAILED, message);
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Result<?> handleException(Exception e) {
        return Result.failed(e.getCause() + " " + e.getMessage());
    }
}

