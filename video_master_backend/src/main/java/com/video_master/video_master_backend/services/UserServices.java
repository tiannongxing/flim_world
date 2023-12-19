package com.video_master.video_master_backend.services;

import com.video_master.video_master_backend.model.dto.UserRegisterDTO;
import com.video_master.video_master_backend.model.entity.UserEntity;
import com.video_master.video_master_backend.model.entity.UserRegisterEntity;
import com.video_master.video_master_backend.model.vo.UserLoginByMailVo;
import com.video_master.video_master_backend.model.vo.UserLoginByPasswordVo;
import com.video_master.video_master_backend.model.vo.UserRegisterVo;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

public interface UserServices {
    Integer addNewUser(UserRegisterEntity userRegister);

    Map<String,Object> UserCertificationByPassword(UserLoginByPasswordVo vo);

    Map<String,UserEntity> UserCertificationByMail(UserLoginByMailVo vo);

    Map<String,UserEntity> UserCertificationByToken(String token);
    UserEntity getUserById(Long id);

    void userExit(Long id);
}
