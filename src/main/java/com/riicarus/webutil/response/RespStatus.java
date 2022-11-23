package com.riicarus.webutil.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * [FEATURE INFO]<br/>
 * 返回类状态
 *
 * @author Riicarus
 * @create 2022-11-7 15:02
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum RespStatus {

    SUCCESS("200", "success"),
    FAIL("500", "fail"),

    HTTP_STATUS_200("200", "ok"),
    HTTP_STATUS_400("400", "request error"),
    HTTP_STATUS_401("401", "no authentication"),
    HTTP_STATUS_403("403", "no authority"),
    HTTP_STATUS_500("500", "server error"),
    ;

    public static final List<RespStatus> HTTP_STATUS_ALL = Collections.unmodifiableList(
            Arrays.asList(HTTP_STATUS_200, HTTP_STATUS_400, HTTP_STATUS_401, HTTP_STATUS_403, HTTP_STATUS_500
            ));

    private final String responseCode;

    private final String description;

}
