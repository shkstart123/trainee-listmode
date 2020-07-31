package com.demo.consumer.controller;

import com.demo.consumer.client.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shkstart
 * @create 2020-07-24 10:05
 */
@RestController
public class ConsumerController {
    // 注入配置文件上下文
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private ProviderClient providerClient;
    /*测试服务间调用*/
    @GetMapping("/service")
    public String service(){

        String providerResult = providerClient.service();
        return "consumer invoke" + "|" + providerResult;
    }

    /*动态更新配置文件*/
    @GetMapping(value = "/configs")
    public String getConfigs(){
        return  applicationContext.getEnvironment().getProperty("name");


    }

}
