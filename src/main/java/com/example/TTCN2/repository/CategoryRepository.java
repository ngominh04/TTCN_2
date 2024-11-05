package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Category;
import com.example.TTCN2.projection.ICategory;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // get all cate
    @Query(value = "select * from category where is_delete=0",nativeQuery = true)
    List<Category> getAllCategory();

    @Query(value = SQL.COUNT_CATEGORY,nativeQuery = true)
    ICategory countAllById();
    @Query(value = "select * from category where id=?",nativeQuery = true)
    Category getCategoryById(Integer idCate);
}