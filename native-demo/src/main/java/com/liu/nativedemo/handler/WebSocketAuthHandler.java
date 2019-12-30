package com.liu.nativedemo.handler;

import com.liu.nativedemo.configuration.WsSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;

/**
 * @author liuyi
 * @date 2019/12/11
 */
@Component
public class WebSocketAuthHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object token = session.getAttributes().get("token");
        if (token != null){
            // 客户端连接成功，放入在线客户端缓存
            WsSessionManager.add(token.toString(), session);
        } else {
            throw new RuntimeException("客户端连接已失效！");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 接收客户端传来的消息
        String payload = message.getPayload();
        Object token = session.getAttributes().get("token");
        System.out.println("服务端接收到-" + token + "-发送的消息：" + payload);
        session.sendMessage(new TextMessage("服务端接收到-" + token + "-发送的消息：" + payload
                + " " + LocalDateTime.now().toString()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object token = session.getAttributes().get("token");
        if (token != null){
            // 客户端断连，移除缓存
            WsSessionManager.remove(token.toString());
        }
    }
}
