package com.liu.rabbitmqwebsockerdemo.controller;

import com.liu.rabbitmqwebsockerdemo.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * @author liuyi
 * @date 2019/12/24
 */
@Controller
public class TestController {

    @MessageMapping("/chat/sendMessage")
    @SendTo("/queue/test")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }

    @MessageMapping("/chat/newUser")
    @SendTo("/queue/test")
    public ChatMessage newUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor accessor){
        accessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
