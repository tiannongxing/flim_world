package com.video_master.video_master_backend.model.mapper;

import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.util.DynaSQLProviderBuilder;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface VideoMapper {
    @SelectProvider(type= DynaSQLProviderBuilder.class,method = "selectVideoByName")
    List<VideoEntity> getVideoByName(@Param("name") String name);

    @SelectProvider(type= DynaSQLProviderBuilder.class,method = "selectVideoEraByType")
    List<String> getVideoEra(@Param("type") String typeName);

    @SelectProvider(type= DynaSQLProviderBuilder.class,method = "selectVideoLocationByType")
    List<String> getVideoLocation();

    @SelectProvider(type= DynaSQLProviderBuilder.class,method = "selectVideoTypeByType")
    List<String> getVideoType(@Param("type") String typeName);

    @SelectProvider(type= DynaSQLProviderBuilder.class,method = "selectVideosLikeName")
    List<VideoEntity> getVideoLikeName(@Param("name") String name);
}
