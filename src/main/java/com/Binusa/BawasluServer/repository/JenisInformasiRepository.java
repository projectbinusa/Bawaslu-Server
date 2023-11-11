package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.JenisInformasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenisInformasiRepository extends JpaRepository<JenisInformasi, Long> {
}


