package com.video_master.video_master_backend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileIOUtil {
    public static void writeMultipartFile (String uploadDir,MultipartFile ...files){
        try {
            // 确保上传目录存在
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Arrays.stream(files).forEach(file -> {
                    // 获取原始文件名
                    String originalFileName = file.getOriginalFilename();

                    // 构建目标文件的完整路径
                    Path filePath = uploadPath.resolve(originalFileName);

                    // 将文件内容写入目标文件
                try {
                    file.transferTo(filePath.toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
