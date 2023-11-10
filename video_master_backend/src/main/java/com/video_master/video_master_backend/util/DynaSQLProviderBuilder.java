package com.video_master.video_master_backend.util;

import org.apache.ibatis.jdbc.SQL;

public class DynaSQLProviderBuilder {
    public String selectVideoByName(final String name) {
        return new SQL(){
            {
                SELECT("id","name","current_episode","total_episode","img_src","starring",
                        "description","type","location","publish_date","finished");
                FROM("videos");
                LEFT_OUTER_JOIN("category ON videos.category_id = category.id");
                WHERE("category.category_name='${name}'");
                ORDER_BY("video.finished");
                LIMIT(6);
                OFFSET(0);
            }
        }.toString();
    }

}
