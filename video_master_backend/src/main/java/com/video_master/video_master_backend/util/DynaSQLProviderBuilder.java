package com.video_master.video_master_backend.util;

import com.video_master.video_master_backend.model.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
@Slf4j
public class DynaSQLProviderBuilder {
    public String selectVideoByName(final String name) {
        return new SQL(){
            {
                SELECT("videos.id","name","current_episode","total_episode","img_src",
                        "starring","description","type","location_id","publish_date","finished");
                FROM("videos");
                LEFT_OUTER_JOIN("category ON videos.type = category.id");
                WHERE("category.category_name=${name}");
                ORDER_BY("videos.finished");
                LIMIT(6);
            }
        }.toString();
    }

    public String selectVideoEraByType(final String type){
        return new SQL(){
            {
                SELECT("distinct publish_date");
                FROM("videos");
                WHERE("type=(select id from category where category_name=${type})");
                ORDER_BY("publish_date desc");
            }
        }.toString();
    }

    public String selectVideoLocationByType(){
        return new SQL(){
            {
                SELECT("location_name");
                FROM("video_location");
                ORDER_BY( "location_id");
            }
        }.toString();
    }

    public String selectVideoTypeByType(final String type){
        return new SQL(){
            {
                SELECT("category_name");
                FROM("category");
                WHERE("pid=(SELECT id from category where category_name=${type})");
            }
        }.toString();
    }

    public String selectVideosLikeName(final String name){
        return new SQL() {
            {
                SELECT("id","name","current_episode","total_episode","img_src",
                        "starring","description","type","location_id","publish_date","finished");
                FROM("videos");
                WHERE("name like '${name}'");
            }
        }.toString();
    }

    public String selectVideoByIdAndCurrentEpisode(final Integer id,final Integer episode){
        return new SQL(){
            {
                SELECT("""
                        video_detail.id,
                        video_detail.title,
                        video_detail.episode,
                        video_detail.file_name,
                        video_detail.upload_date,
                        videos.total_episode,
                        videos.img_src
                        """);
                FROM("video_detail");
                LEFT_OUTER_JOIN("videos on videos.id = video_detail.id");
                WHERE("video_detail.id = #{id} and video_detail.episode = #{episode}");
            }
        }.toString();
    }

    public String updateUser(final UserEntity user){
        return new SQL(){
            {
                UPDATE("user");
                SET("nickname = #{user.nickname}");
                if(!Objects.equals(user.getSex(),null)){
                    SET("sex = #{user.sex}");
                }
                if(!Objects.equals(user.getBirthday(),null)){
                    SET("birthday = #{user.birthday}");
                }
                if(!Objects.equals(user.getLocation(),null)){
                    SET("location = #{user.location}");
                }
                WHERE("id = #{user.id}");
            }
        }.toString();
    }

    public String updateUserPlusAvatar(final UserEntity user,final String avatar){
        return new SQL(){
            {
                UPDATE("user");
                SET("nickname = #{user.nickname}");
                if(!Objects.equals(user.getSex(),null)){
                    SET("sex = #{user.sex}");
                }
                if(!Objects.equals(user.getBirthday(),null)){
                    SET("birthday = #{user.birthday}");
                }
                if(!Objects.equals(user.getLocation(),null)){
                    SET("location = #{user.location}");
                }
                if(!Objects.equals(avatar,null)){
                    SET("img = #{avatar}");
                }
                WHERE("id = #{user.id}");
            }
        }.toString();
    }
}
