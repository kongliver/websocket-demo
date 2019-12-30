package com.liu.annotationdemo.controller;

import com.liu.annotationdemo.configuration.WebSockerServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyi
 * @date 2019/12/10
 */
@RestController
public class TestController {

    /**
     * 向客户端推送数据
     * @param flag 标识哪个服务端
     * @param message 推送的消息
     * @return 结果
     */
    @RequestMapping("/socket/push/{flag}")
    public String pushToClient(@PathVariable("flag") String flag, String message){
        WebSockerServer.sendToMessage(message, flag);
        return "ok->" + flag;
    }

    @RequestMapping("/socket/push/all")
    public String pushAll(String message){
        WebSockerServer.sendToMessage(message, null);
        return "ok->all";
    }

}
