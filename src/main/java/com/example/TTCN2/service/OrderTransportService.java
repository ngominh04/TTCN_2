package com.example.TTCN2.service;

import com.example.TTCN2.domain.OrderTransport;
import com.example.TTCN2.repository.OrderTransportRepository;
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
public class OrderTransportService {
    @Autowired
    OrderTransportRepository orderTransportRepository;

    public void flush() {
        orderTransportRepository.flush();
    }

    public <S extends OrderTransport> boolean exists(Example<S> example) {
        return orderTransportRepository.exists(example);
    }

    @Deprecated
    public void deleteInBatch(Iterable<OrderTransport> entities) {
        orderTransportRepository.deleteInBatch(entities);
    }

    public <S extends OrderTransport, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return orderTransportRepository.findBy(example, queryFunction);
    }

    public <S extends OrderTransport> List<S> findAll(Example<S> example, Sort sort) {
        return orderTransportRepository.findAll(example, sort);
    }

    public <S extends OrderTransport> List<S> saveAllAndFlush(Iterable<S> entities) {
        return orderTransportRepository.saveAllAndFlush(entities);
    }

    public <S extends OrderTransport> Optional<S> findOne(Example<S> example) {
        return orderTransportRepository.findOne(example);
    }

    public void deleteAll() {
        orderTransportRepository.deleteAll();
    }

    public <S extends OrderTransport> List<S> findAll(Example<S> example) {
        return orderTransportRepository.findAll(example);
    }

    public Optional<OrderTransport> findById(Integer integer) {
        return orderTransportRepository.findById(integer);
    }

    public List<OrderTransport> findAll(Sort sort) {
        return orderTransportRepository.findAll(sort);
    }

    public <S extends OrderTransport> long count(Example<S> example) {
        return orderTransportRepository.count(example);
    }

    public <S extends OrderTransport> S saveAndFlush(S entity) {
        return orderTransportRepository.saveAndFlush(entity);
    }

    public OrderTransport getReferenceById(Integer integer) {
        return orderTransportRepository.getReferenceById(integer);
    }

    public boolean existsById(Integer integer) {
        return orderTransportRepository.existsById(integer);
    }

    public List<OrderTransport> findAll() {
        return orderTransportRepository.findAll();
    }

    @Deprecated
    public OrderTransport getById(Integer integer) {
        return orderTransportRepository.getById(integer);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        orderTransportRepository.deleteAllById(integers);
    }

    public List<OrderTransport> findAllById(Iterable<Integer> integers) {
        return orderTransportRepository.findAllById(integers);
    }

    @Deprecated
    public OrderTransport getOne(Integer integer) {
        return orderTransportRepository.getOne(integer);
    }

    public void deleteAll(Iterable<? extends OrderTransport> entities) {
        orderTransportRepository.deleteAll(entities);
    }

    public <S extends OrderTransport> Page<S> findAll(Example<S> example, Pageable pageable) {
        return orderTransportRepository.findAll(example, pageable);
    }

    public void deleteAllInBatch() {
        orderTransportRepository.deleteAllInBatch();
    }

    public long count() {
        return orderTransportRepository.count();
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        orderTransportRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteById(Integer integer) {
        orderTransportRepository.deleteById(integer);
    }

    public <S extends OrderTransport> S save(S entity) {
        return orderTransportRepository.save(entity);
    }

    public <S extends OrderTransport> List<S> saveAll(Iterable<S> entities) {
        return orderTransportRepository.saveAll(entities);
    }

    public void delete(OrderTransport entity) {
        orderTransportRepository.delete(entity);
    }

    public Page<OrderTransport> findAll(Pageable pageable) {
        return orderTransportRepository.findAll(pageable);
    }

    public void deleteAllInBatch(Iterable<OrderTransport> entities) {
        orderTransportRepository.deleteAllInBatch(entities);
    }
}
