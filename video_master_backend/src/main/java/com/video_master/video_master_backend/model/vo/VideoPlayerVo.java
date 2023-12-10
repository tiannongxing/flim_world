package com.video_master.video_master_backend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoPlayerVo {
    private Integer id;
    private String title;
    private Integer episode;
    private String uploadTime;
    private Integer totalEpisode;
    private String uri;
    private String fileFormat;
    private String imgSrc;
}
