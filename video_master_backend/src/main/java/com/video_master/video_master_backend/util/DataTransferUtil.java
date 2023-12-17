package com.video_master.video_master_backend.util;

import com.video_master.video_master_backend.services.VideoServices;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataTransferUtil {
    @Resource
    private VideoServices videoServices;

    private static DataTransferUtil dsf;

    @PostConstruct
    public void init(){
        dsf = this;
    }

    public static String getVideoType(Integer typeId){
        return dsf.videoServices.videoType(typeId);
    }

    public static List<String> getVideoCategory(Integer id){
        return dsf.videoServices.videoCategory(id);
    }

    public static String getVideoLocation(Integer locationId){
        return dsf.videoServices.videoLocation(locationId);
    }
}
