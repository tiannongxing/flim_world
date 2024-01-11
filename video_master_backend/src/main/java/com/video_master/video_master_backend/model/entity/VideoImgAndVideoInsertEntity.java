package com.video_master.video_master_backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoImgAndVideoInsertEntity {
    private String imgSrc;
    private String videoSrc;
}
