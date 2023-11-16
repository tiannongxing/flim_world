package com.video_master.video_master_backend;

import com.video_master.video_master_backend.util.DynaSQLProviderBuilder;
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

}
