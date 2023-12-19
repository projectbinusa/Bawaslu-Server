package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.TabelDip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabelDipRepository extends CrudRepository<TabelDip, Integer> {
    TabelDip findById(long id);
    Page<TabelDip> findAll(Pageable pageable);
    Page<TabelDip> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query(value = "SELECT * FROM tabel_dip WHERE daftar_dip = :daftarDip", nativeQuery = true)
    Page<TabelDip> allByDaftarDip(Pageable pageable, String daftarDip);
}
