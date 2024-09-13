package com.example.TTCN2.service;

import com.example.TTCN2.repository.TreeRepository;
import com.example.TTCN2.domain.Tree;
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
public class TreesService {
    @Autowired
    TreeRepository treeRepository;

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
