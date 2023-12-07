package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.model.JenisKeterangan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JenisKeteranganRepository extends JpaRepository<JenisKeterangan, Long> {
    List<JenisKeterangan> findByJenisInformasiId(Long jenisInformasiId);
}


