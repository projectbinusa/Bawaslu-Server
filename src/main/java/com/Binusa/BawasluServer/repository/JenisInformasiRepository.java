package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.JenisInformasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface JenisInformasiRepository extends JpaRepository<JenisInformasi, Long> {
    @Modifying
    @Query(value = "DELETE FROM JenisInformasi j WHERE j.id = :id")
    @Transactional
    void truncateTable(@Param("id") Long id);
}



