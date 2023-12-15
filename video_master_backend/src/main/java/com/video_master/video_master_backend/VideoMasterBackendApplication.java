package com.video_master.video_master_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class VideoMasterBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoMasterBackendApplication.class, args);
    }

}
