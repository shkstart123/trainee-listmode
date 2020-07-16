package com.demo.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author shkstart
 * @create 2020-07-15 15:28
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"})
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunConfig {
    private   String endpoint;
    private   String accessKeyId;
    private   String accessKeySecret;
    private   String bucketName;
    private   String urlPrefix;

    @Bean
    public  OSS oSSClient() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setIdleConnectionTime(1000);
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);

    }
}