package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.JenisKeterangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenisKeteranganRepository extends JpaRepository<JenisKeterangan, Long> {
}

