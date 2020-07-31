package com.demo.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author shkstart
 * @create 2020-07-24 10:04
 */
@FeignClient(name = "provider")
public interface ProviderClient {
    @GetMapping("/service")
    String service();
}