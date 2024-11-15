package com.example.TTCN2.service;

import com.example.TTCN2.domain.PaymentMethod;
import com.example.TTCN2.domain.TransportMethod;
import com.example.TTCN2.repository.TransportMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TransportService {
    @Autowired
    TransportMethodRepository transportMethodRepository;

    // phan trang cho transport in admin
    public Page<TransportMethod> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<TransportMethod> list = List.of();

        if (transportMethodRepository.getAllById_admin().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transportMethodRepository.getAllById_admin().size());
            list = transportMethodRepository.getAllById_admin().subList(startItem, toIndex);
        }

        return new PageImpl<TransportMethod>(list, PageRequest.of(currentPage, pageSize), transportMethodRepository.getAllById_admin().size());
    }
    public void flush() {
        transportMethodRepository.flush();
    }

    public <S extends TransportMethod> S saveAndFlush(S entity) {
        return transportMethodRepository.saveAndFlush(entity);
    }

    public <S extends TransportMethod> List<S> saveAllAndFlush(Iterable<S> entities) {
        return transportMethodRepository.saveAllAndFlush(entities);
    }

    @Deprecated
    public void deleteInBatch(Iterable<TransportMethod> entities) {
        transportMethodRepository.deleteInBatch(entities);
    }

    public void deleteAllInBatch(Iterable<TransportMethod> entities) {
        transportMethodRepository.deleteAllInBatch(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        transportMethodRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteAllInBatch() {
        transportMethodRepository.deleteAllInBatch();
    }

    @Deprecated
    public TransportMethod getOne(Integer integer) {
        return transportMethodRepository.getOne(integer);
    }

    @Deprecated
    public TransportMethod getById(Integer integer) {
        return transportMethodRepository.getById(integer);
    }

    public TransportMethod getReferenceById(Integer integer) {
        return transportMethodRepository.getReferenceById(integer);
    }

    public <S extends TransportMethod> List<S> findAll(Example<S> example) {
        return transportMethodRepository.findAll(example);
    }

    public <S extends TransportMethod> List<S> findAll(Example<S> example, Sort sort) {
        return transportMethodRepository.findAll(example, sort);
    }

    public <S extends TransportMethod> List<S> saveAll(Iterable<S> entities) {
        return transportMethodRepository.saveAll(entities);
    }

    public List<TransportMethod> findAll() {
        return transportMethodRepository.findAll();
    }

    public List<TransportMethod> findAllById(Iterable<Integer> integers) {
        return transportMethodRepository.findAllById(integers);
    }

    public <S extends TransportMethod> S save(S entity) {
        return transportMethodRepository.save(entity);
    }

    public Optional<TransportMethod> findById(Integer integer) {
        return transportMethodRepository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return transportMethodRepository.existsById(integer);
    }

    public long count() {
        return transportMethodRepository.count();
    }

    public void deleteById(Integer integer) {
        transportMethodRepository.deleteById(integer);
    }

    public void delete(TransportMethod entity) {
        transportMethodRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        transportMethodRepository.deleteAllById(integers);
    }

    public void deleteAll(Iterable<? extends TransportMethod> entities) {
        transportMethodRepository.deleteAll(entities);
    }

    public void deleteAll() {
        transportMethodRepository.deleteAll();
    }

    public List<TransportMethod> findAll(Sort sort) {
        return transportMethodRepository.findAll(sort);
    }

    public Page<TransportMethod> findAll(Pageable pageable) {
        return transportMethodRepository.findAll(pageable);
    }

    public <S extends TransportMethod> Optional<S> findOne(Example<S> example) {
        return transportMethodRepository.findOne(example);
    }

    public <S extends TransportMethod> Page<S> findAll(Example<S> example, Pageable pageable) {
        return transportMethodRepository.findAll(example, pageable);
    }

    public <S extends TransportMethod> long count(Example<S> example) {
        return transportMethodRepository.count(example);
    }

    public <S extends TransportMethod> boolean exists(Example<S> example) {
        return transportMethodRepository.exists(example);
    }

    public <S extends TransportMethod, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return transportMethodRepository.findBy(example, queryFunction);
    }
}
