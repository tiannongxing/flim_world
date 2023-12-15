package com.video_master.video_master_backend.config;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


// 用于websocket 的多线程线程池配置
@Configuration
@ConfigurationProperties(prefix = "spring.websocket.task.execution.pool")
@EnableAsync
@Data
// websocket线程池
public class SocketExecutorConfig {
    // 使用lombok自动生成属性的setter方法，便于从配置文件中读取信息
    private Integer coreSize;
    private Integer maxSize;
    private Integer queueLength;


    /*
     *   @Resource
     *   private ThreadPoolTaskExecutor executor; // ThreadPoolTaskExecutor(spring core)是ThreadTaskExecutor(JDK.JUC)的一个扩展
     *
     */
    @Bean("socketExecutor")
    public ThreadPoolTaskExecutor socketExecutor(){
        ThreadPoolTaskExecutor socketExecutor = new ThreadPoolTaskExecutor();
        socketExecutor.setCorePoolSize(coreSize);
        socketExecutor.setMaxPoolSize(maxSize);
        socketExecutor.setQueueCapacity(queueLength);
        socketExecutor.setThreadNamePrefix("socketExecutor-");
        socketExecutor.initialize();

        return socketExecutor;
    }
}
