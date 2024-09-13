package com.example.TTCN2.service;

import com.example.TTCN2.domain.TreesImage;
import com.example.TTCN2.repository.TreesImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TreeImageService {
    @Autowired
    TreesImageRepository treesImageRepository;

    public void flush() {
        treesImageRepository.flush();
    }

    public void deleteAllInBatch(Iterable<TreesImage> entities) {
        treesImageRepository.deleteAllInBatch(entities);
    }

    public boolean existsById(Integer integer) {
        return treesImageRepository.existsById(integer);
    }

    public <S extends TreesImage> boolean exists(Example<S> example) {
        return treesImageRepository.exists(example);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        treesImageRepository.deleteAllByIdInBatch(integers);
    }

    public <S extends TreesImage, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return treesImageRepository.findBy(example, queryFunction);
    }

    public void deleteAll() {
        treesImageRepository.deleteAll();
    }

    public void deleteAllInBatch() {
        treesImageRepository.deleteAllInBatch();
    }

    @Deprecated
    public TreesImage getOne(Integer integer) {
        return treesImageRepository.getOne(integer);
    }

    public Optional<TreesImage> findById(Integer integer) {
        return treesImageRepository.findById(integer);
    }

    public <S extends TreesImage> long count(Example<S> example) {
        return treesImageRepository.count(example);
    }

    @Deprecated
    public TreesImage getById(Integer integer) {
        return treesImageRepository.getById(integer);
    }

    public List<TreesImage> findAll(Sort sort) {
        return treesImageRepository.findAll(sort);
    }

    public List<TreesImage> findAll() {
        return treesImageRepository.findAll();
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        treesImageRepository.deleteAllById(integers);
    }

    public <S extends TreesImage> S save(S entity) {
        return treesImageRepository.save(entity);
    }

    public TreesImage getReferenceById(Integer integer) {
        return treesImageRepository.getReferenceById(integer);
    }

    public List<TreesImage> findAllById(Iterable<Integer> integers) {
        return treesImageRepository.findAllById(integers);
    }

    public <S extends TreesImage> List<S> saveAll(Iterable<S> entities) {
        return treesImageRepository.saveAll(entities);
    }

    public void deleteAll(Iterable<? extends TreesImage> entities) {
        treesImageRepository.deleteAll(entities);
    }

    public <S extends TreesImage> S saveAndFlush(S entity) {
        return treesImageRepository.saveAndFlush(entity);
    }

    public <S extends TreesImage> Page<S> findAll(Example<S> example, Pageable pageable) {
        return treesImageRepository.findAll(example, pageable);
    }

    public <S extends TreesImage> List<S> findAll(Example<S> example) {
        return treesImageRepository.findAll(example);
    }

    public <S extends TreesImage> List<S> saveAllAndFlush(Iterable<S> entities) {
        return treesImageRepository.saveAllAndFlush(entities);
    }

    public <S extends TreesImage> Optional<S> findOne(Example<S> example) {
        return treesImageRepository.findOne(example);
    }

    public <S extends TreesImage> List<S> findAll(Example<S> example, Sort sort) {
        return treesImageRepository.findAll(example, sort);
    }

    public long count() {
        return treesImageRepository.count();
    }

    @Deprecated
    public void deleteInBatch(Iterable<TreesImage> entities) {
        treesImageRepository.deleteInBatch(entities);
    }

    public void delete(TreesImage entity) {
        treesImageRepository.delete(entity);
    }

    public Page<TreesImage> findAll(Pageable pageable) {
        return treesImageRepository.findAll(pageable);
    }

    public void deleteById(Integer integer) {
        treesImageRepository.deleteById(integer);
    }
}
