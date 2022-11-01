package com.skyline.webutil;

import com.skyline.webutil.config.WebUtilProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebUtilApplicationTests {

    private WebUtilProperties webUtilProperties;

    @Autowired
    public void setWebUtilProperties(WebUtilProperties webUtilProperties) {
        this.webUtilProperties = webUtilProperties;
    }

    @Test
    void contextLoads() {
        System.out.println(webUtilProperties.getJwtProperties().getExpiration());
        System.out.println(webUtilProperties.getFileProperties().getStore().getPaths());
    }

}
