package com.liu.stompdemo.controller;

import com.liu.stompdemo.pojo.Greeting;
import com.liu.stompdemo.pojo.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liuyi
 * @date 2019/12/11
 */
@Controller
public class TestController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greeting")
    public Greeting greeting(HelloMessage message){
        System.out.println("接收到消息：" + message.getMessage());
        return new Greeting("服务端收到消息了，现在广播：" + message.getMessage());
    }

    @ResponseBody
    @RequestMapping("/send/msg/user")
    public String sendMsgToUser(String token, String msg){
        simpMessagingTemplate.convertAndSendToUser(token, "/msg", msg);
        return "success->" + token;
    }

    @ResponseBody
    @RequestMapping("/send/msg/all")
    public String sendMsgToAll(String msg){
        simpMessagingTemplate.convertAndSend("/topic", msg);
        return "success";
    }

}
