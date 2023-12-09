package com.video_master.video_master_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{uid}")
@Slf4j
public class WebSocketController {
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    // concurrent 包的线程安全 Set，用来存放每个客户端对应的 WebSocket 对象。
    private static ConcurrentHashMap<String, WebSocketController> webSocketSet = new ConcurrentHashMap<String, WebSocketController>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 接收客户端传来的sid
    private String uid = "";

    // 连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) {
        // 实现多个用户同时在线，需要用到线程安全的操作，此外还需要开发一个线程池 用来存放每个客户端对应的 WebSocket 对象。
        // 前台开启不同的聊天页面，每个页面都会创建一个 WebSocket 对象，这些对象都需要存放在线程池中，以便于后台向不同的客户端发送消息。
        this.session = session;
        if (webSocketSet.containsKey(uid)) {
            webSocketSet.remove(uid);
            webSocketSet.put(uid, this);
        } else {
            webSocketSet.put(uid, this);
            // 在线数加1
            addOnlineCount();
        }

        log.info("用户【" + uid + "】连接成功，当前在线人数为:" + getOnlineCount());

        try {
            sendMsg("连接成功");
        } catch (IOException e) {
            log.error("用户【" + uid + "】网络异常!", e);
        }
    }

    // 连接关闭调用的方法
    @OnClose
    public void onClose() {
        if (webSocketSet.containsKey(uid)) {
            webSocketSet.remove(uid);
            // 在线数减1
            subOnlineCount();
        }
        log.info("用户【" + uid + "】退出成功，当前在线人数为:" + getOnlineCount());
    }

    //接收到客户端消息时调用的方法
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户【" + uid + "】发送消息:" + message);
        // 群发消息
        //群发消息
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> map = objectMapper.readValue(message, Map.class);;
                //追加发送人(防止串改)
                map.put("fromUID", this.uid);
                String toUID = map.get("toUID");
                //传送给对应的toUserId用户的WebSocket
                if (StringUtils.isNotBlank(toUID) && webSocketSet.containsKey(toUID)) {
                    webSocketSet.get(toUID).sendMsg(objectMapper.writeValueAsString(map));
                } else {
                    //若果不在这个服务器上，可以考虑发送到mysql或者redis
                    log.error("请求目标用户【" + toUID + "】不在该服务器上");
                }
            } catch (Exception e) {
                log.error("用户【" + uid + "】发送消息异常！", e);
            }
        }
    }


    // 处理错误
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户【" + this.uid + "】处理消息错误，原因:" + error.getMessage());
        error.printStackTrace();
    }

    // 实现服务器主动推送
    private void sendMsg(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }

    // 发送自定义消息
    public static void sendInfo(String message, @PathParam("uid") String uid) throws IOException {
        log.info("发送消息到用户【" + uid + "】发送的报文:" + message);
        if (!StringUtils.isEmpty(uid) && webSocketSet.containsKey(uid)) {
            webSocketSet.get(uid).sendMsg(message);
        } else {
            log.error("用户【" + uid + "】不在线！");
        }
    }


    private static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }


    private static synchronized String getOnlineCount() {
        return String.valueOf(WebSocketController.onlineCount);
    }

    private static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }
}
