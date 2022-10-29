package com.skyline.webutil.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * [FEATURE INFO]<br/>
 * Jwt 配置属性
 *
 * @author Skyline
 * @create 2022-10-30 1:04
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties("web.util.jwt")
public class JwtProperties {

    private String secret;

    private long expiration;

    private String issuer;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
