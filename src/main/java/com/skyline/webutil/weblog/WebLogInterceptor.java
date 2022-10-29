package com.skyline.webutil.weblog;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * [FEATURE INFO]<br/>
 * web log aspect
 *
 * @author Skyline
 * @create 2022/7/27 15:25
 * @since 1.0.0
 */
@Aspect
@Component
public class WebLogInterceptor implements MethodInterceptor {

    private final static Logger LOGGER = LogManager.getLogger(WebLogInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        doBefore(invocation);
        Object result = invocation.proceed();
        doAfter(result, startTime);

        return result;
    }

    private void doBefore(MethodInvocation invocation) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            LOGGER.info("==============================start==============================");
            LOGGER.info("URL                : {}", request.getRequestURL().toString());
            LOGGER.info("HTTP Method        : {}", request.getMethod());
            LOGGER.info("Class Method       : {}.{}", invocation.getMethod().getDeclaringClass().getName(), invocation.getMethod().getName());
            LOGGER.info("IP                 : {}", request.getRemoteAddr());

            Object[] requestParams = invocation.getArguments();
            LOGGER.info("Param              : {}", Arrays.toString(requestParams));
        }
    }

    private void doAfter(Object result, long startTime) {
        LOGGER.info("return value       : {}", result == null ? null : result.toString());
        LOGGER.info("time consuming     : {} ms", System.currentTimeMillis() - startTime);
        LOGGER.info("============================== end ==============================");
    }
}