package com.riicarus.webutil.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * [FEATURE INFO]<br/>
 * Jwt 配置属性
 *
 * @author Riicarus
 * @create 2022-10-30 1:04
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties("web.util.jwt")
@Setter
@Getter
public class JwtProperties {

    private String secret;

    private long expiration;

    private String issuer;

}
