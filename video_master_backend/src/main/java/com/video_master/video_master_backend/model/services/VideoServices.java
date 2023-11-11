package com.video_master.video_master_backend.model.services;

import com.video_master.video_master_backend.model.entity.VideoEntity;

import java.util.List;

public interface VideoServices {

    List<VideoEntity> getVideoByName(String name);
}
