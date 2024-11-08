package com.example.TTCN2.service;

import com.example.TTCN2.domain.Category;
import com.example.TTCN2.domain.Tree;
import com.example.TTCN2.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    // phân trang ở trang chủ
    // https://techmaster.vn/posts/37233/spring-with-thymeleaf-pagination-for-a-list-phan-trang-voi-spring-va-thymeleaf
    public Page<Category> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Category> list = List.of();

        if (categoryRepository.getAllCategoryAdmin().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, categoryRepository.getAllCategoryAdmin().size());
            list = categoryRepository.getAllCategoryAdmin().subList(startItem, toIndex);
        }

        Page<Category> coursePage = new PageImpl<Category>(list, PageRequest.of(currentPage, pageSize), categoryRepository.getAllCategoryAdmin().size());
        return coursePage;
    }

    public void flush() {
        categoryRepository.flush();
    }

    public <S extends Category> S saveAndFlush(S entity) {
        return categoryRepository.saveAndFlush(entity);
    }

    public <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities) {
        return categoryRepository.saveAllAndFlush(entities);
    }

    @Deprecated
    public void deleteInBatch(Iterable<Category> entities) {
        categoryRepository.deleteInBatch(entities);
    }

    public void deleteAllInBatch(Iterable<Category> entities) {
        categoryRepository.deleteAllInBatch(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        categoryRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteAllInBatch() {
        categoryRepository.deleteAllInBatch();
    }

    @Deprecated
    public Category getOne(Integer integer) {
        return categoryRepository.getOne(integer);
    }

    @Deprecated
    public Category getById(Integer integer) {
        return categoryRepository.getById(integer);
    }

    public Category getReferenceById(Integer integer) {
        return categoryRepository.getReferenceById(integer);
    }

    public <S extends Category> List<S> findAll(Example<S> example) {
        return categoryRepository.findAll(example);
    }

    public <S extends Category> List<S> findAll(Example<S> example, Sort sort) {
        return categoryRepository.findAll(example, sort);
    }

    public <S extends Category> List<S> saveAll(Iterable<S> entities) {
        return categoryRepository.saveAll(entities);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findAllById(Iterable<Integer> integers) {
        return categoryRepository.findAllById(integers);
    }

    public <S extends Category> S save(S entity) {
        return categoryRepository.save(entity);
    }

    public Optional<Category> findById(Integer integer) {
        return categoryRepository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return categoryRepository.existsById(integer);
    }

    public long count() {
        return categoryRepository.count();
    }

    public void deleteById(Integer integer) {
        categoryRepository.deleteById(integer);
    }

    public void delete(Category entity) {
        categoryRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        categoryRepository.deleteAllById(integers);
    }

    public void deleteAll(Iterable<? extends Category> entities) {
        categoryRepository.deleteAll(entities);
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public <S extends Category> Optional<S> findOne(Example<S> example) {
        return categoryRepository.findOne(example);
    }

    public <S extends Category> Page<S> findAll(Example<S> example, Pageable pageable) {
        return categoryRepository.findAll(example, pageable);
    }

    public <S extends Category> long count(Example<S> example) {
        return categoryRepository.count(example);
    }

    public <S extends Category> boolean exists(Example<S> example) {
        return categoryRepository.exists(example);
    }

    public <S extends Category, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return categoryRepository.findBy(example, queryFunction);
    }
}
