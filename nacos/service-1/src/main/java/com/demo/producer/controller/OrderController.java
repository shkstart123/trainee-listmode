package com.demo.producer.controller;

import com.demo.producer.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shkstart
 * @create 2020-07-29 16:36
 */
@RestController
    public class OrderController {
    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/showOrder")
    public String showOrder() {
        return "showOrder";
    }


    @PostMapping("/addOrder")
    public String addOrder() {
        return "addOrder";
    }


    @PostMapping("/updateOrder")
    public String updateOrder() {
        return "updateOrder";
    }


    @GetMapping("/deleteOrder")
    public String deleteOrder() {
        return "deleteOrder";
    }

    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }



}
