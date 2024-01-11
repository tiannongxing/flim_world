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
public class VideoUploadVo {
    private String videoName;
    private String currentEpisode;
    private String totalEpisode;
    private String starring;
    private String description;
    private String type;
    private List<String> subType;
    private String location;
    private String publishDate;
}
