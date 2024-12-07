package com.example.TTCN2.controller;

import com.example.TTCN2.domain.ChatBoxDetail;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")  // Địa chỉ URL mà client gửi tin nhắn tới
    public void sendMessage(ChatBoxDetail message) {
        messagingTemplate.convertAndSend("/topic/messages", message);  // Gửi tin nhắn tới tất cả các client đã subscribe vào /topic/messages
    }
}
