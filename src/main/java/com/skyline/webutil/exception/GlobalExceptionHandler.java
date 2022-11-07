package com.skyline.webutil.exception;

import com.skyline.webutil.response.Resp;
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
    @ExceptionHandler(ApiException.class)
    public Resp<?> handle(ApiException e) {
        return Resp.fail(e, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Resp<?> handleException(Exception e) {
        return Resp.fail(e, e.getMessage());
    }
}

