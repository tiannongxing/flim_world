package com.video_master.video_master_backend.mapper;

import com.video_master.video_master_backend.model.entity.UserRegisterEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("""
            insert into user(id,username,password,nickname) values(#{user.id},#{user.username},#{user.password},#{user.nickname})
            """)
    Integer insertNewUser(@Param("user") UserRegisterEntity user);
}
