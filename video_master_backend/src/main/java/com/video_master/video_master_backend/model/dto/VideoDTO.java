package com.video_master.video_master_backend.model.dto;

import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.vo.VideoVo;
import com.video_master.video_master_backend.util.DataTransferUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoDTO {
    private Integer id;
    private String name;
    private Integer currentEpisode;
    private Integer totalEpisode;
    private String imgSrc;
    private String starring;
    private String description;
    private Integer type;
    private Integer locationId;
    private String publishDate;
    private Integer finished;

    public static VideoDTO entityToDTO(VideoEntity entity){
        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCurrentEpisode(entity.getCurrentEpisode());
        dto.setTotalEpisode(entity.getTotalEpisode());
        dto.setImgSrc(entity.getImgSrc());
        dto.setStarring(entity.getStarring());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        dto.setLocationId(entity.getLocationId());
        dto.setPublishDate(entity.getPublishDate());
        dto.setFinished(entity.getFinished());

        return dto;
    }

    public static VideoVo DTOToVo(VideoDTO dto){
        VideoVo vo = new VideoVo();
        vo.setId(dto.getId());
        vo.setName(dto.getName());
        vo.setCurrentEpisode(dto.getCurrentEpisode());
        vo.setTotalEpisode(dto.getTotalEpisode());
        vo.setImgSrc(dto.getImgSrc());
        vo.setStarring(dto.getStarring());
        vo.setDescription(dto.getDescription());
        vo.setType(DataTransferUtil.getVideoType(dto.getType()));
        vo.setCategory(DataTransferUtil.getVideoCategory(dto.getId()));
        vo.setLocation(DataTransferUtil.getVideoLocation(dto.getLocationId()));
        vo.setPublishDate(dto.getPublishDate());
        vo.setFinished(dto.getFinished()==0?"未完结":"已完结");
        return vo;
    }
}
