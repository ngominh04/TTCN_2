package com.example.TTCN2.service;

import com.example.TTCN2.domain.PaymentMethod;
import com.example.TTCN2.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PaymentService {
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    // phan trang cho payment in admin
    public Page<PaymentMethod> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<PaymentMethod> list = List.of();

        if (paymentMethodRepository.getAllById_admin().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, paymentMethodRepository.getAllById_admin().size());
            list = paymentMethodRepository.getAllById_admin().subList(startItem, toIndex);
        }

        return new PageImpl<PaymentMethod>(list, PageRequest.of(currentPage, pageSize), paymentMethodRepository.getAllById_admin().size());
    }

    public void flush() {
        paymentMethodRepository.flush();
    }

    public <S extends PaymentMethod> S saveAndFlush(S entity) {
        return paymentMethodRepository.saveAndFlush(entity);
    }

    public <S extends PaymentMethod> List<S> saveAllAndFlush(Iterable<S> entities) {
        return paymentMethodRepository.saveAllAndFlush(entities);
    }

    @Deprecated
    public void deleteInBatch(Iterable<PaymentMethod> entities) {
        paymentMethodRepository.deleteInBatch(entities);
    }

    public void deleteAllInBatch(Iterable<PaymentMethod> entities) {
        paymentMethodRepository.deleteAllInBatch(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        paymentMethodRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteAllInBatch() {
        paymentMethodRepository.deleteAllInBatch();
    }

    @Deprecated
    public PaymentMethod getOne(Integer integer) {
        return paymentMethodRepository.getOne(integer);
    }

    @Deprecated
    public PaymentMethod getById(Integer integer) {
        return paymentMethodRepository.getById(integer);
    }

    public PaymentMethod getReferenceById(Integer integer) {
        return paymentMethodRepository.getReferenceById(integer);
    }

    public <S extends PaymentMethod> List<S> findAll(Example<S> example) {
        return paymentMethodRepository.findAll(example);
    }

    public <S extends PaymentMethod> List<S> findAll(Example<S> example, Sort sort) {
        return paymentMethodRepository.findAll(example, sort);
    }

    public <S extends PaymentMethod> List<S> saveAll(Iterable<S> entities) {
        return paymentMethodRepository.saveAll(entities);
    }

    public List<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }

    public List<PaymentMethod> findAllById(Iterable<Integer> integers) {
        return paymentMethodRepository.findAllById(integers);
    }

    public <S extends PaymentMethod> S save(S entity) {
        return paymentMethodRepository.save(entity);
    }

    public Optional<PaymentMethod> findById(Integer integer) {
        return paymentMethodRepository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return paymentMethodRepository.existsById(integer);
    }

    public long count() {
        return paymentMethodRepository.count();
    }

    public void deleteById(Integer integer) {
        paymentMethodRepository.deleteById(integer);
    }

    public void delete(PaymentMethod entity) {
        paymentMethodRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        paymentMethodRepository.deleteAllById(integers);
    }

    public void deleteAll(Iterable<? extends PaymentMethod> entities) {
        paymentMethodRepository.deleteAll(entities);
    }

    public void deleteAll() {
        paymentMethodRepository.deleteAll();
    }

    public List<PaymentMethod> findAll(Sort sort) {
        return paymentMethodRepository.findAll(sort);
    }

    public Page<PaymentMethod> findAll(Pageable pageable) {
        return paymentMethodRepository.findAll(pageable);
    }

    public <S extends PaymentMethod> Optional<S> findOne(Example<S> example) {
        return paymentMethodRepository.findOne(example);
    }

    public <S extends PaymentMethod> Page<S> findAll(Example<S> example, Pageable pageable) {
        return paymentMethodRepository.findAll(example, pageable);
    }

    public <S extends PaymentMethod> long count(Example<S> example) {
        return paymentMethodRepository.count(example);
    }

    public <S extends PaymentMethod> boolean exists(Example<S> example) {
        return paymentMethodRepository.exists(example);
    }

    public <S extends PaymentMethod, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return paymentMethodRepository.findBy(example, queryFunction);
    }
}
