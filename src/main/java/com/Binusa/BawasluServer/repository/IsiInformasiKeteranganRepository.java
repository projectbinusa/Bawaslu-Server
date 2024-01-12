package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IsiInformasiKeteranganRepository extends JpaRepository<IsiInformasiKeterangan, Long> {
    List<IsiInformasiKeterangan> findByJenisKeteranganId(Long jenisKeteranganId);
    Page<IsiInformasiKeterangan> findAll(Pageable pageable);
    Page<IsiInformasiKeterangan> findByJenisKeteranganId(Long jenisKeteranganId, Pageable pageable);

}
