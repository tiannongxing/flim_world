package com.video_master.video_master_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoSearchedListDTO {
    private List<VideoSearchedDTO> videoList;
    private Integer total;
}
