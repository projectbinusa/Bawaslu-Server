package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.CategoryBerita;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryBeritaRepository extends CrudRepository<CategoryBerita, Integer> {
    CategoryBerita findById(long id);

    CategoryBerita getById(Long id);
}
