package com.video_master.video_master_backend.model.services.impl;

import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.mapper.VideoMapper;
import com.video_master.video_master_backend.model.services.VideoServices;
import com.video_master.video_master_backend.util.VideosAttributes;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideoServicesImpl implements VideoServices {
    @Resource
    private VideoMapper videoMapper;
    @Override
    public List<VideoEntity> getVideoByName(String name) {
        return videoMapper.getVideoByName(name);
    }

    @Override
    public List<String> getEra(String type) {
        return videoMapper.getVideoEra(type);
    }

    @Override
    public List<String> getLocation(String type) {
        return videoMapper.getVideoLocation();
    }

    @Override
    public List<String> getAllType(String type) {
        return videoMapper.getVideoType(type);
    }

    @Override
    public List<VideoEntity> getVideoLikeName(String name) {
        return videoMapper.getVideoLikeName(name);
    }

    @Override
    public VideoEntity getVideoById(Integer id) {
        return videoMapper.getVideoById(id);
    }

    @Override
    public String videoLocation(Integer id) {
        return videoMapper.getVideoLocationById(id);
    }

    @Override
    public List<String> videoCategory(Integer id) {
        return videoMapper.getVideoTypeById(id);
    }

    @Override
    public String videoType(Integer typeId) {
        return videoMapper.getVideoTrueTypeById(typeId);
    }

    @Override
    public List<VideoEntity> getVideos(Map<String,String> params) {
        // 根据参数的个数和类型动态搜索
        if(params.get(VideosAttributes.TARGET_PAGE) != null) {
            int offset = (Integer.parseInt(params.get("TARGET_PAGE")) - 1) * 30;
            params.put(VideosAttributes.TARGET_OFFSET, Integer.toString(offset));
        }
        List<VideoEntity> videos = videoMapper.getVideos(params);
        return videos;
    }
}


