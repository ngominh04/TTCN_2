package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chat_box_detail")
public class ChatBoxDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat_box")
    private ChatBox idChatBox;

    @Column(name = "content" , length = 500)
    private String content;

    @Column(name = "date")
    private String date;

    @Column(name = "sender", length = 150)
    private String sender;

//    @Lob
//    trang thai da xem hay chua
    @Column(name = "is_viewed")
    private Integer isViewed;

//    public enum MessageType {
//        CHAT, LEAVE, JOIN
//    }
}