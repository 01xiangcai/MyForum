package com.yao.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yao.entity.dto.NotifitionDto;
import com.yao.entity.vo.NotificationVo;
import com.yao.service.NotifitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: NotifationHandler
 * @Description: 实时通知处理器
 * @author: long
 * @date: 2023/4/1 15:03
 */
@Component
public class NotifitionHandler extends TextWebSocketHandler {

//    private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    //定义一个map来存储用户与websocket的映射关系
    private static Map<Long, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Autowired
    NotifitionService notifitionService;

    //缓存
    @Autowired
    CacheManager cacheManager;



    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        //获取当前用户的id，将与其对应的连接存储起来
        Long userId = getUserId(session);
        if (userId != null) {
            sessionMap.put(userId, session);
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        //关闭连接时，移除映射关系，防止内存泄漏
        Long userId = getUserId(session);
        if (userId != null) {
            sessionMap.remove(userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // do nothing
    }


    //给前端发送通知
    public void sendNotification(NotifitionDto notifitionDto) {
        System.out.println("cacheManager = " + cacheManager);
        if (cacheManager != null) {
            //从缓存中拿通知对象id
            Cache notificationReceiverIdCache = cacheManager.getCache("notificationReceiverIdCache");
            Cache.ValueWrapper receiverId1 = notificationReceiverIdCache.get("notificationReceiverId");
            Long receiverId = receiverId1 != null ? (Long) receiverId1.get() : null;
            System.out.println("receiverId = " + receiverId);

            //对通知的信息进行处理发送
            NotificationVo notificationVo = notifitionService.getNotificationVo(notifitionDto);
            //解决时间格式化
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String json = null;
            try {
                json = objectMapper.writeValueAsString(notificationVo);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            TextMessage message = new TextMessage(json);

            //从map中找到对应的连接发送消息
            WebSocketSession session = sessionMap.get(receiverId);
            if (session != null && session.isOpen()) {
                try {
                    session.sendMessage(message);
                    System.out.println("session.sendMessage(message);");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }


    private Long getUserId(WebSocketSession session) {
        String userIdStr = session.getUri().getQuery().split("=")[1];
        try {
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
