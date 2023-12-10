package com.video_master.video_master_backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoPlayerEntity {
    private Integer id;
    private String title;
    private Integer episode;
    private String fileName;
    private String uploadDate;
    private Integer totalEpisode;
    private String imgSrc;
}
