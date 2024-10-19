package com.example.TTCN2.service;

import com.example.TTCN2.domain.User;
import com.example.TTCN2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public void deleteAll() {
        userRepository.deleteAll();
    }

    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return userRepository.saveAllAndFlush(entities);
    }

    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return userRepository.findAll(example, sort);
    }

    public Optional<User> findById(Integer integer) {
        return userRepository.findById(integer);
    }

    public <S extends User> long count(Example<S> example) {
        return userRepository.count(example);
    }

    @Deprecated
    public void deleteInBatch(Iterable<User> entities) {
        userRepository.deleteInBatch(entities);
    }

    public <S extends User> boolean exists(Example<S> example) {
        return userRepository.exists(example);
    }

    public boolean existsById(Integer integer) {
        return userRepository.existsById(integer);
    }

    public void flush() {
        userRepository.flush();
    }

    public User getReferenceById(Integer integer) {
        return userRepository.getReferenceById(integer);
    }

    public <S extends User> S saveAndFlush(S entity) {
        return userRepository.saveAndFlush(entity);
    }

    public <S extends User> List<S> findAll(Example<S> example) {
        return userRepository.findAll(example);
    }

    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return userRepository.findBy(example, queryFunction);
    }

    public void deleteAllInBatch() {
        userRepository.deleteAllInBatch();
    }


    public <S extends User> Optional<S> findOne(Example<S> example) {
        return userRepository.findOne(example);
    }

    @Deprecated
    public User getOne(Integer integer) {
        return userRepository.getOne(integer);
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Integer integer) {
        userRepository.deleteById(integer);
    }

    @Deprecated
    public User getById(Integer integer) {
        return userRepository.getById(integer);
    }

    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        userRepository.deleteAllById(integers);
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void deleteAllInBatch(Iterable<User> entities) {
        userRepository.deleteAllInBatch(entities);
    }

    public List<User> findAllById(Iterable<Integer> integers) {
        return userRepository.findAllById(integers);
    }

    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return userRepository.saveAll(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        userRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteAll(Iterable<? extends User> entities) {
        userRepository.deleteAll(entities);
    }

    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return userRepository.findAll(example, pageable);
    }
}
