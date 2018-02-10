package org.jypj.zgcsx.controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jian_wu on 2017/11/16.
 */
@ServerEndpoint("/websocket/{userId}")
public class WebsocketController {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentMap<String,Session> webSocketMap = new ConcurrentHashMap<>();


    /***
     * 给指定用户发更新消息
     * @param userId
     */
    public static void refreshCanlendar(String userId){
        try {
            Set<String> keys=webSocketMap.keySet();
            for(String key:keys){
                if(key.equals(userId)){
                    Session session = webSocketMap.get(userId);
                    session.getBasicRemote().sendText("refresh");
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session){
        webSocketMap.put(userId, session);     //加入map中

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId,Session session){
        try {
            webSocketMap.remove(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(@PathParam("myWebsocket") String myWebsocket,String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(@PathParam("myWebsocket") String myWebsocket,Session session, Throwable error){
        //System.out.println("发生错误,连接中断");
        //error.printStackTrace();
    }


}
