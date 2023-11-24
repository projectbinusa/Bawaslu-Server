package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Pengumuman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface PengumumanRepository extends CrudRepository<Pengumuman, Integer> {
    Pengumuman findById(long id);
    Page<Pengumuman> findAll(Pageable pageable);

    @Query("SELECT p FROM Pengumuman p WHERE " +
            "p.judulPengumuman LIKE CONCAT('%',:judul, '%')")
    List<Pengumuman> findByJudulPengumuman(String judul);

}
