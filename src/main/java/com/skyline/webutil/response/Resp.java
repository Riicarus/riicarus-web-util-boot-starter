package com.skyline.webutil.response;

import lombok.Builder;
import lombok.Data;

/**
 * [FEATURE INFO]<br/>
 * 返回类封装
 *
 * @author Skyline
 * @create 2022-11-7 14:36
 * @since 1.0.0
 */
@Data
@Builder
public class Resp<T> {

    private long timestamp;

    private String status;

    private String message;

    private T data;

    public static <T> Resp<T> success() {
        return success(null);
    }

    public static <T> Resp<T> success(T data) {
        return Resp.<T>builder()
                .data(data)
                .message(RespStatus.SUCCESS.getDescription())
                .status(RespStatus.SUCCESS.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static <T> Resp<T> fail(String message) {
        return fail(null, message);
    }

    public static <T> Resp<T> fail(T data, String message) {
        return Resp.<T>builder()
                .data(data)
                .message(RespStatus.FAIL.getDescription())
                .status(RespStatus.FAIL.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
