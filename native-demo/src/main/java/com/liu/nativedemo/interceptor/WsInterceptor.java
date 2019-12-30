package com.liu.nativedemo.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyi
 * @date 2019/12/11
 */
@Component
public class WsInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("握手开始");
        // 获取请求参数
        HashMap<String, String> map = HttpUtil.decodeParamMap(request.getURI().getQuery(), "utf-8");
        String token = map.get("token");
        if (StrUtil.isNotBlank(token)){
            attributes.put("token", token);
            System.out.println("客户端" + token + "握手成功！");
            return true;
        }
        System.out.println("客户端已失效");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("握手完成");
    }
}
