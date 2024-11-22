package com.example.TTCN2.service;

import com.example.TTCN2.domain.ShipperOrder;
import com.example.TTCN2.repository.ShipperOrderRepository;
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
public class ShipperOrderService {
    @Autowired
    ShipperOrderRepository shipperOrderRepository;

    public void flush() {
        shipperOrderRepository.flush();
    }

    public <S extends ShipperOrder> boolean exists(Example<S> example) {
        return shipperOrderRepository.exists(example);
    }

    @Deprecated
    public void deleteInBatch(Iterable<ShipperOrder> entities) {
        shipperOrderRepository.deleteInBatch(entities);
    }

    public <S extends ShipperOrder, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return shipperOrderRepository.findBy(example, queryFunction);
    }

    public <S extends ShipperOrder> List<S> saveAllAndFlush(Iterable<S> entities) {
        return shipperOrderRepository.saveAllAndFlush(entities);
    }

    public void deleteAllInBatch() {
        shipperOrderRepository.deleteAllInBatch();
    }

    public <S extends ShipperOrder> Optional<S> findOne(Example<S> example) {
        return shipperOrderRepository.findOne(example);
    }

    public void deleteAll() {
        shipperOrderRepository.deleteAll();
    }

    @Deprecated
    public ShipperOrder getOne(Integer integer) {
        return shipperOrderRepository.getOne(integer);
    }

    public List<ShipperOrder> findAll(Sort sort) {
        return shipperOrderRepository.findAll(sort);
    }

    public Optional<ShipperOrder> findById(Integer integer) {
        return shipperOrderRepository.findById(integer);
    }

    public <S extends ShipperOrder> long count(Example<S> example) {
        return shipperOrderRepository.count(example);
    }

    public <S extends ShipperOrder> S saveAndFlush(S entity) {
        return shipperOrderRepository.saveAndFlush(entity);
    }

    @Deprecated
    public ShipperOrder getById(Integer integer) {
        return shipperOrderRepository.getById(integer);
    }

    public boolean existsById(Integer integer) {
        return shipperOrderRepository.existsById(integer);
    }

    public List<ShipperOrder> findAll() {
        return shipperOrderRepository.findAll();
    }

    public ShipperOrder getReferenceById(Integer integer) {
        return shipperOrderRepository.getReferenceById(integer);
    }

    public <S extends ShipperOrder> S save(S entity) {
        return shipperOrderRepository.save(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        shipperOrderRepository.deleteAllById(integers);
    }

    public void deleteAll(Iterable<? extends ShipperOrder> entities) {
        shipperOrderRepository.deleteAll(entities);
    }

    public <S extends ShipperOrder> List<S> findAll(Example<S> example) {
        return shipperOrderRepository.findAll(example);
    }

    public List<ShipperOrder> findAllById(Iterable<Integer> integers) {
        return shipperOrderRepository.findAllById(integers);
    }

    public <S extends ShipperOrder> Page<S> findAll(Example<S> example, Pageable pageable) {
        return shipperOrderRepository.findAll(example, pageable);
    }

    public long count() {
        return shipperOrderRepository.count();
    }

    public <S extends ShipperOrder> List<S> findAll(Example<S> example, Sort sort) {
        return shipperOrderRepository.findAll(example, sort);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        shipperOrderRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteById(Integer integer) {
        shipperOrderRepository.deleteById(integer);
    }

    public <S extends ShipperOrder> List<S> saveAll(Iterable<S> entities) {
        return shipperOrderRepository.saveAll(entities);
    }

    public void delete(ShipperOrder entity) {
        shipperOrderRepository.delete(entity);
    }

    public Page<ShipperOrder> findAll(Pageable pageable) {
        return shipperOrderRepository.findAll(pageable);
    }

    public void deleteAllInBatch(Iterable<ShipperOrder> entities) {
        shipperOrderRepository.deleteAllInBatch(entities);
    }
}
