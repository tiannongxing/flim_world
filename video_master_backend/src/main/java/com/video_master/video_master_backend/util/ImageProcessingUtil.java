package com.video_master.video_master_backend.util;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

@Slf4j
public class ImageProcessingUtil {

    public static File convertBase64ToFile(String base64String,String filePath) {
        // 解码base64格式字符串
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        File file = new File(filePath);

        // 使用FileOutPutStream 将写入文件
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(decodedBytes);
        }catch (Exception e){
           log.info(String.valueOf(e));
        }

        return file;
    }
}
