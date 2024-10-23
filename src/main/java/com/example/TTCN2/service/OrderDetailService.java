package com.example.TTCN2.service;

import com.example.TTCN2.domain.OrderDetail;
import com.example.TTCN2.repository.OrderDetailRepository;
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
public class OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    public void flush() {
        orderDetailRepository.flush();
    }

    public <S extends OrderDetail> boolean exists(Example<S> example) {
        return orderDetailRepository.exists(example);
    }

    @Deprecated
    public void deleteInBatch(Iterable<OrderDetail> entities) {
        orderDetailRepository.deleteInBatch(entities);
    }

    public <S extends OrderDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return orderDetailRepository.findBy(example, queryFunction);
    }

    public <S extends OrderDetail> List<S> findAll(Example<S> example, Sort sort) {
        return orderDetailRepository.findAll(example, sort);
    }

    public <S extends OrderDetail> List<S> saveAllAndFlush(Iterable<S> entities) {
        return orderDetailRepository.saveAllAndFlush(entities);
    }

    public <S extends OrderDetail> Optional<S> findOne(Example<S> example) {
        return orderDetailRepository.findOne(example);
    }

    public void deleteAll() {
        orderDetailRepository.deleteAll();
    }

    public <S extends OrderDetail> List<S> findAll(Example<S> example) {
        return orderDetailRepository.findAll(example);
    }

    public Optional<OrderDetail> findById(Integer integer) {
        return orderDetailRepository.findById(integer);
    }

    public List<OrderDetail> findAll(Sort sort) {
        return orderDetailRepository.findAll(sort);
    }

    public <S extends OrderDetail> long count(Example<S> example) {
        return orderDetailRepository.count(example);
    }

    public <S extends OrderDetail> S saveAndFlush(S entity) {
        return orderDetailRepository.saveAndFlush(entity);
    }

    public OrderDetail getReferenceById(Integer integer) {
        return orderDetailRepository.getReferenceById(integer);
    }

    public boolean existsById(Integer integer) {
        return orderDetailRepository.existsById(integer);
    }

    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Deprecated
    public OrderDetail getById(Integer integer) {
        return orderDetailRepository.getById(integer);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        orderDetailRepository.deleteAllById(integers);
    }

    public List<OrderDetail> findAllById(Iterable<Integer> integers) {
        return orderDetailRepository.findAllById(integers);
    }

    @Deprecated
    public OrderDetail getOne(Integer integer) {
        return orderDetailRepository.getOne(integer);
    }

    public void deleteAll(Iterable<? extends OrderDetail> entities) {
        orderDetailRepository.deleteAll(entities);
    }

    public <S extends OrderDetail> Page<S> findAll(Example<S> example, Pageable pageable) {
        return orderDetailRepository.findAll(example, pageable);
    }

    public void deleteAllInBatch() {
        orderDetailRepository.deleteAllInBatch();
    }

    public long count() {
        return orderDetailRepository.count();
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        orderDetailRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteById(Integer integer) {
        orderDetailRepository.deleteById(integer);
    }

    public <S extends OrderDetail> S save(S entity) {
        return orderDetailRepository.save(entity);
    }

    public <S extends OrderDetail> List<S> saveAll(Iterable<S> entities) {
        return orderDetailRepository.saveAll(entities);
    }

    public void delete(OrderDetail entity) {
        orderDetailRepository.delete(entity);
    }

    public Page<OrderDetail> findAll(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    public void deleteAllInBatch(Iterable<OrderDetail> entities) {
        orderDetailRepository.deleteAllInBatch(entities);
    }
}
