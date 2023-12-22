package com.video_master.video_master_backend.model.entity;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 8841617363117549076L;
    private String id;
    private String username;
    private String phone;
    private String password;
    private String img;
    private String birthday;
    private String sex;
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z].{5,23}$",message = "昵称格式检验错误，昵称必须使用字母、汉字开头，且需要在6-24个字之间")
    private String nickname;
    private String email;
    private String safeQuestion;
    private String location;
}
