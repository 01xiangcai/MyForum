package com.yao.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yao.entity.Article;
import com.yao.entity.ArticleComment;
import com.yao.entity.Question;
import com.yao.entity.dto.NotifitionDto;
import com.yao.entity.vo.NotificationVo;
import com.yao.mapper.ArticleCommentMapper;
import com.yao.mapper.ArticleMapper;
import com.yao.mapper.QuestionMapper;
import com.yao.mapper.UserMapper;
import com.yao.service.NotifitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @className: NotifationHandler
 * @Description: 实时通知处理器
 * @author: long
 * @date: 2023/4/1 15:03
 */
@Component
public class NotifitionHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Autowired
    NotifitionService notifitionService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // do nothing
    }


    public void sendNotification(NotifitionDto notifitionDto)  {

        NotificationVo notificationVo = notifitionService.getNotificationVo(notifitionDto);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(notificationVo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        String json = new ObjectMapper().writeValueAsString(notificationVo);

        TextMessage message = new TextMessage(json);
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
