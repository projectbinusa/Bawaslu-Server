package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.CategoryBerita;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryBeritaRepository extends CrudRepository<CategoryBerita, Integer> {
    CategoryBerita findById(long id);

    CategoryBerita getById(Long id);

    @Query(value = "SELECT * FROM category_berita LIMIT 7", nativeQuery = true)
    List<CategoryBerita> limit7();

    Page<CategoryBerita> findAll(Pageable pageable);
    Page<CategoryBerita> findAllByOrderByUpdatedDateDesc(Pageable pageable);
}
