package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.MenuRegulasi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRegulasiRepository extends CrudRepository<MenuRegulasi, Integer> {
    MenuRegulasi findById(long id);
    @Query(value = "SELECT * FROM menu_regulasi WHERE jenis_regulasi_id = :jenisId",
            countQuery = "SELECT count(*) FROM menu_regulasi WHERE jenis_regulasi_id = :jenisId",
            nativeQuery = true)
    Page<MenuRegulasi> getByJenis(Long jenisId, Pageable pageable);

    @Query(value = "SELECT * FROM menu_regulasi  WHERE jenis_regulasi_id = :jenisId", nativeQuery = true)
    List<MenuRegulasi> getByJenis(Long jenisId);

    @Query(value = "SELECT * FROM menu_regulasi",
            countQuery = "SELECT count(*) FROM menu_regulasi",
            nativeQuery = true)
    Page<MenuRegulasi> getAllMenuRegulasi(Pageable pageable);
}
