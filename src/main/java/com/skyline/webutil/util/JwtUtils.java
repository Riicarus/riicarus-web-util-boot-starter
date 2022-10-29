package com.skyline.webutil.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.skyline.webutil.config.WebUtilProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * [FEATURE INFO]<br/>
 * Jwt Util
 *
 * @author Skyline
 * @create 2022-10-30 0:52
 * @since 1.0.0
 */
@Component
public class JwtUtils {

    private WebUtilProperties webUtilProperties;

    @Autowired
    public void setWebUtilProperties(WebUtilProperties webUtilProperties) {
        this.webUtilProperties = webUtilProperties;
    }

    /**
     * 生成 token
     * @param map 要加密的信息
     * @return 令牌
     */
    public String sign(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach(builder::withClaim);
        // issuer
        builder.withIssuer(webUtilProperties.getJwtProperties().getIssuer());
        // expireTime
        Date date = new Date(System.currentTimeMillis() + webUtilProperties.getJwtProperties().getExpiration());
        builder.withExpiresAt(date);
        // 加密
        return builder.sign(Algorithm.HMAC256(webUtilProperties.getJwtProperties().getSecret()));
    }

    /**
     * 验证token合法性/获取token全部信息
     * @param token token
     * @return verify
     */
    public DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(webUtilProperties.getJwtProperties().getSecret())).build().verify(token);
    }

    /**
     * 获取token单个信息
     * @param token token
     * @param key key
     * @return value
     */
    public String getInfo(String token, String key) {
        DecodedJWT verify = verify(token);
        return verify.getClaim(key).asString();
    }
}
