package com.example.TTCN2.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Xử lý tin nhắn từ client và gửi lại cho tất cả các client
        String messageContent = message.getPayload();
        System.out.println("Received message: " + messageContent);

        // Gửi lại tin nhắn cho tất cả các client kết nối
        session.sendMessage(new TextMessage("Server: " + messageContent));
    }
}
