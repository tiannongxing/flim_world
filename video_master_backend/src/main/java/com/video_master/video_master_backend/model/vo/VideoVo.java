package com.video_master.video_master_backend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoVo {
    private Integer id;
    private String name;
    private Integer currentEpisode;
    private Integer totalEpisode;
    private String imgSrc;
    private String starring;
    private String description;
    private String type;
    private List<String> category;
    private String location;
    private String publishDate;
    private String finished;
}
