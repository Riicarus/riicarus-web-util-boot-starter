package com.riicarus.webutil;

import com.riicarus.comandante.main.CommandLauncher;
import com.riicarus.webutil.command.WebCommandIOHandler;
import com.riicarus.webutil.config.WebUtilProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class WebUtilApplicationTests {

    private final WebUtilProperties webUtilProperties;
    private final WebCommandIOHandler ioHandler;

    @Autowired
    public WebUtilApplicationTests(WebUtilProperties webUtilProperties, WebCommandIOHandler ioHandler) {
        this.webUtilProperties = webUtilProperties;
        this.ioHandler = ioHandler;
    }

    @Test
    void contextLoads() throws UnsupportedEncodingException, InterruptedException {
        System.out.println(webUtilProperties.getJwtProperties().getExpiration());
        System.out.println(webUtilProperties.getFileProperties().getStore().getPaths());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLauncher.redirectOutput(outputStream);

        ioHandler.dispatchCommand("command -i");
        ioHandler.dispatchCommand("command -h");

        Thread.sleep(1000);

        System.out.println(outputStream.toString(StandardCharsets.UTF_8.name()));
    }

}
