package com.video_master.video_master_backend.util;

import org.apache.ibatis.jdbc.SQL;

public class DynaSQLProviderBuilder {
    public String selectVideoByName(final String name) {
        return new SQL(){
            {
                SELECT("videos.id","name","current_episode","total_episode","img_src",
                        "starring","description","type","location","publish_date","finished");
                FROM("videos");
                LEFT_OUTER_JOIN("category ON videos.type = category.id");
                WHERE("category.category_name=${name}");
                ORDER_BY("videos.finished");
                LIMIT(6);
            }
        }.toString();
    }

}
