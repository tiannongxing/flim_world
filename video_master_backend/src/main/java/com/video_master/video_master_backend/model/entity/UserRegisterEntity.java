package com.video_master.video_master_backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterEntity {
    private Long id;
    private String username;
    private String password;
    private String nickname;
}
