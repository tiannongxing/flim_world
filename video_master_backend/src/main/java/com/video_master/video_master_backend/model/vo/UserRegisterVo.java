package com.video_master.video_master_backend.model.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVo {
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z].{5,23}$",message = "昵称格式检验错误，昵称必须使用字母、汉字开头，且需要在6-24个字之间")
    private String nickname;
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$",message = "用户名必须以字母开头，且在6-16个字之间")
    private String username;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,24}$", message = "密码必须包含大小写字母和数字的组合，不能使用特殊字符，长度在 6-24 之间")
    private String password;
    private String p_repeat;
}
