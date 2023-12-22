package com.video_master.video_master_backend.mapper;

import com.video_master.video_master_backend.model.dto.VideoSearchedDTO;
import com.video_master.video_master_backend.model.entity.VideoCategoryEntity;
import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.entity.VideoPlayerEntity;
import com.video_master.video_master_backend.util.DynaSQLProviderBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface VideoMapper {
    @SelectProvider(type = DynaSQLProviderBuilder.class, method = "selectVideoByName")
    List<VideoEntity> getVideoByName(@Param("name") String name);

    @SelectProvider(type = DynaSQLProviderBuilder.class, method = "selectVideoEraByType")
    List<String> getVideoEra(@Param("type") String typeName);

    @SelectProvider(type = DynaSQLProviderBuilder.class, method = "selectVideoLocationByType")
    List<String> getVideoLocation();

    @SelectProvider(type = DynaSQLProviderBuilder.class, method = "selectVideoTypeByType")
    List<String> getVideoType(@Param("type") String typeName);

    @SelectProvider(type = DynaSQLProviderBuilder.class, method = "selectVideosLikeName")
    List<VideoEntity> getVideoLikeName(@Param("name") String name);

    // 由于数据库设计的问题，实际上这里获得的type是视频的category,使用的是视频的id非type的id值
    @Select("""
            SELECT
            	category_name
            FROM
            	videos
            	LEFT JOIN video_category ON videos.id = video_category.video_id
            	LEFT JOIN category ON video_category.category_id = category.id
            WHERE
            	videos.id = #{id}
            """)
    List<String> getVideoTypeById(@Param("id") int id);

    @Select("""
            SELECT
            	location_name
            FROM
            	 video_location
            WHERE
            	location_id = #{id}
            """)
    String getVideoLocationById(@Param("id") int id);

    @Select("""
            SELECT
            	category_name
            FROM
            	category
            WHERE
            	id = #{id}
            """)
    String getVideoTrueTypeById(@Param("id") int typeId);

    @SelectProvider(type=DynaSQLProviderBuilder.class,method = "selectVideoByIdAndCurrentEpisode")
    VideoPlayerEntity selectVideoByIdAndCurrentEpisode(@Param("id") Integer id,@Param("episode") Integer episode);

    @Select("""
            SELECT
            	pid,
            	GROUP_CONCAT( category_name SEPARATOR '__' ) AS category
            FROM
            	category
            GROUP BY
            	pid
            """)
    List<VideoCategoryEntity> getVideoCategoryList();

    @Select("""
            select location_name from video_location
            """)
    List<String> getAllLocation();
    //使用存储过程方式 只需调用存储过程和传入参数
    @Select("CALL query_video_by_id(#{rowId})")
    VideoEntity getVideoById(@Param("rowId") int rowId);

    // 对于实在没有办法使用注解处理的使用原始的xml来进行开发
    List<VideoEntity> getVideos(Map<String,String> params);

    Integer getAllVideosByParams(Map<String,String> params);

    List<VideoSearchedDTO> getSearchedVideoLikeName(Map<String,String> params);

    Integer getAllVideosLikeName(Map<String,String> params);

}
