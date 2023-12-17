package com.video_master.video_master_backend.services;

import com.video_master.video_master_backend.model.dto.UserRegisterDTO;
import com.video_master.video_master_backend.model.entity.UserRegisterEntity;

public interface UserServices {
    Integer addNewUser(UserRegisterEntity userRegister);
}
