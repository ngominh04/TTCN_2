package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Tree;
import com.example.TTCN2.projection.ICategory;
import com.example.TTCN2.projection.ITrees;
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

    // lay tree theo id
    @Query(value = "select * from trees where id=?",nativeQuery = true)
    Tree findAllById(Integer idPro);

    // lay theo id category
    @Query(value = SQL.ALL_TREES_TO_ID_CATEGORY,nativeQuery = true)
    List<Tree> findAllTreeToIdCategory(Integer idCate);

    // dem so luong cay theo category
    @Query(value = SQL.COUNT_TREES_TO_ID_CATEGORY,nativeQuery = true)
    ICategory findCategoryById(Integer idCate);

    // count tree
    @Query(value = SQL.COUNT_TREES,nativeQuery = true)
    ITrees countAllById();
}