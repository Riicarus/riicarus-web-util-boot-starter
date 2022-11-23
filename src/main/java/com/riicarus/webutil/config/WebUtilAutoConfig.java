package com.riicarus.webutil.config;

import com.riicarus.webutil.WebUtil;
import com.riicarus.webutil.weblog.WebLogInterceptor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [FEATURE INFO]<br/>
 * web 工具自动配置类
 *
 * @author Riicarus
 * @create 2022-10-27 20:30
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(WebUtil.class)
@Setter
@Getter
public class WebUtilAutoConfig {

    private WebUtilProperties webUtilProperties;

    @Autowired
    public void setWebUtilProperties(WebUtilProperties webUtilProperties) {
        this.webUtilProperties = webUtilProperties;
    }

    @Bean
    public AspectJExpressionPointcutAdvisor configAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(webUtilProperties.getLogPointcut());
        advisor.setAdvice(new WebLogInterceptor());
        return advisor;
    }

    @Bean
    public WebUtil webUtil() {
        return new WebUtil();
    }
}
