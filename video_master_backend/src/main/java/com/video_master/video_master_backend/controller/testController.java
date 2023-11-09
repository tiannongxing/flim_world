package com.video_master.video_master_backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/test")
public class testController {
    @GetMapping("/hello")
    public String hello() {
        log.info("hello");
        return "hello";
    }
}
