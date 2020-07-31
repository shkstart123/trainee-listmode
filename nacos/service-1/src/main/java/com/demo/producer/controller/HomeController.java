package com.demo.producer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author shkstart
 * @create 2020-07-30 16:37
 */
@Controller
public class HomeController {
    @GetMapping(path = "/loginpage")
    public String getLoginPage() {
        return "/html/login.html";
    }
}
