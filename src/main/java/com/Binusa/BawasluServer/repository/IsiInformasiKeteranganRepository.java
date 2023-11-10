package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IsiInformasiKeteranganRepository extends JpaRepository<IsiInformasiKeterangan, Long> {
    List<IsiInformasiKeterangan> findByJenisInformasiId(Long jenisInformasiId);
}
