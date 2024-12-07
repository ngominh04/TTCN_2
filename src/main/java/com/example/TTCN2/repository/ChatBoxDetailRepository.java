package com.example.TTCN2.repository;

import com.example.TTCN2.domain.ChatBoxDetail;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatBoxDetailRepository extends JpaRepository< ChatBoxDetail, Integer> {
    // get all detail chat by idChatBox
    @Query(value = SQL.SHOW_CHAT_BOX_BY_ID_CHAT_BOX,nativeQuery = true)
    List<ChatBoxDetail> findChatBoxByChatBoxId(Integer chatBoxId);
}
