package com.lenin.springsecurity6.app.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class Usercontroller {

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
