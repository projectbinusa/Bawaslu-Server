package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.TabelSop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabelSopReporitory extends CrudRepository<TabelSop, Integer> {
    TabelSop findById(long id);
    Page<TabelSop> findAll(Pageable pageable);
    Page<TabelSop> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query(value = "SELECT * FROM tabel_sop WHERE daftar_sop = :daftarSop ORDER BY created_date", nativeQuery = true)
    Page<TabelSop> allByDaftarSopByCreatedDate(Pageable pageable, String daftarSop);

    @Query(value = "SELECT * FROM tabel_sop WHERE daftar_sop = :daftarSop", nativeQuery = true)
    Page<TabelSop> allByDaftarSop(Pageable pageable, String daftarSop);

}
