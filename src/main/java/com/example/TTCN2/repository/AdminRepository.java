package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // ktra tai khoan theo username admin
    @Query(value= SQL.CHECK_LOGIN_ADMIN,nativeQuery = true)
    Admin getAdmin(String username);

    // get admin according to idAdmin
    @Query(value = "select * from admin where id=?",nativeQuery = true)
    Admin getAdminById(Integer idAdmin);
}