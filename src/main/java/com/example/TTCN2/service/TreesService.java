package com.example.TTCN2.service;

import com.example.TTCN2.repository.TreeRepository;
import com.example.TTCN2.domain.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TreesService {
    @Autowired
    TreeRepository treeRepository;

    // phân trang ở trang chủ
    // https://techmaster.vn/posts/37233/spring-with-thymeleaf-pagination-for-a-list-phan-trang-voi-spring-va-thymeleaf
    public Page<Tree> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Tree> list = List.of();

        if (treeRepository.findAllTree().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, treeRepository.findAllTree().size());
            list = treeRepository.findAllTree().subList(startItem, toIndex);
        }

        Page<Tree> coursePage = new PageImpl<Tree>(list, PageRequest.of(currentPage, pageSize), treeRepository.findAllTree().size());
        return coursePage;
    }
    // phan trang cho trang category user
    public Page<Tree> pageCategory(Pageable pageable,Integer idCate) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Tree> list = List.of();

        if (treeRepository.findAllTree().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, treeRepository.findAllTree().size());
            list = treeRepository.findAllTreeToIdCategory(idCate).subList(startItem, toIndex);
        }
        Page<Tree> coursePage = new PageImpl<Tree>(list, PageRequest.of(currentPage, pageSize), treeRepository.findAllTree().size());
        return coursePage;
    }


    public void flush() {
        treeRepository.flush();
    }

    public void deleteById(Integer integer) {
        treeRepository.deleteById(integer);
    }

    public Tree getReferenceById(Integer integer) {
        return treeRepository.getReferenceById(integer);
    }

    public long count() {
        return treeRepository.count();
    }

    public <S extends Tree> S saveAndFlush(S entity) {
        return treeRepository.saveAndFlush(entity);
    }

    public <S extends Tree> List<S> findAll(Example<S> example) {
        return treeRepository.findAll(example);
    }

    public <S extends Tree> List<S> saveAllAndFlush(Iterable<S> entities) {
        return treeRepository.saveAllAndFlush(entities);
    }

    @Deprecated
    public Tree getOne(Integer integer) {
        return treeRepository.getOne(integer);
    }

    public <S extends Tree> S save(S entity) {
        return treeRepository.save(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        treeRepository.deleteAllById(integers);
    }

    public <S extends Tree> long count(Example<S> example) {
        return treeRepository.count(example);
    }

    public void delete(Tree entity) {
        treeRepository.delete(entity);
    }

    public List<Tree> findAll(Sort sort) {
        return treeRepository.findAll(sort);
    }

    @Deprecated
    public Tree getById(Integer integer) {
        return treeRepository.getById(integer);
    }

    public <S extends Tree> Page<S> findAll(Example<S> example, Pageable pageable) {
        return treeRepository.findAll(example, pageable);
    }

    public <S extends Tree, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return treeRepository.findBy(example, queryFunction);
    }

    public void deleteAll() {
        treeRepository.deleteAll();
    }

    public Page<Tree> findAll(Pageable pageable) {
        return treeRepository.findAll(pageable);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        treeRepository.deleteAllByIdInBatch(integers);
    }

    public <S extends Tree> List<S> saveAll(Iterable<S> entities) {
        return treeRepository.saveAll(entities);
    }

    public <S extends Tree> boolean exists(Example<S> example) {
        return treeRepository.exists(example);
    }

    public void deleteAll(Iterable<? extends Tree> entities) {
        treeRepository.deleteAll(entities);
    }

    public void deleteAllInBatch() {
        treeRepository.deleteAllInBatch();
    }

    public List<Tree> findAll() {
        return treeRepository.findAll();
    }

    public <S extends Tree> List<S> findAll(Example<S> example, Sort sort) {
        return treeRepository.findAll(example, sort);
    }

    @Deprecated
    public void deleteInBatch(Iterable<Tree> entities) {
        treeRepository.deleteInBatch(entities);
    }

    public <S extends Tree> Optional<S> findOne(Example<S> example) {
        return treeRepository.findOne(example);
    }

    public boolean existsById(Integer integer) {
        return treeRepository.existsById(integer);
    }

    public List<Tree> findAllById(Iterable<Integer> integers) {
        return treeRepository.findAllById(integers);
    }

    public void deleteAllInBatch(Iterable<Tree> entities) {
        treeRepository.deleteAllInBatch(entities);
    }

    public Optional<Tree> findById(Integer integer) {
        return treeRepository.findById(integer);
    }
}
