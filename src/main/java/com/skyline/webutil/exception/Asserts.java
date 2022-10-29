package com.skyline.webutil.exception;

/**
 * [FEATURE INFO]<br/>
 * assert handler, throw exception
 *
 * @author Skyline
 * @create 2022/6/10 16:06
 * @since 1.0.0
 */
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

}
