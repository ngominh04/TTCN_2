package com.example.TTCN2.repository;

import com.example.TTCN2.domain.TreesImage;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TreesImageRepository extends JpaRepository<TreesImage, Integer> {
    // lấy ảnh chính của cây theo tree_id
    @Query(value = SQL.MAIN_IMAGE_OF_TREE,nativeQuery = true )
    TreesImage findByTreeId_Image(Integer treeId);
}