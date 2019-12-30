package com.liu.nativedemo.configuration;

import com.liu.nativedemo.handler.WebSocketAuthHandler;
import com.liu.nativedemo.interceptor.WsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @author liuyi
 * @date 2019/12/11
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private WebSocketAuthHandler authHandler;

    @Resource
    private WsInterceptor wsInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(authHandler, "test/websocket").addInterceptors(wsInterceptor).setAllowedOrigins("*");
    }

}
