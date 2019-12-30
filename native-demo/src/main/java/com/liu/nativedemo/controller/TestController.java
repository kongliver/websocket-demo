package com.liu.nativedemo.controller;

import com.liu.nativedemo.configuration.WsSessionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @author liuyi
 * @date 2019/12/11
 */
@RestController
public class TestController {

    @RequestMapping("/send/msg")
    public String sendMessage(String token, String message) throws IOException {
        WebSocketSession session = WsSessionManager.get(token);
        if (session == null) {
            return "客户端连接失效";
        }
        session.sendMessage(new TextMessage(message));
        return "发送消息成功，消息为：" + message;
    }

}
