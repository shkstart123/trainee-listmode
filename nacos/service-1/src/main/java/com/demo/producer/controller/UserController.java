package com.demo.producer.controller;

import com.demo.producer.entity.po.User;
import com.demo.producer.service.UserService;
import com.demo.producer.utils.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shkstart
 * @create 2020-07-31 10:14
 */

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/addUser")
    public String addUser(User user){
         userService.addUser(user);
         return CommunityUtil.getJSONString(1);
    }
    @GetMapping("/deleteUser")
    public String deleteUser(int id){
         userService.deleteUser(id);
         return CommunityUtil.getJSONString(1);
    }
    @PostMapping("/updateUser")
    public String updateUser(User user){
         userService.updateUser(user);
         return CommunityUtil.getJSONString(1);
    }
    @GetMapping("/selectUser")
    public User selectUser(int id){
         User user = userService.selectUser(id);
         return user;
    }
}
