package com.BuiHuuThong.MyTravel.controller;

import com.google.firebase.auth.FirebaseAuth;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class TestController {
    private FirebaseAuth firebaseAuth;
    @Autowired
    public TestController(FirebaseAuth firebaseAuth){
        this.firebaseAuth =firebaseAuth;
    }
    @GetMapping("/token")
    public String getToken(){
        return "Hello";
    }
}
