package com.lenin.springsecurity6.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Usercontroller {
   /* @GetMapping("/hello1")
    public String hello(){
        return "Hello World Public";
    }

    @GetMapping("/hello2")
    public String hello2(){
        return "Hello World Private";
    }*/


    @GetMapping("/get")
    public String helloGet() {
        return "Hello World -GET";
    }

    @PostMapping("/post")
    public String helloPost() {
        return "Hello World -POST";
    }

    @PutMapping("/put")
    public String helloPut() {
        return "Hello World -PUT";
    }

    @DeleteMapping("/delete")
    public String helloDelete() {
        return "Hello World -DELETE";
    }

    @PatchMapping("patch")
    public String helloPatch() {
        return "Hello World -PATCH";
    }

}
