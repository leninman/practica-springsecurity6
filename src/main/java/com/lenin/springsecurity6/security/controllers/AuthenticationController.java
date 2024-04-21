package com.lenin.springsecurity6.security.controllers;

import com.lenin.springsecurity6.security.persistence.dto.AuthLoginRequest;
import com.lenin.springsecurity6.security.persistence.dto.AuthResponse;
import com.lenin.springsecurity6.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest authRequestDto){
        return new ResponseEntity<>(userDetailsService.login(authRequestDto), HttpStatus.OK);
    }
}
