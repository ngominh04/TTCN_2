package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Tree;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Integer> {
    @Query(value = SQL.ALL_TREES,nativeQuery = true)
    List<Tree> findAllTree();
}