package com.example.TTCN2.repository;

import com.example.TTCN2.domain.User;
import com.example.TTCN2.sql.SQL;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // ktra tai khoan theo username
    @Query(value= SQL.CHECK_LOGIN,nativeQuery = true)
    User getCustomer(String username);
    // lay toan bo user
    @Query(value = SQL.ALL_USER,nativeQuery = true)
    List<User> getAll();
    // get User according to idCus
    @Query(value = SQL.USER_BY_IDUSER,nativeQuery = true)
    User getCustomerById(Integer idCus);
}