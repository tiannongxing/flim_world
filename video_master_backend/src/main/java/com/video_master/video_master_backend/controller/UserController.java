package com.video_master.video_master_backend.controller;

import com.video_master.video_master_backend.model.dto.UserRegisterDTO;
import com.video_master.video_master_backend.model.entity.UserRegisterEntity;
import com.video_master.video_master_backend.services.UserServices;
import com.video_master.video_master_backend.model.vo.UserRegisterVo;
import com.video_master.video_master_backend.util.GenerateCaptcha;
import com.video_master.video_master_backend.util.GenerateMailSender;
import com.video_master.video_master_backend.util.JackonUtil;
import com.video_master.video_master_backend.util.PasswordMatcher;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jdk.jfr.Registered;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController {
    @Resource
    private UserServices userServices;
    @Resource
    private RedisTemplate redisTemplate;
    @PostMapping("/getCaptcha")
    String getCaptcha(@RequestBody String json) {
        String mail = JackonUtil.getNode(json).get("mail").asText();
        String captcha = GenerateCaptcha.generate();
        String subject = "您本次的验证码是：";
        String text = """
                 本次请求的邮件验证码为: """ + captcha + """
                 本验证码 5 分钟内有效，请及时输入。（请勿泄露此验证码）               
                """;
        GenerateMailSender.send(subject,text,mail);
        // 将发送后的验证码添加到redis服务器中，并设置过期时间为5分钟
        redisTemplate.opsForValue().set(mail,captcha, Duration.ofSeconds(5*60));
        return captcha;
    }

    @PostMapping("/register")
    Boolean register(@RequestBody @Valid UserRegisterVo vo){ // 如果要使用校验注解，则必须让传入的参数在spring的托管下
        // 利用自定义异常类和全局异常处理机捕获异常，将异常状态输出至前端，且如有异常，则在该方法后的代码无法执行
        PasswordMatcher.match(vo.getPassword(), vo.getP_repeat());
        UserRegisterEntity userRegisterEntity = UserRegisterDTO.DtoToEntity(UserRegisterDTO.VoToDto(vo));
        return userServices.addNewUser(userRegisterEntity)>0;
    }

    @PostMapping("/login/mail")
    Boolean loginByMail(@RequestBody @Valid UserRegisterVo vo){
        return true;
    }

    @PostMapping("/login/password")
    Boolean loginByPassword(@RequestBody @Valid UserRegisterVo vo){

        return true;
    }

}