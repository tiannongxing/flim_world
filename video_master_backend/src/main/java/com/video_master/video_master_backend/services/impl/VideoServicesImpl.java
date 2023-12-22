package com.video_master.video_master_backend.services.impl;

import com.video_master.video_master_backend.mapper.VideoMapper;
import com.video_master.video_master_backend.model.dto.VideoCategoryDTO;
import com.video_master.video_master_backend.model.dto.VideoPlayerDTO;
import com.video_master.video_master_backend.model.dto.VideoSearchedDTO;
import com.video_master.video_master_backend.model.entity.VideoCategoryEntity;
import com.video_master.video_master_backend.model.entity.VideoEntity;
import com.video_master.video_master_backend.model.entity.VideoPlayerEntity;
import com.video_master.video_master_backend.model.vo.VideoCategoryVo;
import com.video_master.video_master_backend.model.vo.VideoPlayerVo;
import com.video_master.video_master_backend.services.VideoServices;
import com.video_master.video_master_backend.util.FtpUtil;
import com.video_master.video_master_backend.util.VideosAttributes;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongFunction;

@Service
@Slf4j
public class VideoServicesImpl implements VideoServices {
    @Resource
    private VideoMapper videoMapper;


    @Value("${store.ftp.host}")
    private String ftpHost;
    @Value("${store.ftp.port}")
    private String ftpPort;
    @Value("${store.ftp.user}")
    private String user;
    @Value("${store.ftp.password}")
    private String password;
    private static final String IMG_REG = "^(image\\/(jpeg|png|gif))$";

    private static final String VIDEO_REG = "^(video\\/(mp4|x-msvideo|quicktime))$";

    @Override
    public List<VideoEntity> getVideoByName(String name) {
        return videoMapper.getVideoByName(name);
    }

    @Override
    public List<String> getEra(String type) {
        return videoMapper.getVideoEra(type);
    }

    @Override
    public List<String> getLocation(String type) {
        return videoMapper.getVideoLocation();
    }

    @Override
    public List<String> getAllType(String type) {
        return videoMapper.getVideoType(type);
    }

    @Override
    public List<VideoEntity> getVideoLikeName(String name) {
        return videoMapper.getVideoLikeName(name);
    }

    @Override
    public VideoEntity getVideoById(Integer id) {
        return videoMapper.getVideoById(id);
    }

    @Override
    public String videoLocation(Integer id) {
        return videoMapper.getVideoLocationById(id);
    }

    @Override
    public List<String> videoCategory(Integer id) {
        return videoMapper.getVideoTypeById(id);
    }

    @Override
    public String videoType(Integer typeId) {
        return videoMapper.getVideoTrueTypeById(typeId);
    }

    @Override
    public List<VideoEntity> getVideos(Map<String, String> params) {
        // 根据参数的个数和类型动态搜索
        if (params.get(VideosAttributes.TARGET_PAGE) != null) {
            int offset = (Integer.parseInt(params.get(VideosAttributes.TARGET_PAGE)) - 1) * 30;
            params.put(VideosAttributes.TARGET_OFFSET, String.valueOf(offset));
        }
        List<VideoEntity> videos = videoMapper.getVideos(params);
        return videos;
    }

    @Override
    public Integer getAllVideosByParams(Map<String, String> params) {
        return videoMapper.getAllVideosByParams(params);
    }

    @Override
    public List<VideoSearchedDTO> getSearchedVideoLikeName(Map<String, String> params) {
        return videoMapper.getSearchedVideoLikeName(params);
    }

    @Override
    public Integer getAllVideosLikeName(Map<String, String> params) {
        return videoMapper.getAllVideosLikeName(params);
    }

    @Override
    public VideoPlayerVo getPlayerVideoByIdAndCurrentEpisode(Integer id, Integer currentEpisode) {
        VideoPlayerEntity videoPlayerEntity = videoMapper.selectVideoByIdAndCurrentEpisode(id, currentEpisode);
        VideoPlayerVo videoPlayerVo = VideoPlayerDTO.DTOToVo(VideoPlayerDTO.entityToDTO(videoPlayerEntity));
        return videoPlayerVo;
    }

    @Override
    public Boolean writeFileToDisk(MultipartFile... files) {
        AtomicReference<Boolean> success = new AtomicReference<>(false);
        Arrays.stream(files).forEach(file -> {
            // 匹配文件格式并避免乱码问题
            String remoteFilePath = "";
            if (file.getContentType().matches(IMG_REG)) {
                remoteFilePath = "imgs/resources/";
            } else if (file.getContentType().matches(VIDEO_REG)) {
                remoteFilePath = "video/resources/";
            }
            try (InputStream inputStream = file.getInputStream()) {

                success.set(FtpUtil.uploadFile(ftpHost, Integer.parseInt(ftpPort),user,password,
                        // 设置传输的文件编码为UTF-8格式
                        remoteFilePath + new String(file.getOriginalFilename().getBytes(), StandardCharsets.UTF_8),
                        inputStream));

                log.info(String.valueOf(success.get()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        return success.get();
    }

    @Override
    public VideoCategoryVo getVideoCategory() {
        List<VideoCategoryEntity> videoCategoryList = videoMapper.getVideoCategoryList();
        VideoCategoryVo videoCategoryVo = VideoCategoryDTO.entityToVo(videoCategoryList);

        return videoCategoryVo;
    }

    @Override
    public List<String> getLocations() {
        return videoMapper.getAllLocation();
    }
}


