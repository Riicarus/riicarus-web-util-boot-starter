package com.skyline.webutil.response;

import com.skyline.webutil.exception.IErrorCode;

/**
 * [FEATURE INFO]<br/>
 * ResultCode enums
 *
 * @author Skyline
 * @create 2022/6/10 16:06
 * @since 1.0.0
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(600, "SUCCESS"),
    NO_PERMISSION(601, "NO_PERMISSION"),
    FAILED(602, "FAILED"),
    NULL(603, "NULL"),
    ELEMENT_EXISTS(604, "ELEMENT_EXISTS"),
    NO_ELEMENT(604, "NO_ELEMENT"),
    VALIDATE_FAILED(605, "VALIDATE_FAILED");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
