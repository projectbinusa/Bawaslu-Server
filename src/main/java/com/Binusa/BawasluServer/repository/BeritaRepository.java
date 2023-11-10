package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Berita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface BeritaRepository extends CrudRepository<Berita, Integer> {
    Berita findById(long id);

    List<Berita> findFirst5ByOrderByUpdatedDateDesc();

    @Query("SELECT p FROM Berita p WHERE " +
            "p.judulBerita LIKE CONCAT('%',:judul, '%')")
    List<Berita> findByJudulBerita(String judul);

    @Query("SELECT p FROM Berita p WHERE DATE_FORMAT(p.createdDate, '%Y-%m') LIKE CONCAT('%', :bulan, '%')")
    List<Berita> find(String bulan);

    @Query(value = "SELECT * FROM berita t1 INNER JOIN tags_berita t2 ON t1.id = t2.berita_id WHERE t2.tags_id = :tags ", nativeQuery = true)
    List<Berita> getAllByTags(@Param("tags") Long tagsId);
}
