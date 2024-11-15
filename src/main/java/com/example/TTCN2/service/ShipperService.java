package com.example.TTCN2.service;

import com.example.TTCN2.domain.Shipper;
import com.example.TTCN2.domain.User;
import com.example.TTCN2.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ShipperService {
    @Autowired
    ShipperRepository shipperRepository;
    // phan trang cho shipper in admin
    public Page<Shipper> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Shipper> list = List.of();

        if (shipperRepository.getAll().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, shipperRepository.getAll().size());
            list = shipperRepository.getAll().subList(startItem, toIndex);
        }

        return new PageImpl<Shipper>(list, PageRequest.of(currentPage, pageSize), shipperRepository.getAll().size());
    }

    public void flush() {
        shipperRepository.flush();
    }

    public <S extends Shipper> S saveAndFlush(S entity) {
        return shipperRepository.saveAndFlush(entity);
    }

    public <S extends Shipper> List<S> saveAllAndFlush(Iterable<S> entities) {
        return shipperRepository.saveAllAndFlush(entities);
    }

    @Deprecated
    public void deleteInBatch(Iterable<Shipper> entities) {
        shipperRepository.deleteInBatch(entities);
    }

    public void deleteAllInBatch(Iterable<Shipper> entities) {
        shipperRepository.deleteAllInBatch(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        shipperRepository.deleteAllByIdInBatch(integers);
    }

    public void deleteAllInBatch() {
        shipperRepository.deleteAllInBatch();
    }

    @Deprecated
    public Shipper getOne(Integer integer) {
        return shipperRepository.getOne(integer);
    }

    @Deprecated
    public Shipper getById(Integer integer) {
        return shipperRepository.getById(integer);
    }

    public Shipper getReferenceById(Integer integer) {
        return shipperRepository.getReferenceById(integer);
    }

    public <S extends Shipper> List<S> findAll(Example<S> example) {
        return shipperRepository.findAll(example);
    }

    public <S extends Shipper> List<S> findAll(Example<S> example, Sort sort) {
        return shipperRepository.findAll(example, sort);
    }

    public <S extends Shipper> List<S> saveAll(Iterable<S> entities) {
        return shipperRepository.saveAll(entities);
    }

    public List<Shipper> findAll() {
        return shipperRepository.findAll();
    }

    public List<Shipper> findAllById(Iterable<Integer> integers) {
        return shipperRepository.findAllById(integers);
    }

    public <S extends Shipper> S save(S entity) {
        return shipperRepository.save(entity);
    }

    public Optional<Shipper> findById(Integer integer) {
        return shipperRepository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return shipperRepository.existsById(integer);
    }

    public long count() {
        return shipperRepository.count();
    }

    public void deleteById(Integer integer) {
        shipperRepository.deleteById(integer);
    }

    public void delete(Shipper entity) {
        shipperRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        shipperRepository.deleteAllById(integers);
    }

    public void deleteAll(Iterable<? extends Shipper> entities) {
        shipperRepository.deleteAll(entities);
    }

    public void deleteAll() {
        shipperRepository.deleteAll();
    }

    public List<Shipper> findAll(Sort sort) {
        return shipperRepository.findAll(sort);
    }

    public Page<Shipper> findAll(Pageable pageable) {
        return shipperRepository.findAll(pageable);
    }

    public <S extends Shipper> Optional<S> findOne(Example<S> example) {
        return shipperRepository.findOne(example);
    }

    public <S extends Shipper> Page<S> findAll(Example<S> example, Pageable pageable) {
        return shipperRepository.findAll(example, pageable);
    }

    public <S extends Shipper> long count(Example<S> example) {
        return shipperRepository.count(example);
    }

    public <S extends Shipper> boolean exists(Example<S> example) {
        return shipperRepository.exists(example);
    }

    public <S extends Shipper, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return shipperRepository.findBy(example, queryFunction);
    }
}

