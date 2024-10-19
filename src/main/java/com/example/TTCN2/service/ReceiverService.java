package com.example.TTCN2.service;

import com.example.TTCN2.domain.Receiver;
import com.example.TTCN2.repository.ReceiverRepository;
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
public class ReceiverService {
    @Autowired
    ReceiverRepository receiverRepository;

    public void flush() {
        receiverRepository.flush();
    }

    public <S extends Receiver> S saveAndFlush(S entity) {
        return receiverRepository.saveAndFlush(entity);
    }

    public <S extends Receiver> List<S> saveAllAndFlush(Iterable<S> entities) {
        return receiverRepository.saveAllAndFlush(entities);
    }

    @Deprecated
    public void deleteInBatch(Iterable<Receiver> entities) {
        receiverRepository.deleteInBatch(entities);
    }

    public void deleteAllInBatch(Iterable<Receiver> entities) {
        receiverRepository.deleteAllInBatch(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        receiverRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteAllInBatch() {
        receiverRepository.deleteAllInBatch();
    }

    @Deprecated
    public Receiver getOne(Integer integer) {
        return receiverRepository.getOne(integer);
    }

    @Deprecated
    public Receiver getById(Integer integer) {
        return receiverRepository.getById(integer);
    }

    public Receiver getReferenceById(Integer integer) {
        return receiverRepository.getReferenceById(integer);
    }

    public <S extends Receiver> List<S> findAll(Example<S> example) {
        return receiverRepository.findAll(example);
    }

    public <S extends Receiver> List<S> findAll(Example<S> example, Sort sort) {
        return receiverRepository.findAll(example, sort);
    }

    public <S extends Receiver> List<S> saveAll(Iterable<S> entities) {
        return receiverRepository.saveAll(entities);
    }

    public List<Receiver> findAll() {
        return receiverRepository.findAll();
    }

    public List<Receiver> findAllById(Iterable<Integer> integers) {
        return receiverRepository.findAllById(integers);
    }

    public <S extends Receiver> S save(S entity) {
        return receiverRepository.save(entity);
    }

    public Optional<Receiver> findById(Integer integer) {
        return receiverRepository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return receiverRepository.existsById(integer);
    }

    public long count() {
        return receiverRepository.count();
    }

    public void deleteById(Integer integer) {
        receiverRepository.deleteById(integer);
    }

    public void delete(Receiver entity) {
        receiverRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        receiverRepository.deleteAllById(integers);
    }

    public void deleteAll(Iterable<? extends Receiver> entities) {
        receiverRepository.deleteAll(entities);
    }

    public void deleteAll() {
        receiverRepository.deleteAll();
    }

    public List<Receiver> findAll(Sort sort) {
        return receiverRepository.findAll(sort);
    }

    public Page<Receiver> findAll(Pageable pageable) {
        return receiverRepository.findAll(pageable);
    }

    public <S extends Receiver> Optional<S> findOne(Example<S> example) {
        return receiverRepository.findOne(example);
    }

    public <S extends Receiver> Page<S> findAll(Example<S> example, Pageable pageable) {
        return receiverRepository.findAll(example, pageable);
    }

    public <S extends Receiver> long count(Example<S> example) {
        return receiverRepository.count(example);
    }

    public <S extends Receiver> boolean exists(Example<S> example) {
        return receiverRepository.exists(example);
    }

    public <S extends Receiver, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return receiverRepository.findBy(example, queryFunction);
    }
}
