package com.video_master.video_master_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoSearchedDTO {
    private String Id;
    private String name;
    private String starring;
    private String imgSrc;
    private Integer playsNumber;


}
