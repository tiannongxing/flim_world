package com.video_master.video_master_backend.util;

import com.video_master.video_master_backend.exception.PasswordMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class PasswordMatcher {
    public static void match(String password,String repeat){
        if (!Objects.equals(password,repeat)){
            throw new PasswordMismatchException("重复输入密码不匹配，请检查");
        }
    }
}
