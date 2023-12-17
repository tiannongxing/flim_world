package com.video_master.video_master_backend.services.impl;

import com.video_master.video_master_backend.mapper.UserMapper;
import com.video_master.video_master_backend.model.dto.UserRegisterDTO;
import com.video_master.video_master_backend.model.entity.UserRegisterEntity;
import com.video_master.video_master_backend.services.UserServices;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServicesImpl implements UserServices {
    @Resource
    UserMapper userMapper;
    @Override
    public Integer addNewUser(UserRegisterEntity userRegister) {
        return userMapper.insertNewUser(userRegister);
    }
}
