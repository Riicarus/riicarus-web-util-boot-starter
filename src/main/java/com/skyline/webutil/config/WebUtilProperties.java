package com.skyline.webutil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * [FEATURE INFO]<br/>
 * 配置属性
 *
 * @author Skyline
 * @create 2022-10-27 20:32
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties("web.util")
public class WebUtilProperties {

    private String logPointcut;

    private JwtProperties jwtProperties;

    public String getLogPointcut() {
        return logPointcut;
    }

    public void setLogPointcut(String logPointcut) {
        this.logPointcut = logPointcut;
    }

    public JwtProperties getJwtProperties() {
        return jwtProperties;
    }

    @Autowired
    public void setJwtProperties(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }
}
