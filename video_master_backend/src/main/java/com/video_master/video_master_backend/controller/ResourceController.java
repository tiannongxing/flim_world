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

import java.util.ArrayList;
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

    @GetMapping("/getAllOptionByName")
    public String getAllOption(@RequestParam String targetType){
        List<List<String>> lists = new ArrayList<>();
        List<String> era = videoServices.getEra(targetType);
        List<String> location = videoServices.getLocation(targetType);
        List<String> allType = videoServices.getAllType(targetType);
        lists.add(era);
        lists.add(location);
        lists.add(allType);
        String s = JackonUtil.ListToJson(lists);
        return s;
    }

    @GetMapping("/getMoviesLikeName")
    public String getMoviesLikeName(@RequestParam String movieName){

        return "true";
    }
}
