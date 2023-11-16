package com.video_master.video_master_backend.util;

import org.apache.ibatis.jdbc.SQL;

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
}
