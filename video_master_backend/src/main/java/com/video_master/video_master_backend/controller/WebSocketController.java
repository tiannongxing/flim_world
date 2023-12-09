package com.video_master.video_master_backend.controller;

import com.video_master.video_master_backend.util.VideoMasterWebSocketHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

@RestController
public class WebSocketController {

    @GetMapping("/send/{msg}")
    public HttpStatus sendMessage(@PathVariable String msg) {
        VideoMasterWebSocketHandler myWebSocketHandler1 = new VideoMasterWebSocketHandler();
        myWebSocketHandler1.broadcast(new TextMessage(msg));
        return HttpStatus.OK;
    }
}
