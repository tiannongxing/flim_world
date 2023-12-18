package com.video_master.video_master_backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 8841617363117549076L;
    private Long id;
    private String username;
    private String phone;
    private String password;
    private String img;
    private Integer age;
    private String birthday;
    private String sex;
    private String nickname;
    private String email;
    private String safeQuestion;
}
