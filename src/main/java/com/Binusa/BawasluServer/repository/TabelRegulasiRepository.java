package com.Binusa.BawasluServer.repository;


import com.Binusa.BawasluServer.model.TabelRegulasi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TabelRegulasiRepository extends CrudRepository<TabelRegulasi, Integer> {
    TabelRegulasi findById(long id);
    Page<TabelRegulasi> findAll(Pageable pageable);
    Page<TabelRegulasi> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query(value = "SELECT * FROM tabel_regulasi WHERE daftar_regulasi = :daftarRegulasi ORDER BY created_date", nativeQuery = true)
    Page<TabelRegulasi> allByDaftarRegulasiByCreatedDate(Pageable pageable, String daftarRegulasi);

    @Query(value = "SELECT * FROM tabel_regulasi WHERE daftar_regulasi = :daftarRegulasi", nativeQuery = true)
    Page<TabelRegulasi> allByDaftarRegulasi(Pageable pageable, String daftarRegulasi);

}
