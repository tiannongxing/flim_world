package com.video_master.video_master_backend.model.dto;

import com.video_master.video_master_backend.model.entity.UserRegisterEntity;
import com.video_master.video_master_backend.model.vo.UserRegisterVo;
import com.video_master.video_master_backend.util.SnowFlake;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {
    private String id;
    @NotBlank(message = "用户名不得为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$", message = "用户名必须以字母开头，且在6-16个字之间")
    private String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,24}$", message = "密码必须包含大小写字母和数字的组合，不能使用特殊字符，长度在 6-24 之间")
    private String password;
    @NotBlank(message = "昵称不得为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z].{5,23}$", message = "昵称格式检验错误，昵称必须使用字母、汉字开头，且需要在6-24个字之间")
    private String nickname;

    @Validated
    public static UserRegisterDTO VoToDto(UserRegisterVo vo) {
        return UserRegisterDTO.builder()
                .id(String.valueOf(new SnowFlake(1l, 1l).nextId()))
                .nickname(vo.getNickname())
                .password(vo.getPassword())
                .username(vo.getUsername())
                .build();
    }

    public static UserRegisterEntity DtoToEntity(UserRegisterDTO dto){
        return UserRegisterEntity.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                // 在测试阶段采用明码保存。后面应该使用加密
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .build();
    }

}
