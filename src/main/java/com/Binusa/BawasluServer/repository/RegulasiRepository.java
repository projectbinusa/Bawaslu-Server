package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Regulasi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegulasiRepository extends CrudRepository<Regulasi, Integer> {
    Page<Regulasi> findAll(Pageable pageable);
    @Query("SELECT r FROM Regulasi r WHERE r.menuRegulasi.id = :menuRegulasiId")
    Page<Regulasi> getByMenuRegulasi(@Param("menuRegulasiId") Long menuRegulasiId, Pageable pageable);

    Regulasi findById(long id);

    @Query(value = "SELECT * FROM regulasi  WHERE menu_regulasi = :menuRegulasi", nativeQuery = true)
    List<Regulasi> getByMenuRegulasi(Long menuRegulasi);
}
