package com.skyline.webutil.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * [FEATURE INFO]<br/>
 * 文件配置属性
 *
 * @author Skyline
 * @create 2022-11-1 15:31
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties("web.util.file")
@Setter
@Getter
public class FileProperties {

    private long maxSize;

    private FileStore store;

    @Autowired
    public void setStore(FileStore store) {
        this.store = store;
    }

    @Configuration
    @ConfigurationProperties("web.util.file.store")
    @Setter
    @Getter
    public static class FileStore {
        private Map<String, String> paths;
    }

}
