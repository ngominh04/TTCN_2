package com.example.TTCN2.service;

import com.example.TTCN2.domain.CartItem;
import com.example.TTCN2.repository.CartItemRepository;
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
public class CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    public void flush() {
        cartItemRepository.flush();
    }

    public <S extends CartItem> List<S> findAll(Example<S> example) {
        return cartItemRepository.findAll(example);
    }

    public void deleteAll(Iterable<? extends CartItem> entities) {
        cartItemRepository.deleteAll(entities);
    }

    public <S extends CartItem> Page<S> findAll(Example<S> example, Pageable pageable) {
        return cartItemRepository.findAll(example, pageable);
    }

    public <S extends CartItem> S saveAndFlush(S entity) {
        return cartItemRepository.saveAndFlush(entity);
    }

    public <S extends CartItem> List<S> findAll(Example<S> example, Sort sort) {
        return cartItemRepository.findAll(example, sort);
    }

    public List<CartItem> findAllById(Iterable<Integer> integers) {
        return cartItemRepository.findAllById(integers);
    }

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public void deleteAll() {
        cartItemRepository.deleteAll();
    }

    public <S extends CartItem> List<S> saveAllAndFlush(Iterable<S> entities) {
        return cartItemRepository.saveAllAndFlush(entities);
    }

    public void delete(CartItem entity) {
        cartItemRepository.delete(entity);
    }

    public Page<CartItem> findAll(Pageable pageable) {
        return cartItemRepository.findAll(pageable);
    }

    @Deprecated
    public void deleteInBatch(Iterable<CartItem> entities) {
        cartItemRepository.deleteInBatch(entities);
    }

    public <S extends CartItem> S save(S entity) {
        return cartItemRepository.save(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        cartItemRepository.deleteAllById(integers);
    }

    public <S extends CartItem> List<S> saveAll(Iterable<S> entities) {
        return cartItemRepository.saveAll(entities);
    }

    public void deleteAllInBatch(Iterable<CartItem> entities) {
        cartItemRepository.deleteAllInBatch(entities);
    }

    public List<CartItem> findAll(Sort sort) {
        return cartItemRepository.findAll(sort);
    }

    public <S extends CartItem, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return cartItemRepository.findBy(example, queryFunction);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        cartItemRepository.deleteAllByIdInBatch(integers);
    }

    @Deprecated
    public CartItem getOne(Integer integer) {
        return cartItemRepository.getOne(integer);
    }

    public void deleteById(Integer integer) {
        cartItemRepository.deleteById(integer);
    }

    public long count() {
        return cartItemRepository.count();
    }

    public void deleteAllInBatch() {
        cartItemRepository.deleteAllInBatch();
    }

    public <S extends CartItem> Optional<S> findOne(Example<S> example) {
        return cartItemRepository.findOne(example);
    }

    @Deprecated
    public CartItem getById(Integer integer) {
        return cartItemRepository.getById(integer);
    }

    public boolean existsById(Integer integer) {
        return cartItemRepository.existsById(integer);
    }

    public Optional<CartItem> findById(Integer integer) {
        return cartItemRepository.findById(integer);
    }

    public <S extends CartItem> long count(Example<S> example) {
        return cartItemRepository.count(example);
    }

    public CartItem getReferenceById(Integer integer) {
        return cartItemRepository.getReferenceById(integer);
    }

    public <S extends CartItem> boolean exists(Example<S> example) {
        return cartItemRepository.exists(example);
    }
}
