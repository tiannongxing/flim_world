package com.video_master.video_master_backend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoCategoryVo {
    private List<String> category;
    private Map<Integer,List<String>> subCategory;
}
