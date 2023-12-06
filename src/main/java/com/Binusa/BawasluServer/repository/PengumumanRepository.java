package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Pengumuman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface PengumumanRepository extends CrudRepository<Pengumuman, Integer> {
    Pengumuman findById(long id);
    Page<Pengumuman> findAll(Pageable pageable);
    Page<Pengumuman> findAllByOrderByUpdatedDateDesc(Pageable pageable);


    @Query("SELECT p FROM Pengumuman p WHERE " +
            "p.judulPengumuman LIKE CONCAT('%',:judul, '%')")
    List<Pengumuman> findByJudulPengumuman(String judul);

    @Query("SELECT SUBSTRING(b.judulPengumuman, 1, LOCATE(' ', b.judulPengumuman) - 1) FROM Pengumuman b WHERE b.id = :id")
    String getByIdPengumuman(Long id);

    @Query(value = "SELECT * FROM pengumuman WHERE judul_pengumuman LIKE %:judul% LIMIT 4", nativeQuery = true)
    List<Pengumuman> relatedPost(@Param("judul") String judul);
}
