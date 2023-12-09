package com.video_master.video_master_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

// 用于websocket 的多线程线程池配置
@Configuration
@EnableAsync
public class ExecutorConfiguration {

}
