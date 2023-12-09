package com.video_master.video_master_backend.model.dto;

import com.video_master.video_master_backend.model.entity.VideoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoListsDTO {
    List<VideoEntity> videoList;
    Integer total;
}
