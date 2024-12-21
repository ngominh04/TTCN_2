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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        System.out.println(message.getContent());
        if ("admin3".equals(message.getSender())) {
            // Nếu là admin3, gửi tin nhắn đến người nhận cụ thể
            messagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/reply", message);
        } else {
           // Nếu là người dùng khác, chỉ có thể gửi tin nhắn đến admin3
            messagingTemplate.convertAndSendToUser("admin3", "/queue/reply", message); // Gửi đến admin3
            //messagingTemplate.convertAndSend("/topic/messages", message); // Gửi đến topic (gui toi moi nguoi)
        }
        chatBoxDetailRepository.save(message);
    }

    // show all chat
    @GetMapping("/admin/showChat")
    public String showChat(Model model) {
        model.addAttribute("chat",chatBoxRepository.findAll());
        return "admin/message/showMessage";
    }
    // show chat theo id chat_box
    @GetMapping("/admin/chat/{idBoxChat}")
    public String showChatDetail(@PathVariable("idBoxChat") Integer idBoxChat, Model model) {
        model.addAttribute("chat",chatBoxRepository.getChatBoxById(idBoxChat));
        model.addAttribute("box_chat",chatBoxDetailRepository.findChatBoxByChatBoxId(idBoxChat));
        return "admin/message/detailMessage";
    }
}
