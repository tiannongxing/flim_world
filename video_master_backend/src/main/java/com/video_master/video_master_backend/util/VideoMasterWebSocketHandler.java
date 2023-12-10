package com.video_master.video_master_backend.util;

import com.video_master.video_master_backend.model.entity.MsgSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class VideoMasterWebSocketHandler implements WebSocketHandler {

    //保存用户会话信息，用于服务端群发
    private static final ConcurrentHashMap<String, WebSocketSession> concurrentSet = new ConcurrentHashMap<String, WebSocketSession>();

    //保存当前会话用户名称和房间号
    private String uid;
    private String roomId;
    private static final String systemSender = "系统通知";

    private static int onlineCount = 0;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 获取到当前会话的用户名称和房间号
        getUidRoomId(session);
        String key = this.uid + "_" + this.roomId;
        if (concurrentSet.containsKey(key)) {
            concurrentSet.remove(key);
            concurrentSet.put(key, session);
        } else {
            concurrentSet.put(key, session);
            // 在线数加1
            addOnlineCount();
        }
        log.info("用户【" + uid + "】连接成功，当前在线人数为:" + getOnlineCount());

        MsgSender msg = MsgSender.builder().senderId("0").senderName(systemSender).content("您已成功连接到服务器。").time(generateCurrentTime()).build();
        String sendMsg = JackonUtil.ObjectToJSON(msg);
        sendMessage(new TextMessage(sendMsg), key);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 如果是一对一连接的话，需要确认发送地址
        MsgSender msgSender = JackonUtil.JsonToObject(message.getPayload().toString(), MsgSender.class);
        broadcast(new TextMessage(message.getPayload().toString()),msgSender.getRoomId());

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("WebSocket transport error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 获取到当前会话的用户名称和房间号
        getUidRoomId(session);
        String key = uid + "_" + roomId;
        if (concurrentSet.containsKey(key)) {
            concurrentSet.remove(key);
            // 在线数减1
            subOnlineCount();
            log.info("用户【" + uid + "】退出，当前在线人数为:" + getOnlineCount());
        } else {
            log.info("用户【" + uid + "】退出失败，当前在线人数为:" + getOnlineCount());
        }
    }

    private void getUidRoomId(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null) {
            Map<String, String> queryParams = parseQuery(query);
            this.uid = queryParams.get("uid");
            this.roomId = queryParams.get("roomId");
        } else {
            log.info("用户连接失败");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private Map<String, String> parseQuery(String query) {
        // 从请求的URL中解析出请求参数
        Map<String, String> queryParams = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                if (keyValue.length > 1) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return queryParams;
    }

    public void broadcast(WebSocketMessage<?> message, String roomId) {
        for (Map.Entry<String, WebSocketSession> unit : concurrentSet.entrySet()) {
            try {
                if (unit.getValue().isOpen() && Objects.equals(unit.getKey().split("_")[1],roomId)) {
                    unit.getValue().sendMessage(message);
                }
            } catch (Exception e) {
                // todo 输出到日志
                log.info(e.toString());
            }

        }
    }

    public void sendMessage(WebSocketMessage<?> message, String sender, String receiver) {
        // 应同时给发信者和收信者同时发送信息
        try {
            if (concurrentSet.containsKey(sender) && !Objects.equals(sender, null)) {
                sendMessageToUser(concurrentSet.get(sender), message);
            }
            if (concurrentSet.containsKey(receiver) && !Objects.equals(receiver, null)) {
                sendMessageToUser(concurrentSet.get(receiver), message);
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    public void sendMessage(WebSocketMessage<?> message, String receiver) {
        // 系统单方面发送信息
        try {
            if (concurrentSet.containsKey(receiver) && !Objects.equals(receiver, null)) {
                sendMessageToUser(concurrentSet.get(receiver), message);
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    private void sendMessageToUser(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }

    private String generateCurrentTime() {
        // 获取当前时分秒
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }

    private static synchronized void subOnlineCount() {
        VideoMasterWebSocketHandler.onlineCount--;
    }


    private static synchronized String getOnlineCount() {
        return String.valueOf(VideoMasterWebSocketHandler.onlineCount);
    }

    private static synchronized void addOnlineCount() {
        VideoMasterWebSocketHandler.onlineCount++;
    }


}
