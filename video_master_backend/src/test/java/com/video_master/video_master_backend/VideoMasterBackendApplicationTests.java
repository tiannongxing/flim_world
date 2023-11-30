package com.video_master.video_master_backend;


import com.video_master.video_master_backend.model.dto.VideoDTO;
import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.services.VideoServices;

import com.video_master.video_master_backend.model.vo.VideoVo;
import com.video_master.video_master_backend.util.DynaSQLProviderBuilder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class VideoMasterBackendApplicationTests {

    @Test
    void contextLoads() {
        DynaSQLProviderBuilder dynaSQLProviderBuilder = new DynaSQLProviderBuilder();
        log.info(dynaSQLProviderBuilder.selectVideoEraByType("电影"));
        log.info(dynaSQLProviderBuilder.selectVideoByName("电影"));
    }

    @Resource
    private VideoServices videoServices;
    @Test
    void porcTest() {
        VideoEntity videoById = videoServices.getVideoById(1);
        VideoDTO videoDTO = VideoDTO.entityToDTO(videoById);
        VideoVo videoVo = VideoDTO.DTOToVo(videoDTO);
        log.info(videoVo.toString());
    }
}
