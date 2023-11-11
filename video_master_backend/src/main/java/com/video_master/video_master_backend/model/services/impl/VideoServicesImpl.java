package com.video_master.video_master_backend.model.services.impl;

import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.mapper.VideoMapper;
import com.video_master.video_master_backend.model.services.VideoServices;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoServicesImpl implements VideoServices {
    @Resource
    private VideoMapper videoMapper;
    @Override
    public List<VideoEntity> getVideoByName(String name) {
        return videoMapper.getVideoByName(name);
    }
}
