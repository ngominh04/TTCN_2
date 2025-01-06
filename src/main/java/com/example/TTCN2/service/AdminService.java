package com.example.TTCN2.service;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.domain.User;
import com.example.TTCN2.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;
    // phan trang cho user in admin
    public Page<Admin> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Admin> list = List.of();

        if (adminRepository.findAll().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, adminRepository.findAll().size());
            list = adminRepository.findAll().subList(startItem, toIndex);
        }

        Page<Admin> coursePage = new PageImpl<Admin>(list, PageRequest.of(currentPage, pageSize), adminRepository.findAll().size());
        return coursePage;
    }

    public void delete(Admin entity) {
        adminRepository.delete(entity);
    }

    @Deprecated
    public Admin getOne(Integer integer) {
        return adminRepository.getOne(integer);
    }

    public <S extends Admin> List<S> saveAll(Iterable<S> entities) {
        return adminRepository.saveAll(entities);
    }

    public void deleteAllById(Iterable<? extends Integer> integers) {
        adminRepository.deleteAllById(integers);
    }

    public <S extends Admin> Optional<S> findOne(Example<S> example) {
        return adminRepository.findOne(example);
    }

    public void deleteAllInBatch() {
        adminRepository.deleteAllInBatch();
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public void deleteAll(Iterable<? extends Admin> entities) {
        adminRepository.deleteAll(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        adminRepository.deleteAllByIdInBatch(integers);
    }

    public long count() {
        return adminRepository.count();
    }

    public <S extends Admin> S save(S entity) {
        return adminRepository.save(entity);
    }

    public void deleteById(Integer integer) {
        adminRepository.deleteById(integer);
    }

    public void deleteAllInBatch(Iterable<Admin> entities) {
        adminRepository.deleteAllInBatch(entities);
    }

    @Query(value = "select * from admin where id=?", nativeQuery = true)
    public Admin getAdminById(Integer idAdmin) {
        return adminRepository.getAdminById(idAdmin);
    }

    public Page<Admin> findAll(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Deprecated
    public void deleteInBatch(Iterable<Admin> entities) {
        adminRepository.deleteInBatch(entities);
    }

    public Optional<Admin> findById(Integer integer) {
        return adminRepository.findById(integer);
    }

    public <S extends Admin> boolean exists(Example<S> example) {
        return adminRepository.exists(example);
    }

    public <S extends Admin> List<S> findAll(Example<S> example, Sort sort) {
        return adminRepository.findAll(example, sort);
    }

    public <S extends Admin> List<S> saveAllAndFlush(Iterable<S> entities) {
        return adminRepository.saveAllAndFlush(entities);
    }

    public <S extends Admin, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return adminRepository.findBy(example, queryFunction);
    }

    public <S extends Admin> Page<S> findAll(Example<S> example, Pageable pageable) {
        return adminRepository.findAll(example, pageable);
    }

    public <S extends Admin> List<S> findAll(Example<S> example) {
        return adminRepository.findAll(example);
    }

    public List<Admin> findAll(Sort sort) {
        return adminRepository.findAll(sort);
    }

    public <S extends Admin> S saveAndFlush(S entity) {
        return adminRepository.saveAndFlush(entity);
    }

    public List<Admin> findAllById(Iterable<Integer> integers) {
        return adminRepository.findAllById(integers);
    }

    public void deleteAll() {
        adminRepository.deleteAll();
    }

    public Admin getReferenceById(Integer integer) {
        return adminRepository.getReferenceById(integer);
    }

    public void flush() {
        adminRepository.flush();
    }

    public <S extends Admin> long count(Example<S> example) {
        return adminRepository.count(example);
    }

    public boolean existsById(Integer integer) {
        return adminRepository.existsById(integer);
    }

    @Deprecated
    public Admin getById(Integer integer) {
        return adminRepository.getById(integer);
    }
}
