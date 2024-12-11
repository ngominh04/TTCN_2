package com.example.TTCN2.controller;

import com.example.TTCN2.domain.ChatBox;
import com.example.TTCN2.domain.ChatBoxDetail;
import com.example.TTCN2.repository.ChatBoxDetailRepository;
import com.example.TTCN2.repository.ChatBoxRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    ChatBoxDetailRepository chatBoxDetailRepository;
    @Autowired
    ChatBoxRepository chatBoxRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")  // Địa chỉ URL mà client gửi tin nhắn tới
    public void sendMessage(@Payload ChatBoxDetail message) {
        // Kiểm tra người gửi và đảm bảo rằng admin3 có thể gửi tới mọi người,
        // còn người dùng khác chỉ có thể gửi đến admin3
        if ("admin3".equals(message.getSender())) {
            // Nếu là admin3, gửi tin nhắn đến người nhận cụ thể
            messagingTemplate.convertAndSendToUser(String.valueOf(message.getIdSender()), "/queue/reply", message);
        } else {
//            // Nếu là người dùng khác, chỉ có thể gửi tin nhắn đến admin3
//            if (!"admin3".equals(message.getRecipient())) {
//                throw new IllegalArgumentException("ban chi cos the gui tin nhan toi admin3.");
//            }
            messagingTemplate.convertAndSendToUser("admin3", "/queue/reply", message); // Gửi đến admin3
        }
        chatBoxDetailRepository.save(message);
    }

}
