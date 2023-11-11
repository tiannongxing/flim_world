package com.video_master.video_master_backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.management.Descriptor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoEntity {
    private Integer id;
    private String name;
    private Integer currentEpisode;
    private Integer totalEpisode;
    private String imgSrc;
    private String starring;
    private String description;
    private Integer type;
    private String location;
    private Integer finished;
}
