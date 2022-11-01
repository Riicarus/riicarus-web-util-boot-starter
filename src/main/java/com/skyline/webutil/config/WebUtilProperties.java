package com.skyline.webutil.config;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public class WebUtilProperties {

    private String logPointcut;

    private JwtProperties jwtProperties;

    private FileProperties fileProperties;

    @Autowired
    public void setJwtProperties(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Autowired
    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }
}
