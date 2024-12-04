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

    @Column(name = "content")
    private Integer content;

    @Column(name = "date")
    private Instant date;

    @Column(name = "sender", length = 150)
    private String sender;

    @Lob
    @Column(name = "type")
    private MessageType type;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }
}