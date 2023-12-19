package com.video_master.video_master_backend.services.impl;

import com.video_master.video_master_backend.exception.PasswordMismatchException;
import com.video_master.video_master_backend.exception.UserNotFountException;
import com.video_master.video_master_backend.mapper.UserMapper;
import com.video_master.video_master_backend.model.dto.UserRegisterDTO;
import com.video_master.video_master_backend.model.entity.UserEntity;
import com.video_master.video_master_backend.model.entity.UserRegisterEntity;
import com.video_master.video_master_backend.model.vo.UserLoginByMailVo;
import com.video_master.video_master_backend.model.vo.UserLoginByPasswordVo;
import com.video_master.video_master_backend.model.vo.UserRegisterVo;
import com.video_master.video_master_backend.services.UserServices;
import com.video_master.video_master_backend.util.JackonUtil;
import com.video_master.video_master_backend.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServicesImpl implements UserServices {
    @Resource
    UserMapper userMapper;

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public Integer addNewUser(UserRegisterEntity userRegister) {
        return userMapper.insertNewUser(userRegister);
    }

    @Override
    public Map<String,Object> UserCertificationByPassword(UserLoginByPasswordVo vo) {
        UserEntity userEntity = userMapper.selectUserExist(vo.getUsername());
        if (Objects.equals(userEntity,null)){
            throw new UserNotFountException("用户不存在");
        }
        if(!Objects.equals(userEntity.getPassword(),vo.getPassword())){
            throw new PasswordMismatchException("密码验证不通过");
        }

        // 生成一个token 再将token返回到前端，前端将token存储在localstorage中
        String token = JwtTokenUtil.generateToken(userEntity.getId());
        Map<String,Object> map = new HashMap();
        map.put("token",token);
        map.put("data",userEntity);

        return map;
    }

    @Override
    public Map<String,UserEntity> UserCertificationByMail(UserLoginByMailVo vo) {
        return null;
    }

    @Override
    public Map<String, UserEntity> UserCertificationByToken(String token) {
        return null;
    }

    @Override
    @Cacheable(key = "#id",value = "userCache")
    public UserEntity getUserById(Long id) {
        UserEntity userEntity = userMapper.selectUserExistById(id);
        return userEntity;
    }

    @Override
    @CacheEvict(key="#id",value = "userCache")
    public void userExit(Long id) {}
}
