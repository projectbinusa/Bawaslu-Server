package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Berita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface BeritaRepository extends CrudRepository<Berita, Integer> {
    Berita findById(long id);

    List<Berita> findFirst5ByOrderByUpdatedDateDesc();

    @Query("SELECT p FROM Berita p WHERE " +
            "p.judulBerita LIKE CONCAT('%',:judul, '%')")
    List<Berita> findByJudulBerita(String judul);
}
