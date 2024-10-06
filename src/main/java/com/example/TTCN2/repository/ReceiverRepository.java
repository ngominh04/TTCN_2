package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Receiver;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Integer> {

    // give all receiver to idUser
    @Query(value = SQL.ALL_RECEIVER_BY_IDUSER,nativeQuery = true)
    List<Receiver> getAllByReceiverId(Integer idUser);

    // get receiver by idRece
    @Query(value = SQL.RECEIVER_BY_IDRECE,nativeQuery = true)
    Receiver findAllById(Integer idRece);
}