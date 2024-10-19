package com.example.TTCN2.service;

import com.example.TTCN2.domain.Cart;
import com.example.TTCN2.repository.CartRepository;
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
public class CartService {
    @Autowired
    CartRepository cartRepository;


    public <S extends Cart> List<S> findAll(Example<S> example) {
        return cartRepository.findAll(example);
    }

    public void deleteAll(Iterable<? extends Cart> entities) {
        cartRepository.deleteAll(entities);
    }

    public <S extends Cart> Page<S> findAll(Example<S> example, Pageable pageable) {
        return cartRepository.findAll(example, pageable);
    }

    public <S extends Cart> S saveAndFlush(S entity) {
        return cartRepository.saveAndFlush(entity);
    }

    public <S extends Cart> List<S> findAll(Example<S> example, Sort sort) {
        return cartRepository.findAll(example, sort);
    }

    public List<Cart> findAllById(Iterable<Integer> integers) {
        return cartRepository.findAllById(integers);
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public void deleteAll() {
        cartRepository.deleteAll();
    }

    public <S extends Cart> List<S> saveAllAndFlush(Iterable<S> entities) {
        return cartRepository.saveAllAndFlush(entities);
    }

    public void delete(Cart entity) {
        cartRepository.delete(entity);
    }

    public Page<Cart> findAll(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    @Deprecated
    public void deleteInBatch(Iterable<Cart> entities) {
        cartRepository.deleteInBatch(entities);
    }

    public <S extends Cart> S save(S entity) {
        return cartRepository.save(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        cartRepository.deleteAllById(integers);
    }

    public <S extends Cart> List<S> saveAll(Iterable<S> entities) {
        return cartRepository.saveAll(entities);
    }

    public void deleteAllInBatch(Iterable<Cart> entities) {
        cartRepository.deleteAllInBatch(entities);
    }

    public List<Cart> findAll(Sort sort) {
        return cartRepository.findAll(sort);
    }

    public <S extends Cart, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return cartRepository.findBy(example, queryFunction);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        cartRepository.deleteAllByIdInBatch(integers);
    }

    @Deprecated
    public Cart getOne(Integer integer) {
        return cartRepository.getOne(integer);
    }

    public void deleteById(Integer integer) {
        cartRepository.deleteById(integer);
    }

    public long count() {
        return cartRepository.count();
    }

    public void deleteAllInBatch() {
        cartRepository.deleteAllInBatch();
    }

    public <S extends Cart> Optional<S> findOne(Example<S> example) {
        return cartRepository.findOne(example);
    }

    @Deprecated
    public Cart getById(Integer integer) {
        return cartRepository.getById(integer);
    }

    public boolean existsById(Integer integer) {
        return cartRepository.existsById(integer);
    }

    public Optional<Cart> findById(Integer integer) {
        return cartRepository.findById(integer);
    }

    public <S extends Cart> long count(Example<S> example) {
        return cartRepository.count(example);
    }

    public Cart getReferenceById(Integer integer) {
        return cartRepository.getReferenceById(integer);
    }

    @Query(value = "select * from cart where id_user=?", nativeQuery = true)
    public Cart findByIdUser(Integer idUser) {
        return cartRepository.findByIdUser(idUser);
    }

    public void flush() {
        cartRepository.flush();
    }

    public <S extends Cart> boolean exists(Example<S> example) {
        return cartRepository.exists(example);
    }
}
