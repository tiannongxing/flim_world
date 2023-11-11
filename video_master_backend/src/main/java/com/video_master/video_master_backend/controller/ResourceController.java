package com.video_master.video_master_backend.controller;

import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.services.VideoServices;
import com.video_master.video_master_backend.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/resource")
public class ResourceController {
    @Resource
    private VideoServices videoServices;
   
    @GetMapping("/getByName")
    public String getVideoData(@RequestParam String name) {
        List<VideoEntity> returnVideo = videoServices.getVideoByName(name);
        String json = JackonUtil.ListToJson(returnVideo);
        return json;
    }
}
