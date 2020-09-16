package com.shb.springsecurityjsonlogin.controller;

import com.shb.springsecurityjsonlogin.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Shen Hanbo
 * @date : 2020/9/4 11:25
 */
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "hello springsecurity";
    }

    @GetMapping("/db/hello")
    public String dbahello(){
        return "dba dba dbahello springsecurity";
    }

    @GetMapping("/admin/hello")
    public String adminhello(){
        return "admin admin adminhello springsecurity";
    }

    @GetMapping("/user/hello")
    public String userhello(){
        return "user user userhello springsecurity";
    }

}
