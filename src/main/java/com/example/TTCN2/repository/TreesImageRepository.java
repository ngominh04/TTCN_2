package com.example.TTCN2.repository;

import com.example.TTCN2.domain.TreesImage;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TreesImageRepository extends JpaRepository<TreesImage, Integer> {
    // lấy ảnh chính của cây theo tree_id
    @Query(value = SQL.MAIN_IMAGE_OF_TREE,nativeQuery = true )
    TreesImage findByMainTreeId_Image(Integer treeId);

    // toan bo anh cua cay theo idTree
    @Query(value = SQL.IDTREE_IMAGE,nativeQuery = true)
    List<TreesImage> findByIdTree_Images(Integer idTree);

    // get all image by idImage
    @Query(value = "select * from trees_image where id=?",nativeQuery = true)
    TreesImage getByIdImage(Integer idImage);

//    // delete TreesImage by id_tree
//    @Query(value = "delete from trees_image where id_tree=?",nativeQuery = true)
//    TreesImage deleteByTreeId(Integer id_tree);

}