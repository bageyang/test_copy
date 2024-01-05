package com.zj.auction.seckill.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        System.out.println("hello2");
        return LocalDateTime.now().toString();
    }
}
