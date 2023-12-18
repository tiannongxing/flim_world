package com.video_master.video_master_backend.mapper;

import com.video_master.video_master_backend.model.entity.UserEntity;
import com.video_master.video_master_backend.model.entity.UserRegisterEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("""
            insert into user(id,username,password,nickname) values(#{user.id},#{user.username},#{user.password},#{user.nickname})
            """)
    Integer insertNewUser(@Param("user") UserRegisterEntity user);

    @Select("""
            select * from user where username = #{username}
            """)
    UserEntity selectUserExist(@Param("username") String username);


    @Select("""
            select * from user where id = #{id}
            """)
    UserEntity selectUserExistById(@Param("id") Long id);
}
