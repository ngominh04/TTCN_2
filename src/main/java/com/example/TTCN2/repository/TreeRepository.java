package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Tree;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Integer> {
    // lay toan bo cay
    @Query(value = SQL.ALL_TREES,nativeQuery = true)
    List<Tree> findAllTree();

    // xem chi tiet cay
    @Query(value = SQL.DETAIL_TREE,nativeQuery = true)
    Tree findDetailTreeById(Integer id);

    // SAN PHAM CUNG CATEGORY (TRU SAN PHAM DANG XEM CHI TIET)
    @Query(value = SQL.TREES_CATEGORY_EXCLUDED_DETAIL_TREE,nativeQuery = true)
    List<Tree> findDetailTree_CategoryId(Integer idCate,Integer idTree);
}