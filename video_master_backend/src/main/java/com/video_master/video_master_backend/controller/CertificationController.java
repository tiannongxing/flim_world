package com.video_master.video_master_backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certification")
@Slf4j
public class CertificationController {
    @PostMapping("/getCaptcha")
    public String getCaptcha(@RequestBody String receivedJSON){
        log.info(receivedJSON);
        //todo 通过amqp发邮件并且存到redis，设置过期时间。
        return "true";
    }
}
