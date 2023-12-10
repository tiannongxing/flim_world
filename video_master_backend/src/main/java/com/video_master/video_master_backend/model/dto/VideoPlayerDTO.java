package com.video_master.video_master_backend.model.dto;


import com.video_master.video_master_backend.model.entity.VideoPlayerEntity;
import com.video_master.video_master_backend.model.vo.VideoPlayerVo;
import com.video_master.video_master_backend.model.vo.VideoVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoPlayerDTO {
    private Integer id;
    private String title;
    private Integer episode;
    private String fileName;
    private String uploadTime;
    private Integer totalEpisode;
    private String imgSrc;

    public static VideoPlayerDTO entityToDTO(VideoPlayerEntity entity){
        return new VideoPlayerDTO().builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .episode(entity.getEpisode())
                .fileName(entity.getFileName())
                .uploadTime(entity.getUploadDate())
                .totalEpisode(entity.getTotalEpisode())
                .imgSrc(entity.getImgSrc())
                .build();
    }

    public static VideoPlayerVo DTOToVo(VideoPlayerDTO dto){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY年MM月dd日");
        return new VideoPlayerVo().builder()
                .id(dto.getId())
                .uri(dto.getFileName())
                .fileFormat(dto.getFileName().substring(dto.getFileName().lastIndexOf(".")+1))
                .title(dto.getTitle())
                .episode(dto.getEpisode())
                .totalEpisode(dto.getTotalEpisode())
                .uploadTime(formatter.format(format.parse(dto.getUploadTime())).toString())
                .imgSrc(dto.getImgSrc())
                .build();
    }
}
