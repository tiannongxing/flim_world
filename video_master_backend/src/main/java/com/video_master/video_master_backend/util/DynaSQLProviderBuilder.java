package com.video_master.video_master_backend.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

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

    public String selectVideos(final Map<String,String> params){
        return new SQL(){
            {
                SELECT("*");
                FROM("videos");
                if (!Objects.equals(params.get(VideosAttributes.VIDEOS_TYPE),null)){
                    final String category_name = params.get(VideosAttributes.VIDEOS_TYPE);
                    WHERE("type = ( SELECT id FROM category WHERE category_name = #{category_name} )");
                }
                if(!Objects.equals(params.get(VideosAttributes.VIDEOS_SUB_TYPE),null)){
                    final String category_name = params.get(VideosAttributes.VIDEOS_SUB_TYPE);
                    AND().WHERE("id IN ( SELECT video_category.video_id FROM video_category LEFT JOIN category ON category.id = video_category.category_id WHERE category.category_name = '#{category_name}' ) ");
                }
                if(!Objects.equals(params.get(VideosAttributes.VIDEOS_REGION),null)){
                    final String location_name = params.get(VideosAttributes.VIDEOS_REGION);
                    AND().WHERE("location_id = ( SELECT video_location.location_id FROM video_location WHERE location_name = '#{location_name}' ) ");
                }
                if(!Objects.equals(params.get(VideosAttributes.VIDEOS_REGION),null)){
                    final Integer publish_date = Integer.valueOf(params.get(VideosAttributes.VIDEOS_AGING));
                    AND().WHERE("publish_date = #{publish_date}");
                }
                LIMIT(30);
                if(!Objects.equals(params.get(VideosAttributes.TARGET_PAGE),null)) {
                    final Integer offset = (Integer.valueOf(params.get(VideosAttributes.TARGET_PAGE))-1)*30;
                }
            }
        }.toString();
    }
}
