package com.video_master.video_master_backend.controller;

import com.video_master.video_master_backend.model.dto.VideoDTO;
import com.video_master.video_master_backend.model.dto.VideoListsDTO;
import com.video_master.video_master_backend.model.dto.VideoSearchedDTO;
import com.video_master.video_master_backend.model.dto.VideoSearchedListDTO;
import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.vo.VideoCategoryVo;
import com.video_master.video_master_backend.model.vo.VideoPlayerVo;
import com.video_master.video_master_backend.model.vo.VideoVo;
import com.video_master.video_master_backend.services.VideoServices;
import com.video_master.video_master_backend.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    public String getAllOption(@RequestParam String targetType) {
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


    @GetMapping("/getMovieDetailById")
    public String getMovieDetailById(@RequestParam Integer mid) {
        // 通过id查找影片信息，返回影片的json格式字符串
        VideoVo videoVo = VideoDTO.DTOToVo(VideoDTO.entityToDTO(videoServices.getVideoById(mid)));
        String res = JackonUtil.ObjectToJSON(videoVo);
        return res;
    }

    @GetMapping("/getVideos")
    public String getVideos(@RequestParam Map<String, String> params) {
        // 直接传入params，根据params里的key 做筛选
        List<VideoEntity> videos = videoServices.getVideos(params);

        VideoListsDTO videoListsDTO = VideoListsDTO.builder()
                .videoList(videos)
                .total(videoServices.getAllVideosByParams(params))
                .build();
        String returnJson = JackonUtil.ObjectToJSON(videoListsDTO);
        log.info(returnJson);
        return returnJson;
    }

    @GetMapping("/getMoviesLikeName")
    public String getMoviesLikeName(@RequestParam Map<String, String> params) {
        List<VideoSearchedDTO> searchedVideoLikeName = videoServices.getSearchedVideoLikeName(params);
        VideoSearchedListDTO videoSearchedListDTO = VideoSearchedListDTO.builder()
                .videoList(searchedVideoLikeName)
                .total(videoServices.getAllVideosLikeName(params))
                .build();

        String returnJson = JackonUtil.ObjectToJSON(videoSearchedListDTO);
        return returnJson;
    }


    @GetMapping("/getPlayingCorrect")
    public String getPlayingCorrect(@RequestParam Map<String, String> params) {
        List<String> paramList = params.values().stream().toList();
        VideoPlayerVo playerVideo = videoServices.getPlayerVideoByIdAndCurrentEpisode(Integer.parseInt(paramList.get(0)), Integer.parseInt(paramList.get(1)));
        return JackonUtil.ObjectToJSON(playerVideo);
    }

    @PostMapping("/videoUpload")
    public Boolean videoUpload(@RequestPart("video") MultipartFile video,
                              @RequestPart("image") MultipartFile image) throws IOException {

        return videoServices.writeFileToDisk(video,image);
    }

    @GetMapping("/getVideoCategory")
    VideoCategoryVo getVideoCategory(){
        VideoCategoryVo videoCategory = videoServices.getVideoCategory();
        return videoCategory;
    }

    @GetMapping("/getAllLocations")
    List<String> getAllLocations(){
        return videoServices.getLocations();
    }
}