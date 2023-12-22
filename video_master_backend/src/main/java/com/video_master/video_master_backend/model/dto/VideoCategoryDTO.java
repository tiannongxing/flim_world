package com.video_master.video_master_backend.model.dto;

import com.video_master.video_master_backend.model.entity.VideoCategoryEntity;
import com.video_master.video_master_backend.model.vo.VideoCategoryVo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoCategoryDTO {
    public static VideoCategoryVo entityToVo(List<VideoCategoryEntity> vcel){
        return VideoCategoryVo.builder()
                .category(Arrays.stream(vcel.remove(0).getCategory().split("__")).toList())
                .subCategory(VideoCategoryDTO.getSubCategory(vcel))
                .build();
    }

    private static Map<Integer, List<String>> getSubCategory(List<VideoCategoryEntity> ce){
        Map<Integer,List<String>> map = new HashMap<Integer,List<String>>();
        ce.stream().forEach(item -> map.put(Integer.valueOf(item.getPid()), Arrays.stream(item.getCategory().split("__")).toList()));
        return map;
    }
}
