package com.video_master.video_master_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final SocketExecutorConfig socketExecutor;

    public WebSocketConfiguration(SocketExecutorConfig socketExecutor){
        this.socketExecutor = socketExecutor;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 添加控制器和拦截器 拦截器还没有写 并设置允许跨域
        registry.addHandler(myHandler(), "/websocket").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new VideoMasterWebSocketHandler(socketExecutor);
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);  //文本消息最大缓存
        container.setMaxBinaryMessageBufferSize(8192);  //二进制消息大战缓存
        container.setAsyncSendTimeout(10L * 1000); //异步发送超时时间
        return container;
    }
}
