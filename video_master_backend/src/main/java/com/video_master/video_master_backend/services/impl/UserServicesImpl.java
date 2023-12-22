package com.video_master.video_master_backend.services.impl;

import com.video_master.video_master_backend.exception.PasswordMismatchException;
import com.video_master.video_master_backend.exception.UnableFileUpload;
import com.video_master.video_master_backend.exception.UserNotFountException;
import com.video_master.video_master_backend.mapper.UserMapper;
import com.video_master.video_master_backend.model.dto.UserRegisterDTO;
import com.video_master.video_master_backend.model.entity.UserEntity;
import com.video_master.video_master_backend.model.entity.UserRegisterEntity;
import com.video_master.video_master_backend.model.vo.UserLoginByMailVo;
import com.video_master.video_master_backend.model.vo.UserLoginByPasswordVo;
import com.video_master.video_master_backend.model.vo.UserRegisterVo;
import com.video_master.video_master_backend.services.UserServices;
import com.video_master.video_master_backend.util.FtpUtil;
import com.video_master.video_master_backend.util.JackonUtil;
import com.video_master.video_master_backend.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Base64;
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

    @Value("${store.ftp.host}")
    private String ftpHost;
    @Value("${store.ftp.port}")
    private Integer ftpPort;
    @Value("${store.ftp.user}")
    private String user;
    @Value("${store.ftp.password}")
    private String password;
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
        String token = JwtTokenUtil.generateToken(Long.valueOf(userEntity.getId()));
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
    public UserEntity getUserById(String id) {
        UserEntity userEntity = userMapper.selectUserExistById(id);
        return userEntity;
    }

    @Override
    @CachePut(key = "#id",value = "userCache")
    public UserEntity updateUserById(String id) {
        UserEntity userEntity = userMapper.selectUserExistById(id);
        return userEntity;
    }

    @Override
    @CacheEvict(key="#id",value = "userCache")
    public void userExit(String id) {}

    @Override
    public Integer userUpdate(UserEntity userData) {

        if(userData.getImg().indexOf("data:image") >= 0){
            String base64 = userData.getImg().replace("-","+").replace("_","/").substring(userData.getImg().indexOf(",")+1);
            String remoteFilePath = "avatar/img_"+ System.currentTimeMillis() + ".png";
            //表示图片文件是一个base64格式的图片 并不严谨，需要严格验证的话依赖正则表达式 将base64转码后写入服务器
            byte [] decodedBytes = Base64.getDecoder().decode(base64);
            InputStream fileString = new ByteArrayInputStream(decodedBytes);
            try {
                boolean res = FtpUtil.uploadFile(ftpHost,ftpPort,user,password,"imgs/users/"+remoteFilePath,fileString);
                if (!res){
                    throw new UnableFileUpload("文件上传失败");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return userMapper.updateUserPlusAvatar(userData, remoteFilePath);
        } else{
            return userMapper.updateUser(userData);
        }
    }
}
