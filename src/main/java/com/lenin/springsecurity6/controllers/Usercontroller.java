package com.lenin.springsecurity6.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Usercontroller {
    @GetMapping("/hello1")
    public String hello(){
        return "Hello World Public";
    }

    @GetMapping("/hello2")
    public String hello2(){
        return "Hello World Private";
    }
}
