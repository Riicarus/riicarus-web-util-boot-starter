package com.riicarus.webutil.exception;

import com.riicarus.webutil.response.RespStatus;
import lombok.Getter;

/**
 * [FEATURE INFO]<br/>
 * ApiException
 *
 * @author Riicarus
 * @create 2022/6/10 16:06
 * @since 1.0.0
 */
@Getter
public class ApiException extends RuntimeException {

    private RespStatus respStatus;

    public ApiException(RespStatus respStatus, String message) {
        super(message);
        this.respStatus = respStatus;
    }

    public ApiException(RespStatus respStatus, String message, Throwable cause) {
        super(message, cause);
        this.respStatus = respStatus;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

