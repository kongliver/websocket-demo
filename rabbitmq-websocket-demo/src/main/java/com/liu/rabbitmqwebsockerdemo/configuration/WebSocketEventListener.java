package com.liu.rabbitmqwebsockerdemo.configuration;

import com.liu.rabbitmqwebsockerdemo.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author liuyi
 * @date 2019/12/24
 */
@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleConnectListener(SessionConnectedEvent event){
        System.out.println("收到一个新websocket连接");
    }

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = accessor.getSessionAttributes().get("username") == null ? null : accessor.getSessionAttributes().get("username").toString();
        if (username != null){
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType("Leave");
            chatMessage.setSender(username);
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}
