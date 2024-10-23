package com.example.TTCN2.service;

import com.example.TTCN2.domain.OrderPayment;
import com.example.TTCN2.repository.OrderPaymentRepository;
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
public class OrderPaymentService {
    @Autowired
    OrderPaymentRepository orderPaymentRepository;

    public void flush() {
        orderPaymentRepository.flush();
    }

    public <S extends OrderPayment> boolean exists(Example<S> example) {
        return orderPaymentRepository.exists(example);
    }

    @Deprecated
    public void deleteInBatch(Iterable<OrderPayment> entities) {
        orderPaymentRepository.deleteInBatch(entities);
    }

    public <S extends OrderPayment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return orderPaymentRepository.findBy(example, queryFunction);
    }

    public <S extends OrderPayment> List<S> findAll(Example<S> example, Sort sort) {
        return orderPaymentRepository.findAll(example, sort);
    }

    public <S extends OrderPayment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return orderPaymentRepository.saveAllAndFlush(entities);
    }

    public <S extends OrderPayment> Optional<S> findOne(Example<S> example) {
        return orderPaymentRepository.findOne(example);
    }

    public void deleteAll() {
        orderPaymentRepository.deleteAll();
    }

    public <S extends OrderPayment> List<S> findAll(Example<S> example) {
        return orderPaymentRepository.findAll(example);
    }

    public Optional<OrderPayment> findById(Integer integer) {
        return orderPaymentRepository.findById(integer);
    }

    public List<OrderPayment> findAll(Sort sort) {
        return orderPaymentRepository.findAll(sort);
    }

    public <S extends OrderPayment> long count(Example<S> example) {
        return orderPaymentRepository.count(example);
    }

    public <S extends OrderPayment> S saveAndFlush(S entity) {
        return orderPaymentRepository.saveAndFlush(entity);
    }

    public OrderPayment getReferenceById(Integer integer) {
        return orderPaymentRepository.getReferenceById(integer);
    }

    public boolean existsById(Integer integer) {
        return orderPaymentRepository.existsById(integer);
    }

    public List<OrderPayment> findAll() {
        return orderPaymentRepository.findAll();
    }

    @Deprecated
    public OrderPayment getById(Integer integer) {
        return orderPaymentRepository.getById(integer);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        orderPaymentRepository.deleteAllById(integers);
    }

    public List<OrderPayment> findAllById(Iterable<Integer> integers) {
        return orderPaymentRepository.findAllById(integers);
    }

    @Deprecated
    public OrderPayment getOne(Integer integer) {
        return orderPaymentRepository.getOne(integer);
    }

    public void deleteAll(Iterable<? extends OrderPayment> entities) {
        orderPaymentRepository.deleteAll(entities);
    }

    public <S extends OrderPayment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return orderPaymentRepository.findAll(example, pageable);
    }

    public void deleteAllInBatch() {
        orderPaymentRepository.deleteAllInBatch();
    }

    public long count() {
        return orderPaymentRepository.count();
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        orderPaymentRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteById(Integer integer) {
        orderPaymentRepository.deleteById(integer);
    }

    public <S extends OrderPayment> S save(S entity) {
        return orderPaymentRepository.save(entity);
    }

    public <S extends OrderPayment> List<S> saveAll(Iterable<S> entities) {
        return orderPaymentRepository.saveAll(entities);
    }

    public void delete(OrderPayment entity) {
        orderPaymentRepository.delete(entity);
    }

    public Page<OrderPayment> findAll(Pageable pageable) {
        return orderPaymentRepository.findAll(pageable);
    }

    public void deleteAllInBatch(Iterable<OrderPayment> entities) {
        orderPaymentRepository.deleteAllInBatch(entities);
    }
}
