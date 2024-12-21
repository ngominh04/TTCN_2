package com.example.TTCN2.repository;

import com.example.TTCN2.domain.ChatBox;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatBoxRepository extends JpaRepository<ChatBox,Integer> {
    //get all
    @Query(value ="select * from chat_box",nativeQuery = true)
    List<ChatBox> getAllChatBoxes();

    // get by id
    @Query(value = "select * from chat_box where id=?",nativeQuery = true)
    ChatBox getChatBoxById(Integer idChatBox);

    // get by id user
    @Query(value = "select * from chat_box where id_user=?",nativeQuery = true)
    ChatBox getChatBoxByIdUser(Integer idUser);
    // get by id shipper
    @Query(value = "select * from chat_box where id_shipper=?",nativeQuery = true)
    ChatBox getChatBoxByIdShipper(Integer idShipper);
}
