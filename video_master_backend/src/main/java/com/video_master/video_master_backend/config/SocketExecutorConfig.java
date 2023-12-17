package com.video_master.video_master_backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;


// 用于websocket 的多线程线程池配置
@Component
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
    public ThreadPoolTaskExecutor socketExecutor() {
        // 初始化socket线程池
        ThreadPoolTaskExecutor socketExecutor = new ThreadPoolTaskExecutor();
        socketExecutor.setCorePoolSize(coreSize);
        socketExecutor.setMaxPoolSize(maxSize);
        socketExecutor.setQueueCapacity(queueLength);
        socketExecutor.setThreadNamePrefix("socketExecutor-");
        /* 设置线程池拒绝策略，当线程和队列都满时会调用
           1. AbortPolicy 拒绝并抛出异常
           2. DiscardPolicy 拒绝，不抛出异常
           3. DiscardOldestPolicy  抛弃最老的线程接收最新的线程
           4. CallRunsPolicy  由调用线程处理该任务
         */
        socketExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        socketExecutor.initialize();

        return socketExecutor;
    }
}
