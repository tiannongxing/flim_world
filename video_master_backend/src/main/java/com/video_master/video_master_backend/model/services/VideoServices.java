package com.video_master.video_master_backend.model.services;

import com.video_master.video_master_backend.model.entity.VideoEntity;

import java.util.List;
import java.util.Map;

public interface VideoServices {

    List<VideoEntity> getVideoByName(String name);

    List<String> getEra(String type);

    List<String> getLocation(String type);

    List<String> getAllType(String type);

    List<VideoEntity> getVideoLikeName(String Name);

    VideoEntity getVideoById(Integer id);

    String videoLocation (Integer id);

    List<String> videoCategory (Integer id);

    String videoType(Integer typeId);

    List<VideoEntity> getVideos(Map<String,String> params);

}