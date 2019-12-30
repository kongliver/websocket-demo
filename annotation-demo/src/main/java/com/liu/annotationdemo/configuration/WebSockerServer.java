package com.liu.annotationdemo.configuration;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author liuyi
 * @date 2019/12/10
 */
@Component
@ServerEndpoint("/test/websocket/{flag}")
public class WebSockerServer {

    /**
     * 记录当前在线连接数
     */
    private static int onlineCount = 0;

    /**
     * 简易websocket客户端池
     */
    private static CopyOnWriteArraySet<WebSockerServer> serverPool = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，通过它给客户端发送消息
     */
    private Session session;

    /**
     * 标识哪个客户端
     */
    private String flag = "";

    /**
     * 连接建立成功后调用
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("flag") String flag){
        this.session = session;
        serverPool.add(this);
        // 在线人数加1
        addOnlineCount();
        this.flag = flag;
        System.out.println("有新窗口" + flag + "开始监听，当前在线人数为：" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose(){
        // 从池中删除
        serverPool.remove(this);
        // 在线人数减1
        subOnlineCount();
        System.out.println("有一个连接关闭，当前在线人数为：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println("接收到来自" + flag + "的消息为：" + message);
        try {
            this.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 手动群发自定义消息
     * @param message 发送的消息
     */
    public static void sendToMessage(String message, String flag){
        System.out.println("推送消息到" + flag + "窗口，推送内容为：" + message);
        for (WebSockerServer server : serverPool) {
            // 可以根据flag推送给某个特定的客户端，如果flag为null时则全部推送
            try {
                if (flag == null || flag.equals(server.flag)){
                    server.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private static synchronized int getOnlineCount(){
        return onlineCount;
    }

    private static synchronized void addOnlineCount(){
        onlineCount++;
    }

    private static synchronized void subOnlineCount(){
        onlineCount--;
    }

}
