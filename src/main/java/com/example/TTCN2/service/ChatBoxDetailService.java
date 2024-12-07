package com.example.TTCN2.service;

import com.example.TTCN2.domain.ChatBoxDetail;
import com.example.TTCN2.repository.ChatBoxDetailRepository;
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
public class ChatBoxDetailService {
    @Autowired
    ChatBoxDetailRepository chatBoxDetailRepository;

    public void flush() {
        chatBoxDetailRepository.flush();
    }

    public <S extends ChatBoxDetail> S saveAndFlush(S entity) {
        return chatBoxDetailRepository.saveAndFlush(entity);
    }

    public <S extends ChatBoxDetail> List<S> saveAllAndFlush(Iterable<S> entities) {
        return chatBoxDetailRepository.saveAllAndFlush(entities);
    }

    @Deprecated
    public void deleteInBatch(Iterable<ChatBoxDetail> entities) {
        chatBoxDetailRepository.deleteInBatch(entities);
    }

    public void deleteAllInBatch(Iterable<ChatBoxDetail> entities) {
        chatBoxDetailRepository.deleteAllInBatch(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        chatBoxDetailRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteAllInBatch() {
        chatBoxDetailRepository.deleteAllInBatch();
    }

    @Deprecated
    public ChatBoxDetail getOne(Integer integer) {
        return chatBoxDetailRepository.getOne(integer);
    }

    @Deprecated
    public ChatBoxDetail getById(Integer integer) {
        return chatBoxDetailRepository.getById(integer);
    }

    public ChatBoxDetail getReferenceById(Integer integer) {
        return chatBoxDetailRepository.getReferenceById(integer);
    }

    public <S extends ChatBoxDetail> List<S> findAll(Example<S> example) {
        return chatBoxDetailRepository.findAll(example);
    }

    public <S extends ChatBoxDetail> List<S> findAll(Example<S> example, Sort sort) {
        return chatBoxDetailRepository.findAll(example, sort);
    }

    public <S extends ChatBoxDetail> List<S> saveAll(Iterable<S> entities) {
        return chatBoxDetailRepository.saveAll(entities);
    }

    public List<ChatBoxDetail> findAll() {
        return chatBoxDetailRepository.findAll();
    }

    public List<ChatBoxDetail> findAllById(Iterable<Integer> integers) {
        return chatBoxDetailRepository.findAllById(integers);
    }

    public <S extends ChatBoxDetail> S save(S entity) {
        return chatBoxDetailRepository.save(entity);
    }

    public Optional<ChatBoxDetail> findById(Integer integer) {
        return chatBoxDetailRepository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return chatBoxDetailRepository.existsById(integer);
    }

    public long count() {
        return chatBoxDetailRepository.count();
    }

    public void deleteById(Integer integer) {
        chatBoxDetailRepository.deleteById(integer);
    }

    public void delete(ChatBoxDetail entity) {
        chatBoxDetailRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        chatBoxDetailRepository.deleteAllById(integers);
    }

    public void deleteAll(Iterable<? extends ChatBoxDetail> entities) {
        chatBoxDetailRepository.deleteAll(entities);
    }

    public void deleteAll() {
        chatBoxDetailRepository.deleteAll();
    }

    public List<ChatBoxDetail> findAll(Sort sort) {
        return chatBoxDetailRepository.findAll(sort);
    }

    public Page<ChatBoxDetail> findAll(Pageable pageable) {
        return chatBoxDetailRepository.findAll(pageable);
    }

    public <S extends ChatBoxDetail> Optional<S> findOne(Example<S> example) {
        return chatBoxDetailRepository.findOne(example);
    }

    public <S extends ChatBoxDetail> Page<S> findAll(Example<S> example, Pageable pageable) {
        return chatBoxDetailRepository.findAll(example, pageable);
    }

    public <S extends ChatBoxDetail> long count(Example<S> example) {
        return chatBoxDetailRepository.count(example);
    }

    public <S extends ChatBoxDetail> boolean exists(Example<S> example) {
        return chatBoxDetailRepository.exists(example);
    }

    public <S extends ChatBoxDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return chatBoxDetailRepository.findBy(example, queryFunction);
    }
}
