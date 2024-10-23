package com.example.TTCN2.service;

import com.example.TTCN2.domain.Order;
import com.example.TTCN2.repository.OrderRepository;
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
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void flush() {
        orderRepository.flush();
    }

    public <S extends Order> boolean exists(Example<S> example) {
        return orderRepository.exists(example);
    }

    @Deprecated
    public void deleteInBatch(Iterable<Order> entities) {
        orderRepository.deleteInBatch(entities);
    }

    public <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return orderRepository.findBy(example, queryFunction);
    }

    public <S extends Order> List<S> findAll(Example<S> example, Sort sort) {
        return orderRepository.findAll(example, sort);
    }

    public <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities) {
        return orderRepository.saveAllAndFlush(entities);
    }

    public <S extends Order> Optional<S> findOne(Example<S> example) {
        return orderRepository.findOne(example);
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }

    public <S extends Order> List<S> findAll(Example<S> example) {
        return orderRepository.findAll(example);
    }

    public Optional<Order> findById(Integer integer) {
        return orderRepository.findById(integer);
    }

    public List<Order> findAll(Sort sort) {
        return orderRepository.findAll(sort);
    }

    public <S extends Order> long count(Example<S> example) {
        return orderRepository.count(example);
    }

    public <S extends Order> S saveAndFlush(S entity) {
        return orderRepository.saveAndFlush(entity);
    }

    public Order getReferenceById(Integer integer) {
        return orderRepository.getReferenceById(integer);
    }

    public boolean existsById(Integer integer) {
        return orderRepository.existsById(integer);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Deprecated
    public Order getById(Integer integer) {
        return orderRepository.getById(integer);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        orderRepository.deleteAllById(integers);
    }

    public List<Order> findAllById(Iterable<Integer> integers) {
        return orderRepository.findAllById(integers);
    }

    @Deprecated
    public Order getOne(Integer integer) {
        return orderRepository.getOne(integer);
    }

    public void deleteAll(Iterable<? extends Order> entities) {
        orderRepository.deleteAll(entities);
    }

    public <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable) {
        return orderRepository.findAll(example, pageable);
    }

    public void deleteAllInBatch() {
        orderRepository.deleteAllInBatch();
    }

    public long count() {
        return orderRepository.count();
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        orderRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteById(Integer integer) {
        orderRepository.deleteById(integer);
    }

    public <S extends Order> S save(S entity) {
        return orderRepository.save(entity);
    }

    public <S extends Order> List<S> saveAll(Iterable<S> entities) {
        return orderRepository.saveAll(entities);
    }

    public void delete(Order entity) {
        orderRepository.delete(entity);
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public void deleteAllInBatch(Iterable<Order> entities) {
        orderRepository.deleteAllInBatch(entities);
    }
}
