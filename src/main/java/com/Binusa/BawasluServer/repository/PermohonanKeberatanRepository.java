package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.PermohonanKeberatan;
import org.springframework.data.domain.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermohonanKeberatanRepository extends CrudRepository<PermohonanKeberatan, Integer> {
    PermohonanKeberatan findById(long id);
    Page<PermohonanKeberatan> findAll(Pageable pageable);
}
