package com.video_master.video_master_backend.services;

import com.video_master.video_master_backend.model.dto.VideoSearchedDTO;
import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.entity.VideoPlayerEntity;
import com.video_master.video_master_backend.model.vo.VideoCategoryVo;
import com.video_master.video_master_backend.model.vo.VideoPlayerVo;
import org.springframework.web.multipart.MultipartFile;

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

    Integer getAllVideosByParams(Map<String,String> params);

    List<VideoSearchedDTO> getSearchedVideoLikeName(Map<String,String> params);

    Integer getAllVideosLikeName(Map<String,String> params);

    VideoPlayerVo getPlayerVideoByIdAndCurrentEpisode(Integer id, Integer currentEpisode);

    Boolean writeFileToDisk(MultipartFile ...files);

    VideoCategoryVo getVideoCategory();

    List<String> getLocations();

}