package com.demo.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shkstart
 * @create 2020-07-24 9:55
 */
@RestController
public class ProviderController {
    // 注入配置文件上下文
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping("/service")
    public String service(){
        return "provider invoke";
    }

    /*实时读取最新配置文件*/
    @GetMapping(value = "/configs")
    public String getConfigs(){
        return applicationContext.getEnvironment().getProperty("name");
    }
}
