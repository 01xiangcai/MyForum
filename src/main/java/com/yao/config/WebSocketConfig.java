package com.yao.config;

import com.yao.handler.NotifitionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @className: WebSocketConfig
 * @Description: websocket配置类
 * @author: long
 * @date: 2023/4/1 14:59
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new NotifitionHandler(),"/notification").setAllowedOrigins("*");
    }
}
