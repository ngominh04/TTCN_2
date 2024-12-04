package com.example.TTCN2.controller;

import com.example.TTCN2.domain.ChatBoxDetail;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatBoxDetail sendMessage(@Payload ChatBoxDetail chatBoxDetail) {
        return chatBoxDetail;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatBoxDetail addUser(@Payload ChatBoxDetail chatBoxDetail,
                                 SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatBoxDetail.getSender());
        return chatBoxDetail;
    }
}
