package com.liu.nativedemo.configuration;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuyi
 * @date 2019/12/11
 */
public class WsSessionManager {

    /**
     * 简易连接session池
     */
    private static ConcurrentHashMap<String, WebSocketSession> sessionPool = new ConcurrentHashMap<>();

    /**
     * 添加session
     */
    public static void add(String key, WebSocketSession session){
        sessionPool.put(key, session);
    }

    /**
     * 删除session，并返回删除的session
     */
    public static WebSocketSession remove(String key){
        return sessionPool.remove(key);
    }

    /**
     * 删除并关闭session
     */
    public static void removeAndClose(String key){
        WebSocketSession session = sessionPool.remove(key);
        if (session != null){
            try {
                // 关闭session
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取session
     */
    public static WebSocketSession get(String key) {
        // 获得session
        return sessionPool.get(key);
    }

}
